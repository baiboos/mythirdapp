package com.zjt.mythirdapp;

public class RateItem {
    private int id;
    private String curName;
    private String curRate;

    public RateItem(String name,String val){
        super();
        curName = name;
        curRate = val;
    }

    public RateItem(){
        super();
        curName="";
        curRate="";
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getCurName(){
        return curName;
    }
    public void setCurName(String curName){
        this.curName = curName;
    }

    public String getCurRate(){
        return curRate;
    }
    public void setCurRate(String curRate){
        this.curRate = curRate;
    }
}
