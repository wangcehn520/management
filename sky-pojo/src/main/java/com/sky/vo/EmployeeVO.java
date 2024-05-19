package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author LeLe
 * @date 2024/5/6 14:50
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO {
    private Long id;
    private String username;
    private String name;
    private String phone;
    private Integer status;
    private LocalDateTime updateTime;
}
