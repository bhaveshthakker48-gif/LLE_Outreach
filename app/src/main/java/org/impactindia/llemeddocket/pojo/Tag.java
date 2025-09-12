package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

public class Tag implements WsModel, Model {

    public static final String TAG_NAME = "TagName";
    public static final String USER_ID = "Userid";
    public static final String TAG_ID = "TagId";

    private Long id;

    @SerializedName(TAG_NAME)
    private String tagName;

    @SerializedName(USER_ID)
    private Long userId;

    @SerializedName(TAG_ID)
    private Long tagId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
