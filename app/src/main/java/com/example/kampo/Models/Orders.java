package com.example.kampo.Models;

public class Orders {

    String BookingId,Address,MobileNumber,PaymentMethod,UserId,Slot,WorkerMobileNumber,WorkerName,Name,BookingDate;

    public Orders() {
    }

    public Orders(String bookingId, String address, String mobileNumber, String paymentMethod, String userId, String slot, String workerMobileNumber, String workerName, String name, String bookingDate) {
        BookingId = bookingId;
        Address = address;
        MobileNumber = mobileNumber;
        PaymentMethod = paymentMethod;
        UserId = userId;
        Slot = slot;
        WorkerMobileNumber = workerMobileNumber;
        WorkerName = workerName;
        Name = name;
        BookingDate = bookingDate;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSlot() {
        return Slot;
    }

    public void setSlot(String slot) {
        Slot = slot;
    }

    public String getWorkerMobileNumber() {
        return WorkerMobileNumber;
    }

    public void setWorkerMobileNumber(String workerMobileNumber) {
        WorkerMobileNumber = workerMobileNumber;
    }

    public String getWorkerName() {
        return WorkerName;
    }

    public void setWorkerName(String workerName) {
        WorkerName = workerName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }
}
