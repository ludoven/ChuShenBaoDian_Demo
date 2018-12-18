package com.example.ludoven.chushenbaodian_demo.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class StepsBean extends DataSupport implements Serializable {
    private int data_Id;

    public int getData_Id() {
        return data_Id;
    }

    public void setData_Id(int data_Id) {
        this.data_Id = data_Id;
    }

    private String img;
    private String step;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
