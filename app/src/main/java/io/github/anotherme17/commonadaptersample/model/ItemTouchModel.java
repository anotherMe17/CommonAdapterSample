package io.github.anotherme17.commonadaptersample.model;

/**
 * Created by Administrator on 2017/1/19.
 */

public class ItemTouchModel {
    private String imgUrl;
    private String message;

    public ItemTouchModel(String imgUrl, String message) {
        this.imgUrl = imgUrl;
        this.message = message;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
