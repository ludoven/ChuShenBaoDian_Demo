package com.example.ludoven.chushenbaodian_demo.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取的菜谱数据
 */
public class DataBean  extends DataSupport implements Serializable {
    private int databean_id;
    private int id;
    private String title;
    private String tags;
    private String imtro;
    private String ingredients;
    private String burden;
    private List<String> albums;
    private List<StepsBean> steps;

    public int getDatabean_id() {
        return databean_id;
    }

    public void setDatabean_id(int databean_id) {
        this.databean_id = databean_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }

    public List<StepsBean> getSteps() {

        return steps;
    }

    public void setSteps(List<StepsBean> steps) {
        this.steps = steps;
    }

}


