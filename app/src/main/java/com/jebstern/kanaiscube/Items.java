package com.jebstern.kanaiscube;

public class Items {

    private int id;
    private String name;
    private String affix;
    private String type;
    private int seasonMode;
    private int normalMode;
    private int stashMode;

    public Items() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getSeasonMode() {
        return seasonMode;
    }

    public void setSeasonMode(int seasonMode) {
        this.seasonMode = seasonMode;
    }

    public int getNormalMode() {
        return normalMode;
    }

    public void setNormalMode(int normalMode) {
        this.normalMode = normalMode;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getAffix() {
        return affix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAffix(String affix) {
        this.affix = affix;
    }

    public int getStashMode() {
        return stashMode;
    }

    public void setStashMode(int stashMode) {
        this.stashMode = stashMode;
    }
}
