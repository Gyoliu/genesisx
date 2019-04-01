package com.genesis.x.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: liuxing
 * @Date: 2019/3/29 17:28
 * @Description:
 */
@Data
public class ResetPasswordVo {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "旧密码不能为空")
    private String password;

    @NotEmpty(message = "新密码不能为空")
    private String resetPassword;

}