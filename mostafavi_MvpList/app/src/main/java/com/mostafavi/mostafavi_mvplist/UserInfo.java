package com.mostafavi.mostafavi_mvplist;

import org.json.JSONObject;

/**
 * Created by admin on 1/2/17.
 */
public class UserInfo {
    private String gender;
    private String titleName;
    private String firstName;
    private String lastName;
    private String email;
    private String thumbnailAvatar;
    private String mediumAvatar;
    private String largeAvatar;
    private String location;
    private String data;

    public static UserInfo getFromJson(JSONObject object) {
        UserInfo userInfo = new UserInfo();
        userInfo.setGender(object.optString(Keys.gender.name()));
        userInfo.setEmail(object.optString(Keys.email.name()));
        JSONObject name = object.optJSONObject(Keys.name.name());
        userInfo.setFirstName(name.optString(Keys.first.name()));
        userInfo.setTitleName(name.optString(Keys.title.name()));
        userInfo.setLastName(name.optString(Keys.last.name()));
        JSONObject picture = object.optJSONObject(Keys.picture.name());
        userInfo.setThumbnailAvatar(picture.optString(Keys.thumbnail.name()));
        userInfo.setLargeAvatar(picture.optString(Keys.large.name()));
        userInfo.setMediumAvatar(picture.optString(Keys.medium.name()));
        userInfo.setLocation(object.optString(Keys.location.name()));
        userInfo.setData(object.toString());
        return userInfo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getThumbnailAvatar() {
        return thumbnailAvatar;
    }

    public void setThumbnailAvatar(String thumbnailAvatar) {
        this.thumbnailAvatar = thumbnailAvatar;
    }

    public String getMediumAvatar() {
        return mediumAvatar;
    }

    public void setMediumAvatar(String mediumAvatar) {
        this.mediumAvatar = mediumAvatar;
    }

    public String getLargeAvatar() {
        return largeAvatar;
    }

    public void setLargeAvatar(String largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
