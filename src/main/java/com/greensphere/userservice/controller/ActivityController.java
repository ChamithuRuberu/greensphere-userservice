package com.greensphere.userservice.controller;

import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.DefaultResponse;
import com.greensphere.userservice.dto.response.activity.AuditTrailResponse;
import com.greensphere.userservice.entity.AppUser;
import com.greensphere.userservice.service.ActivityService;
import com.greensphere.userservice.utils.ResponseCodeUtil;
import com.greensphere.userservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/audit")
    public ResponseEntity<DefaultResponse> getAudit(
            @RequestAttribute("user") AppUser appUser,
            @RequestParam(defaultValue = "7") int days) {
        BaseResponse<AuditTrailResponse> response = activityService.getAuditTrail(appUser, days);
        if (response.getCode().equals(ResponseCodeUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(DefaultResponse.success(
                    ResponseUtil.SUCCESS,
                    response.getMessage(),
                    response.getData()));
        } else if (response.getCode().equals(ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE)) {
            return ResponseEntity.internalServerError()
                    .body(DefaultResponse.internalServerError(
                            ResponseCodeUtil.INTERNAL_SERVER_ERROR_CODE,
                            response.getMessage()));
        } else {
            return ResponseEntity.badRequest().body(DefaultResponse.error(
                    ResponseUtil.FAILED,
                    response.getMessage(),
                    response.getData()));
        }
    }
}


