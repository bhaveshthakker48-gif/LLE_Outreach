package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

public class SubTag implements WsModel, Model {

    public static final String SUB_TAG_NAME = "SubTagName";
    public static final String SUB_TAG_ID = "SubTagId";
    public static final String TAG_ID = "Tagid";

    private Long id;

    @SerializedName(SUB_TAG_ID)
    private Long subTagId;

    @SerializedName(SUB_TAG_NAME)
    private String subTagName;

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

    public Long getSubTagId() {
        return subTagId;
    }

    public void setSubTagId(Long subTagId) {
        this.subTagId = subTagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getSubTagName() {
        return subTagName;
    }

    public void setSubTagName(String subTagName) {
        this.subTagName = subTagName;
    }

}
