/*
 * Copyright (c) 2017 Jalotsav
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jalotsav.jamnadasconnect.models.teacher;

import com.google.gson.annotations.SerializedName;
import com.jalotsav.jamnadasconnect.common.AppConstants;

/**
 * Created by Jalotsav on 27/4/17.
 */

public class MdlTeacherContactEductn implements AppConstants {

    @SerializedName(KEY_TD_EXPERIENCE)
    String experience;
    @SerializedName(KEY_TD_AREA_OF_INTEREST)
    String areaOfInterest;
    @SerializedName(KEY_TD_EDUCATIONAL_QUALIFICATION)
    String educationalQualification;
    @SerializedName(KEY_TD_ACHIEVEMENTS)
    String achievements;
    @SerializedName(KEY_TD_BIRTH_DATE)
    String birthdate;
    @SerializedName(KEY_TD_ADDRESS_LINE_1)
    String addressLine1;
    @SerializedName(KEY_TD_ADDRESS_LINE_2)
    String addressLine2;
    @SerializedName(KEY_TD_CITY)
    String city;
    @SerializedName(KEY_TD_STATE)
    String state;
    @SerializedName(KEY_TD_COUNTRY)
    String country;
    @SerializedName(KEY_TD_PINCODE)
    String pincode;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAreaOfInterest() {
        return areaOfInterest;
    }

    public void setAreaOfInterest(String areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    public String getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
