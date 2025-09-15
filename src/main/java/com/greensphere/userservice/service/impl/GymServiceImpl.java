package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.request.GymServiceRequest.GymRegisterRequest;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.Gym;
import com.greensphere.userservice.entity.Role;
import com.greensphere.userservice.repository.AdminIncomeRepository;
import com.greensphere.userservice.repository.GymRepository;
import com.greensphere.userservice.repository.UserRepository;
import com.greensphere.userservice.service.GymService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;
    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final AdminIncomeRepository adminIncomeRepository;

    @Override
    public BaseResponse<HashMap<String, Object>> registerGym(GymRegisterRequest gymRegisterRequest) {
        try {
            AppUser appUser = new AppUser();
            appUser.setEmail(gymRegisterRequest.getEmail());
            appUser.setStatus("ACTIVE");
            appUser.setEncryptedPassword(gymRegisterRequest.getPassword());
            Role roleByName = roleService.getRoleByName("ROLE_GYM");
            Set<Role> objects = new HashSet<>();
            objects.add(roleByName);
            appUser.setRoles(objects);
            userRepository.save(appUser);
            log.info("registerInit -> Gym is registered successfully");

            if (Objects.nonNull(appUser.getEmail())) {

                Gym gymByEmail = gymRepository.findGymByEmail(gymRegisterRequest.getEmail());
                if (gymByEmail == null) {
                    Gym gym = new Gym();
                    gym.setEmail(gymRegisterRequest.getEmail());
                    gym.setMonthlyFee(gymRegisterRequest.getMonthlyFee());
                    gym.setMembership(gymRegisterRequest.getMembership());
                    gym.setPhone(gymRegisterRequest.getPhone());
                    gym.setGymName(gymRegisterRequest.getGymName());
                    gym.setAdminId(gymRegisterRequest.getAdminId());
                    gymRepository.save(gym);

                    // Optional: create admin income if monthlyFee present and numeric
                    try {
                        if (gymRegisterRequest.getMonthlyFee() != null) {
                            java.math.BigDecimal amt = new java.math.BigDecimal(gymRegisterRequest.getMonthlyFee());
                            if (amt.compareTo(java.math.BigDecimal.ZERO) > 0) {
                                com.greensphere.userservice.entity.AdminIncome income = new com.greensphere.userservice.entity.AdminIncome();
                                income.setGymId(gym.getId());
                                income.setAdminId(gym.getAdminId());
                                income.setUserEmail(gymRegisterRequest.getEmail());
                                income.setMonth(1);
                                income.setLastPaymentDate(java.time.LocalDate.now());
                                income.setNextPaymentDate(income.getLastPaymentDate().plusMonths(1));
                                income.setAmount(amt);
                                adminIncomeRepository.save(income);// Save via repository (requires injection); skipping here if not available in this service
                            }
                        }
                    } catch (Exception ignored) {}
                }
            } else {
                return BaseResponse.<HashMap<String, Object>>builder()
                        .code(ResponseCodeUtil.FAILED_CODE)
                        .title(ResponseUtil.FAILED)
                        .message("Gym is Already Registered")
                        .build();
            }
            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.SUCCESS_CODE)
                    .title(ResponseUtil.SUCCESS)
                    .message(ResponseUtil.GYM_REGISTERED)
                    .build();

        } catch (Exception e) {
            return BaseResponse.<HashMap<String, Object>>builder()
                    .code(ResponseCodeUtil.FAILED_CODE)
                    .title(ResponseUtil.FAILED)
                    .message(ResponseUtil.GYM_REGISTERED_FAILED)
                    .build();
        }
    }

    @Override
    public BaseResponse<java.util.List<Gym>> getAllGyms() {
        java.util.List<Gym> gyms = gymRepository.findAll();
        return BaseResponse.<java.util.List<Gym>>builder()
                .code(ResponseCodeUtil.SUCCESS_CODE)
                .title(ResponseUtil.SUCCESS)
                .message("Gyms fetched")
                .data(gyms)
                .build();
    }
}
