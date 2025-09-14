package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.Trainer;
import com.greensphere.userservice.entity.WorkoutHistory;
import com.greensphere.userservice.repository.WorkoutsHistoryRepository;
import com.greensphere.userservice.repository.TrainerRepository;
import com.greensphere.userservice.repository.UserRepository;
import com.greensphere.userservice.service.TrainerService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final WorkoutsHistoryRepository workoutsHistoryRepository;

    @Override
    public BaseResponse<HashMap<String, Object>> getAllTrainers() {
        List<Trainer> all = trainerRepository.findAll();

        if (all.isEmpty()) {
            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.FAILED_CODE)
                    .title(ResponseUtil.FAILED)
                    .message("No Trainers Found")
                    .build();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("trainers", all);
        return BaseResponse.<HashMap<String, Object>>builder()
                .code(ResponseCodeUtil.SUCCESS_CODE)
                .title(ResponseUtil.SUCCESS)
                .message("Trainers Retrieved")
                .data(data)
                .build();

    }

    @Override
    public BaseResponse<HashMap<String, Object>> getClientsByTrainer(AppUser appUser) {
        List<AppUser> allByGovId = userRepository.findAllByGovIdAndRoleType(appUser.getGovId(),"ROLE_USER");
        if (allByGovId.isEmpty()) {
            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.SUCCESS_CODE)
                    .title(ResponseUtil.SUCCESS)
                    .message("No Clients Found")
                    .build();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("clients", allByGovId);
        return BaseResponse.<HashMap<String, Object>>builder()
                .code(ResponseCodeUtil.SUCCESS_CODE)
                .title(ResponseUtil.SUCCESS)
                .message("Clients Retrieved")
                .data(data)
                .build();
    }

    @Override
    public BaseResponse<HashMap<String, Object>> getTrainerForClient(AppUser appUser) {
        try {

            Trainer trainer = trainerRepository.findByTrainerId(String.valueOf(appUser.getGovId()));
            HashMap<String, Object> data = new HashMap<>();
            data.put("trainer", trainer);
            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.SUCCESS_CODE)
                    .title(ResponseUtil.SUCCESS)
                    .message("Trainer details for client")
                    .data(data)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.FAILED_CODE)
                    .title(ResponseUtil.FAILED)
                    .message("Failed to fetch trainer: " + e.getMessage())
                    .build();
        }
    }
}
