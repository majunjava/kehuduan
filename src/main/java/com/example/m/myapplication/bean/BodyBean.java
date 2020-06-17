package com.example.m.myapplication.bean;

import java.util.List;

public class BodyBean extends ExampleBaseBean {
    private String str;
    public void setStr1(String str) {
        this.str = str;
    }

    //中间横向滑动的bean  ，通常都有图片展示，这里用本地图片展示
//    private List<Integer> res;
//
//    public List<Integer> getRes() {
//        return res;
//    }
//
//    public void setRes(List<Integer> res) {
//        this.res = res;
//    }


    public BodyBean(String str) {
        this.str = str;
    }

    public String getStr1() {
        return str;
    }
}

