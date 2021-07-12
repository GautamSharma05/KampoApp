package com.example.kampo.Models;

public class Users {
    String Email,FullName,Gender,PhoneNumber,UserId,UserProfilePic,Address;

    public Users() {
    }

    public Users(String email, String fullName, String gender, String phoneNumber, String userProfilePic, String address) {
        Email = email;
        FullName = fullName;
        Gender = gender;
        PhoneNumber = phoneNumber;
        UserProfilePic = userProfilePic;
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserProfilePic() {
        return UserProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        UserProfilePic = userProfilePic;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
