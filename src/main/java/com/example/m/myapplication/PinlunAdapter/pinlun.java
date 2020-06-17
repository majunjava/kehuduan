package com.example.m.myapplication.PinlunAdapter;

/**
 * Created by Administrator on 2019/1/20 0020.
 */

public class pinlun {
    private int imgtouxiang;
    private String namepl;
    private String timepl;
    private String neirongpl;
    public pinlun(int imgtouxiang,String namepl,String timepl,String neirongpl){
        this.imgtouxiang=imgtouxiang;
        this.namepl=namepl;
        this.timepl=timepl;
        this.neirongpl=neirongpl;
    }
    public int getImgtouxiang(){
        return imgtouxiang;
    }
    public String getNamepl(){
        return namepl;
    }
    public String getTimepl(){
        return timepl;
    }
    public String getNeirongpl(){
        return neirongpl;
    }
}
