package com.mju.model.entity;

import java.io.Serializable;

/**
 * Created by Hua on 2017/2/19.
 */
public class Tmpl implements Serializable {
    protected Integer tmplId; //影厅模板编号
    protected String tmplName;    //模板名称
    protected Integer totalSeat;  //总座位数
    protected String seatMap;     //座位分布图

    public Integer getTmplId() {
        return tmplId;
    }

    public Tmpl setTmplId(Integer tmplId) {
        this.tmplId = tmplId;
        return this;
    }

    public String getTmplName() {
        return tmplName;
    }

    public Tmpl setTmplName(String tmplName) {
        this.tmplName = tmplName;
        return this;
    }

    public Integer getTotalSeat() {
        return totalSeat;
    }

    public Tmpl setTotalSeat(Integer totalSeat) {
        this.totalSeat = totalSeat;
        return this;
    }

    public String getSeatMap() {
        return seatMap;
    }

    public Tmpl setSeatMap(String seatMap) {
        this.seatMap = seatMap;
        return this;
    }
}
