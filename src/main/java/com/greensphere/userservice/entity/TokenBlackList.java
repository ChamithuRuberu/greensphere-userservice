/**
 * Created By Dilsha Prasanna
 * Date : 1/10/2024
 * Time : 1:16 PM
 * Project Name : upay-sso
 */

package com.greensphere.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String token;
    private LocalDateTime expiredTime;
}
