package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.request.GymServiceRequest.GymRegisterRequest;
import com.greensphere.userservice.dto.request.userRegister.UserRegisterRequestDto;
import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.entity.Gym;
import com.greensphere.userservice.entity.Role;
import com.greensphere.userservice.repository.GymRepository;
import com.greensphere.userservice.repository.UserRepository;
import com.greensphere.userservice.service.GymService;
import com.greensphere.userservice.service.UserService;
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

    @Override
    public BaseResponse<HashMap<String, Object>> registerGym(GymRegisterRequest gymRegisterRequest) {
        try {
            AppUser appUser = new AppUser();
            appUser.setEmail(gymRegisterRequest.getEmail());
            appUser.setStatus("ACTIVE");
            appUser.setPassword(gymRegisterRequest.getPassword());
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
                    gymRepository.save(gym);
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
}
