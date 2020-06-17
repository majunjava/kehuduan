package com.example.m.myapplication.bean;

import java.util.List;

public class TitleBean extends ExampleBaseBean {
    //轮播bean，为了方便叫titleBean
    private List<Integer> titles;//轮播的数据源一般都为数组。

    public List<Integer> getTitles() {
        return titles;
    }

    public void setTitles(List<Integer> titles) {
        this.titles = titles;
    }
}
