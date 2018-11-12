package com.example.user.mp_salesperson.bean;

/**
 * Created by User on 3/10/2017.
 */

public class RetailerBeanSeller {
/*
    {
        "PeopleID": 2,
            "CompanyID": 1,
            "Warehouseid": 1,
            "PeopleFirstName": "Tanutejas",
            "PeopleLastName": "S",
            "Email": "tejas@shopkirana.com",
            "DisplayName": "Tanutejas S",
            "Country": null,
            "Stateid": 1,
            "state": "MP",
            "Cityid": 1,
            "city": "Indore",
            "Mobile": "9039006314",
            "Password": "123456",
            "RoleId": null,
            "Department": "Sales Executive",
            "BillableRate": 0,
            "CostRate": null,
            "Permissions": "Regular User",
            "Type": "Sales Executive",
            "ImageUrl": null,
            "Deleted": false,
            "EmailConfirmed": false,
            "Approved": false,
            "Active": true,
            "CreatedDate": "2016-12-28T19:38:20.457+05:30",
            "UpdatedDate": "2016-12-28T21:12:26.783+05:30",
            "CreatedBy": null,
            "UpdateBy": null,
            "VehicleId": 0,
            "VehicleName": null,
            "VehicleNumber": null,
            "VehicleCapacity": 0
    }
    */


    String peopleId;
    String companyId;
    String warehouseId;
    String peopleFirstName;
    String peopleLastName;
    String email;
    String displayName;
    String country;
    String stateId;
    String state;
    String cityId;
    String city;
    String mobile;
    String password;
    String roleId;
    String department;
    String billableRate;
    String costRate;
    String permissons;
    String type;
    String imageUrl;
    String deleted;
    String emailConfirmed;
    String Approved;
    String active;
    String createdDate;
    String updatedDate;
    String createdBy;
    String updatedBy;
    String vehicleId;
    String vehicleName;
    String vehicleNumber;
    String vehicleCapacity;


    public RetailerBeanSeller(String peopleId, String companyId, String warehouseId, String peopleFirstName, String peopleLastName, String email, String displayName, String country, String stateId, String state, String cityId, String city, String mobile, String password, String roleId, String department, String billableRate, String costRate, String permissons, String type, String imageUrl, String deleted, String emailConfirmed, String approved, String active, String createdDate, String updatedDate, String createdBy, String updatedBy, String vehicleId, String vehicleName, String vehicleNumber, String vehicleCapacity) {
        this.peopleId = peopleId;
        this.companyId = companyId;
        this.warehouseId = warehouseId;
        this.peopleFirstName = peopleFirstName;
        this.peopleLastName = peopleLastName;
        this.email = email;
        this.displayName = displayName;
        this.country = country;
        this.stateId = stateId;
        this.state = state;
        this.cityId = cityId;
        this.city = city;
        this.mobile = mobile;
        this.password = password;
        this.roleId = roleId;
        this.department = department;
        this.billableRate = billableRate;
        this.costRate = costRate;
        this.permissons = permissons;
        this.type = type;
        this.imageUrl = imageUrl;
        this.deleted = deleted;
        this.emailConfirmed = emailConfirmed;
        Approved = approved;
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleCapacity = vehicleCapacity;
    }


    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getPeopleFirstName() {
        return peopleFirstName;
    }

    public void setPeopleFirstName(String peopleFirstName) {
        this.peopleFirstName = peopleFirstName;
    }

    public String getPeopleLastName() {
        return peopleLastName;
    }

    public void setPeopleLastName(String peopleLastName) {
        this.peopleLastName = peopleLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBillableRate() {
        return billableRate;
    }

    public void setBillableRate(String billableRate) {
        this.billableRate = billableRate;
    }

    public String getCostRate() {
        return costRate;
    }

    public void setCostRate(String costRate) {
        this.costRate = costRate;
    }

    public String getPermissons() {
        return permissons;
    }

    public void setPermissons(String permissons) {
        this.permissons = permissons;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(String emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(String vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }
}
