package com.example.kampo.Models;

public class Workers {
    String FullName,ProfilePicUri,Specialist,WorkerId,ProfileThumbnail;

    public Workers() {
    }

    public Workers(String fullName, String profilePicUri, String specialist, String profileThumbnail) {
        FullName = fullName;
        ProfilePicUri = profilePicUri;
        Specialist = specialist;
        ProfileThumbnail = profileThumbnail;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getProfilePicUri() {
        return ProfilePicUri;
    }

    public void setProfilePicUri(String profilePicUri) {
        ProfilePicUri = profilePicUri;
    }

    public String getSpecialist() {
        return Specialist;
    }

    public void setSpecialist(String specialist) {
        Specialist = specialist;
    }

    public String getProfileThumbnail() {
        return ProfileThumbnail;
    }

    public void setProfileThumbnail(String profileThumbnail) {
        ProfileThumbnail = profileThumbnail;
    }

    public String getWorkerId() {
        return WorkerId;
    }

    public void setWorkerId(String workerId) {
        WorkerId = workerId;
    }
}
