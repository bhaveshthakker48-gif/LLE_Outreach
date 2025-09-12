package org.impactindia.llemeddocket.pojo;

import com.google.gson.annotations.SerializedName;

public class UserDetails implements WsModel, Model {

    public static final String USER_ID = "Userid";
    public static final String USER_NAME = "UserName";
    public static final String PASSWORD = "Password";
    public static final String TITLE = "Title";
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String NAME = "Name";
    public static final String PREFIX = "Prefix";
    public static final String USER_TYPE = "UserType";
    public static final String ADDRESS = "Address";
    public static final String MOBILE_NUMBER = "MobileNumber";
    public static final String CONTACT_NO = "ContactNo";
    public static final String EMAIL_ID = "Emailid";
    public static final String DESIGNATION = "Designation";


    private Long id;

    @SerializedName(DESIGNATION)
    private String designation;

    @SerializedName(USER_ID)
    private Long userId;

    @SerializedName(USER_NAME)
    private String userName;

    @SerializedName(PASSWORD)
    private String password;

    @SerializedName(TITLE)
    private String title;

    @SerializedName(FIRST_NAME)
    private String firstName;

    @SerializedName(LAST_NAME)
    private String lastName;

    @SerializedName(NAME)
    private String name;

    @SerializedName(PREFIX)
    private String prefix;

    @SerializedName(USER_TYPE)
    private String userType;

    @SerializedName(ADDRESS)
    private String address;

    @SerializedName(MOBILE_NUMBER)
    private String mobileNumber;

    @SerializedName(CONTACT_NO)
    private String contactNo;

    @SerializedName(EMAIL_ID)
    private String emailId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
