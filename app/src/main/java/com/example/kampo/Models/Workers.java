package com.example.kampo.Models;

public class Workers {
    String FullName,ProfilePicUri,Specialist,WorkerId;

    public Workers() {
    }

    public Workers(String fullName, String profilePicUri, String specialist, String workerId) {
        FullName = fullName;
        ProfilePicUri = profilePicUri;
        Specialist = specialist;
        WorkerId = workerId;
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
}
