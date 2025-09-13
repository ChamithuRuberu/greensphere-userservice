package com.greensphere.userservice.dto.response.activity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuditTrailResponse {
    private List<AuditActivityDTO> activities;
}
