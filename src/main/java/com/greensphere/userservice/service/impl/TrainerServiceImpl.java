package com.greensphere.userservice.service.impl;

import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.entity.Trainer;
import com.greensphere.userservice.repository.TrainerRepository;
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
}
