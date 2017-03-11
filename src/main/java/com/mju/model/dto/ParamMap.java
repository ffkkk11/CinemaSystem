package com.mju.model.dto;

import java.util.Map;

/**
 * Created by Hua on 2017/3/2.
 */
public class ParamMap {
    private Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public ParamMap setMap(Map<String, Object> map) {
        this.map = map;
        return this;
    }
}
