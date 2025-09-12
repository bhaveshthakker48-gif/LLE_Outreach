package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

public class State implements WsModel, Model {

    public static final String COUNTRY_ID = "CountryID";
    public static final String STATE_ID = "StateId";
    public static final String STATE_NAME = "StateName";

    private Long id;

    @SerializedName(COUNTRY_ID)
    private Long countryId;

    @SerializedName(STATE_ID)
    private Long stateId;

    @SerializedName(STATE_NAME)
    private String stateName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return this.stateName;
    }
}
