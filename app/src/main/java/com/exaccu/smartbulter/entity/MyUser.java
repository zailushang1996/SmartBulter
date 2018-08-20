package com.exaccu.smartbulter.entity;

import cn.bmob.v3.BmobUser;

/**
 * Author:liuzhixiang
 * PackageName:com.exaccu.smartbulter.entity
 * Create by 17864 on 2018/8/20
 *
 * @ Description:用户实体类
 */
public class MyUser extends BmobUser {

    private int age;

    /**
     * true:男
     * false:女
     */
    private boolean sex;


    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
