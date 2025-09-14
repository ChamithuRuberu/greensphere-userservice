package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.activity.AuditActivityDTO;
import com.greensphere.userservice.dto.response.activity.AuditTrailResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.AuditLog;
import com.greensphere.userservice.entity.GymIncome;
import com.greensphere.userservice.entity.TrainerIncome;
import com.greensphere.userservice.entity.WorkoutProgressLog;
import com.greensphere.userservice.repository.GymIncomeRepository;
import com.greensphere.userservice.repository.TrainerIncomeRepository;
import com.greensphere.userservice.repository.AuditLogRepository;
import com.greensphere.userservice.repository.UserRepository;
import com.greensphere.userservice.repository.WorkoutProgressLogRepository;
import com.greensphere.userservice.service.ActivityService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final WorkoutProgressLogRepository workoutProgressLogRepository;
    private final TrainerIncomeRepository trainerIncomeRepository;
    private final GymIncomeRepository gymIncomeRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    public BaseResponse<AuditTrailResponse> getAuditTrail(AppUser appUser, int daysBack) {
        try {
            int window = Math.max(daysBack, 0);
            LocalDateTime after = LocalDateTime.now().minusDays(window);

            List<AuditActivityDTO> activities = new ArrayList<>();

            String normRole = normalizeRole(appUser.getRoleType());

            // Fallback: if role is missing/empty, still provide useful data (latest 50 audit logs)
            if (normRole == null || normRole.isEmpty()) {
                auditLogRepository.findAll(PageRequest.of(0, 50, Sort.by("occurredAt").descending()))
                        .getContent()
                        .forEach(a -> activities.add(mapAuditLog(a)));
            }

            if ("USER".equals(normRole)) {
                String userId = appUser.getUsername();
                workoutProgressLogRepository.findByWorkoutHistory_UserIdAndWorkoutDateAfter(userId, after)
                        .forEach(l -> activities.add(mapWorkoutLog(l)));
                auditLogRepository.findByActorIdAndOccurredAtAfter(userId, after)
                        .forEach(a -> activities.add(mapAuditLog(a)));
            } else if ("TRAINER".equals(normRole)) {
                String trainerId = String.valueOf(appUser.getGovId());
                workoutProgressLogRepository.findByWorkoutHistory_TrainerIdAndWorkoutDateAfter(trainerId, after)
                        .forEach(l -> activities.add(mapWorkoutLog(l)));
                trainerIncomeRepository.findByTrainerIdAndNextPaymentDateBetween(appUser.getGovId(), LocalDate.now().minusDays(window), LocalDate.now().plusDays(window))
                        .forEach(i -> activities.add(mapTrainerIncome(i)));
                auditLogRepository.findByActorIdAndOccurredAtAfter(appUser.getUsername(), after)
                        .forEach(a -> activities.add(mapAuditLog(a)));
            } else if (isGymRole(normRole)) {
                Long gymId = appUser.getGymId();
                if (gymId != null) {
                    gymIncomeRepository.findByGymIdAndNextPaymentDateAfter(gymId, LocalDate.now().minusDays(window))
                            .forEach(i -> activities.add(mapGymIncome(i)));
                }
                auditLogRepository.findByActorIdAndOccurredAtAfter(appUser.getUsername(), after)
                        .forEach(a -> activities.add(mapAuditLog(a)));
            } else if (isAdminRole(normRole)) {
                workoutProgressLogRepository.findByWorkoutDateAfter(after)
                        .forEach(l -> activities.add(mapWorkoutLog(l)));
                trainerIncomeRepository.findByNextPaymentDateBetween(LocalDate.now().minusDays(window), LocalDate.now().plusDays(window))
                        .forEach(i -> activities.add(mapTrainerIncome(i)));
                gymIncomeRepository.findByNextPaymentDateAfter(LocalDate.now().minusDays(window))
                        .forEach(i -> activities.add(mapGymIncome(i)));
                auditLogRepository.findByOccurredAtAfter(after)
                        .forEach(a -> activities.add(mapAuditLog(a)));
                // Always include latest 50 audit rows for admin
                auditLogRepository.findAll(PageRequest.of(0, 50, Sort.by("occurredAt").descending()))
                        .getContent()
                        .forEach(a -> activities.add(mapAuditLog(a)));
            }

            // Final sort desc by time
            activities.sort(Comparator.comparing(AuditActivityDTO::getOccurredAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed());

            AuditTrailResponse data = new AuditTrailResponse();
            data.setActivities(activities);

            return BaseResponse.<AuditTrailResponse>builder()
                    .code(ResponseCodeUtil.SUCCESS_CODE)
                    .title(ResponseUtil.SUCCESS)
                    .message("Audit trail fetched")
                    .data(data)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<AuditTrailResponse>builder()
                    .code(ResponseCodeUtil.FAILED_CODE)
                    .title(ResponseUtil.FAILED)
                    .message("Failed to fetch audit trail: " + e.getMessage())
                    .build();
        }
    }

    private AuditActivityDTO mapWorkoutLog(WorkoutProgressLog l) {
        AuditActivityDTO dto = new AuditActivityDTO();
        dto.setActorType("USER");
        dto.setActorId(l.getWorkoutHistory() != null ? l.getWorkoutHistory().getUserId() : null);
        String name = null;
        if (dto.getActorId() != null) {
            com.greensphere.userservice.entity.AppUser u = userRepository.findAppUserByUsername(dto.getActorId());
            if (u != null) name = u.getFullName();
        }
        dto.setActorName(name);
        dto.setActivityType("WORKOUT_LOG");
        dto.setDescription("Completed " + safe(l.getExerciseName()) + " sets=" + safe(l.getCompletedSets()) + " reps=" + safe(l.getCompletedReps()));
        dto.setOccurredAt(l.getWorkoutDate());
        return dto;
    }

    private AuditActivityDTO mapTrainerIncome(TrainerIncome i) {
        AuditActivityDTO dto = new AuditActivityDTO();
        dto.setActorType("TRAINER");
        dto.setActorId(String.valueOf(i.getTrainerId()));
        dto.setActorName("Trainer " + i.getTrainerId());
        dto.setActivityType("TRAINER_PAYMENT");
        dto.setDescription("Next payment date=" + i.getNextPaymentDate());
        dto.setOccurredAt(i.getLastPaymentDate() != null ? i.getLastPaymentDate().atStartOfDay() : LocalDateTime.now());
        return dto;
    }

    private AuditActivityDTO mapGymIncome(GymIncome i) {
        AuditActivityDTO dto = new AuditActivityDTO();
        dto.setActorType("GYM");
        dto.setActorId(String.valueOf(i.getGymId()));
        dto.setActorName("Gym " + i.getGymId());
        dto.setActivityType("GYM_PAYMENT");
        dto.setDescription("Next payment date=" + i.getNextPaymentDate());
        dto.setOccurredAt(i.getLastPaymentDate() != null ? i.getLastPaymentDate().atStartOfDay() : LocalDateTime.now());
        return dto;
    }

    private AuditActivityDTO mapAuditLog(AuditLog a) {
        AuditActivityDTO dto = new AuditActivityDTO();
        dto.setActorType(a.getActorType());
        dto.setActorId(a.getActorId());
        String name = null;
        if (a.getActorId() != null) {
            com.greensphere.userservice.entity.AppUser u = userRepository.findAppUserByUsername(a.getActorId());
            if (u != null) name = u.getFullName();
        }
        dto.setActorName(name);
        dto.setActivityType("REQUEST");
        dto.setDescription(a.getAction());
        dto.setOccurredAt(a.getOccurredAt());
        return dto;
    }

    private String safe(Object o) {
        return o == null ? "-" : String.valueOf(o);
    }

    private String normalizeRole(String role) {
        if (role == null) return "";
        String r = role.trim().toUpperCase();
        if (r.startsWith("ROLE_")) r = r.substring(5); // -> SUPER_ADMIN, TRAINER, GYM, USER
        if ("SUPERADMIN".equals(r)) r = "SUPER_ADMIN"; // map DB value 'superadmin'
        return r;
    }

    private boolean isAdminRole(String r) {
        return "SUPER_ADMIN".equals(r) || "SUPERADMIN".equals(r);
    }

    private boolean isGymRole(String r) {
        return "GYM".equals(r);
    }
}


