package com.uhuiapp.harness.utils.mybatis.talent;

import com.uhuiapp.harness.utils.mybatis.BaseEntity;

/**
 * Created by zhaoxiong on 2018/1/30.
 */
public class User extends BaseEntity {
    private Integer id;
    private String name;
    private String email;
    private String mobile_phone;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public Integer getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }
}
