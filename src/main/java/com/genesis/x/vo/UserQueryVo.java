package com.genesis.x.vo;

import lombok.Data;

/**
 * @Author: liuxing
 * @Date: 2018/12/4 18:13
 * @Description:
 */
@Data
public class UserQueryVo {

    private String username;
    private String firstName;
    private Boolean locking;
    private Boolean enable;
    private String email;
    private String phoneNumber;

}