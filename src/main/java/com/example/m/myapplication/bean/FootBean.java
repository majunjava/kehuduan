package com.example.m.myapplication.bean;
public class FootBean extends ExampleBaseBean {
    //正常列表的bean
    private String str;
    private String imgfoot;
    private  String price;
    private String str2;
    private String imgfoot2;
    private  String price2;

    public FootBean(String str, String imgfoot, String price, String str2, String imgfoot2, String price2) {
        this.str = str;
        this.imgfoot = imgfoot;
        this.price = price;
        this.str2 = str2;
        this.imgfoot2 = imgfoot2;
        this.price2 = price2;
    }

    public String getStr2() {
        return str2;
    }

    public String getImgfoot2() {
        return imgfoot2;
    }

    public String getPrice2() {
        return price2;
    }

    public String getImgfoot() {
        return imgfoot;
    }

    public String getPrice() {
        return price;
    }

    public void setImgfoot(String imgfoot) {
        this.imgfoot = imgfoot;
    }





    public String getStr() {
        return str;
    }

}

