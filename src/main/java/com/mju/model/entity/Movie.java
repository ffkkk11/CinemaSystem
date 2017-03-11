package com.mju.model.entity;

import java.io.Serializable;

/**
 * Created by Hua on 2017/2/19.
 */
public class Movie implements Serializable {
    private Integer movieId;     //编号
    private String movieName;   //名称
    private String info;        //描述
    private String pic;         //图片

    public Integer getMovieId() {
        return movieId;
    }

    public Movie setMovieId(Integer movieId) {
        this.movieId = movieId;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public Movie setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public Movie setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Movie setPic(String pic) {
        this.pic = pic;
        return this;
    }
}
