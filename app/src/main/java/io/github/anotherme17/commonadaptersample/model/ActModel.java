package io.github.anotherme17.commonadaptersample.model;

/**
 * Created by user798 on 2017/1/5.
 */

public class ActModel {

    private String txt;
    private int img;

    public ActModel(String txt, int img) {
        this.txt = txt;
        this.img = img;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
