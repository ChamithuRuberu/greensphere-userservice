package com.greensphere.userservice.controller;

import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.DefaultResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.service.TrainerService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping("/get-all-trainers")
    public ResponseEntity<DefaultResponse> getAllTrainers() {
        BaseResponse<HashMap<String, Object>> response = trainerService.getAllTrainers();
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }

    @PostMapping("/get-clients")
    public ResponseEntity<DefaultResponse> getClientsByTrainer(@RequestAttribute("user") AppUser appUser) {
        BaseResponse<HashMap<String, Object>> response = trainerService.getClientsByTrainer(appUser);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(ResponseUtil.SUCCESS, response.getMessage(), response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE, response.getMessage()));
        } else {
            return ResponseEntity.badRequest()
                    .body(DefaultResponse.error(ResponseUtil.FAILED, response.getMessage(), response.getData()));
        }
    }


}

