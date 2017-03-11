package com.mju.model.entity;

import java.io.Serializable;

/**
 * Created by Hua on 2017/2/19.
 */
public class Cinema implements Serializable {

    private Integer cinemaId;    //编号
    private String cinemaName;//影院名称
    private String address;//地址
    private String tel;//电话
    private String info;//描述

    public Integer getCinemaId() {
        return cinemaId;
    }

    public Cinema setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
        return this;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public Cinema setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Cinema setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public Cinema setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public Cinema setInfo(String info) {
        this.info = info;
        return this;
    }
}
