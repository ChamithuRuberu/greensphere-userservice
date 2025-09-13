package com.greensphere.userservice.service;

import com.greensphere.userservice.dto.response.BaseResponse;
import com.greensphere.userservice.dto.response.activity.AuditTrailResponse;
import com.greensphere.userservice.entity.AppUser;

public interface ActivityService {
    BaseResponse<AuditTrailResponse> getAuditTrail(AppUser appUser, int daysBack);
}


