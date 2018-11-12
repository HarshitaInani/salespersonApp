package com.example.user.mp_salesperson.bean;

/**
 * Created by Krishna on 12/25/2016.
 */

public class RetailerBean {
    String customerId;
    String customerCategoryId;
    String Skcode;
    String ShopName;
    String Warehouseid;
    String Mobile;
    String WarehouseName;
    String Name;
    String Password;
    String Description;
    String CustomerType;
    String CustomerCategoryName;
    String CompanyId;
    String BillingAddress;
    String TypeOfBuissness;
    String ShippingAddress;
    String LandMark;
    String Country;
    String State;
    String Cityid;
    String City;
    String ZipCode;
    String BAGPSCoordinates;
    String SAGPSCoordinates;
    String RefNo;
    String OfficePhone;
    String Emailid;
    String Familymember;
    String CreatedBy;
    String LastModifiedBy;
    String CreatedDate;
    String UpdatedDate;
    String imei;
    String MonthlyTurnOver;
    String ExecutiveId;
    String SizeOfShop;
    String Rating;
    String ClusterId;
    String Deleted;
    String Active;
    String GroupNotification;
    String Notifications;
    String Exception;
    String DivisionId;
    String Rewardspoints;


    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
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

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public RetailerBean(String customerId, String customerCategoryId, String skcode, String shopName, String warehouseid, String mobile, String warehouseName, String name, String password, String description, String customerType, String customerCategoryName, String companyId, String billingAddress, String typeOfBuissness, String shippingAddress, String landMark, String country, String state, String cityid, String city, String zipCode, String BAGPSCoordinates, String SAGPSCoordinates, String refNo, String officePhone, String emailid, String familymember, String createdBy, String lastModifiedBy, String createdDate, String updatedDate, String imei, String monthlyTurnOver, String executiveId, String sizeOfShop, String rating, String clusterId, String deleted, String active, String groupNotification, String notifications, String exception, String divisionId, String rewardspoints, String peopleId, String companyId1, String warehouseId, String peopleFirstName, String peopleLastName, String email, String displayName, String country1, String stateId, String state1, String cityId, String city1, String mobile1, String password1, String roleId, String department, String billableRate, String costRate, String permissons, String type, String imageUrl, String deleted1, String emailConfirmed, String approved, String active1, String createdDate1, String updatedDate1, String createdBy1, String updatedBy, String vehicleId, String vehicleName, String vehicleNumber, String vehicleCapacity) {
        this.customerId = customerId;
        this.customerCategoryId = customerCategoryId;
        Skcode = skcode;
        ShopName = shopName;
        Warehouseid = warehouseid;
        Mobile = mobile;
        WarehouseName = warehouseName;
        Name = name;
        Password = password;
        Description = description;
        CustomerType = customerType;
        CustomerCategoryName = customerCategoryName;
        CompanyId = companyId;
        BillingAddress = billingAddress;
        TypeOfBuissness = typeOfBuissness;
        ShippingAddress = shippingAddress;
        LandMark = landMark;
        Country = country;
        State = state;
        Cityid = cityid;
        City = city;
        ZipCode = zipCode;
        this.BAGPSCoordinates = BAGPSCoordinates;
        this.SAGPSCoordinates = SAGPSCoordinates;
        RefNo = refNo;
        OfficePhone = officePhone;
        Emailid = emailid;
        Familymember = familymember;
        CreatedBy = createdBy;
        LastModifiedBy = lastModifiedBy;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        this.imei = imei;
        MonthlyTurnOver = monthlyTurnOver;
        ExecutiveId = executiveId;
        SizeOfShop = sizeOfShop;
        Rating = rating;
        ClusterId = clusterId;
        Deleted = deleted;
        Active = active;
        GroupNotification = groupNotification;
        Notifications = notifications;
        Exception = exception;
        DivisionId = divisionId;
        Rewardspoints = rewardspoints;
        this.peopleId = peopleId;
        this.companyId = companyId1;
        this.warehouseId = warehouseId;
        this.peopleFirstName = peopleFirstName;
        this.peopleLastName = peopleLastName;
        this.email = email;
        this.displayName = displayName;
        this.country = country1;
        this.stateId = stateId;
        this.state = state1;
        this.cityId = cityId;
        this.city = city1;
        this.mobile = mobile1;
        this.password = password1;
        this.roleId = roleId;
        this.department = department;
        this.billableRate = billableRate;
        this.costRate = costRate;
        this.permissons = permissons;
        this.type = type;
        this.imageUrl = imageUrl;
        this.deleted = deleted1;
        this.emailConfirmed = emailConfirmed;
        Approved = approved;
        this.active = active1;
        this.createdDate = createdDate1;
        this.updatedDate = updatedDate1;
        this.createdBy = createdBy1;
        this.updatedBy = updatedBy;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleCapacity = vehicleCapacity;
    }

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




    public RetailerBean(String customerId, String customerCategoryId, String skcode, String shopName, String warehouseid, String mobile, String warehouseName, String name, String password, String description, String customerType, String customerCategoryName, String companyId, String billingAddress, String typeOfBuissness, String shippingAddress, String landMark, String country, String state, String cityid, String city, String zipCode, String BAGPSCoordinates, String SAGPSCoordinates, String refNo, String officePhone, String emailid, String familymember, String createdBy, String lastModifiedBy, String createdDate, String updatedDate, String imei, String monthlyTurnOver, String executiveId, String sizeOfShop, String rating, String clusterId, String deleted, String active, String groupNotification, String notifications, String exception, String divisionId, String rewardspoints) {
        this.customerId = customerId;
        this.customerCategoryId = customerCategoryId;
        Skcode = skcode;
        ShopName = shopName;
        Warehouseid = warehouseid;
        Mobile = mobile;
        WarehouseName = warehouseName;
        Name = name;
        Password = password;
        Description = description;
        CustomerType = customerType;
        CustomerCategoryName = customerCategoryName;
        CompanyId = companyId;
        BillingAddress = billingAddress;
        TypeOfBuissness = typeOfBuissness;
        ShippingAddress = shippingAddress;
        LandMark = landMark;
        Country = country;
        State = state;
        Cityid = cityid;
        City = city;
        ZipCode = zipCode;
        this.BAGPSCoordinates = BAGPSCoordinates;
        this.SAGPSCoordinates = SAGPSCoordinates;
        RefNo = refNo;
        OfficePhone = officePhone;
        Emailid = emailid;
        Familymember = familymember;
        CreatedBy = createdBy;
        LastModifiedBy = lastModifiedBy;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        this.imei = imei;
        MonthlyTurnOver = monthlyTurnOver;
        ExecutiveId = executiveId;
        SizeOfShop = sizeOfShop;
        Rating = rating;
        ClusterId = clusterId;
        Deleted = deleted;
        Active = active;
        GroupNotification = groupNotification;
        Notifications = notifications;
        Exception = exception;
        DivisionId = divisionId;
        Rewardspoints = rewardspoints;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCategoryId() {
        return customerCategoryId;
    }

    public void setCustomerCategoryId(String customerCategoryId) {
        this.customerCategoryId = customerCategoryId;
    }

    public String getSkcode() {
        return Skcode;
    }

    public void setSkcode(String skcode) {
        Skcode = skcode;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getWarehouseid() {
        return Warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        Warehouseid = warehouseid;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getWarehouseName() {
        return WarehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        WarehouseName = warehouseName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getCustomerCategoryName() {
        return CustomerCategoryName;
    }

    public void setCustomerCategoryName(String customerCategoryName) {
        CustomerCategoryName = customerCategoryName;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        BillingAddress = billingAddress;
    }

    public String getTypeOfBuissness() {
        return TypeOfBuissness;
    }

    public void setTypeOfBuissness(String typeOfBuissness) {
        TypeOfBuissness = typeOfBuissness;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getLandMark() {
        return LandMark;
    }

    public void setLandMark(String landMark) {
        LandMark = landMark;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCityid() {
        return Cityid;
    }

    public void setCityid(String cityid) {
        Cityid = cityid;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getBAGPSCoordinates() {
        return BAGPSCoordinates;
    }

    public void setBAGPSCoordinates(String BAGPSCoordinates) {
        this.BAGPSCoordinates = BAGPSCoordinates;
    }

    public String getSAGPSCoordinates() {
        return SAGPSCoordinates;
    }

    public void setSAGPSCoordinates(String SAGPSCoordinates) {
        this.SAGPSCoordinates = SAGPSCoordinates;
    }

    public String getRefNo() {
        return RefNo;
    }

    public void setRefNo(String refNo) {
        RefNo = refNo;
    }

    public String getOfficePhone() {
        return OfficePhone;
    }

    public void setOfficePhone(String officePhone) {
        OfficePhone = officePhone;
    }

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getFamilymember() {
        return Familymember;
    }

    public void setFamilymember(String familymember) {
        Familymember = familymember;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getLastModifiedBy() {
        return LastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        LastModifiedBy = lastModifiedBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMonthlyTurnOver() {
        return MonthlyTurnOver;
    }

    public void setMonthlyTurnOver(String monthlyTurnOver) {
        MonthlyTurnOver = monthlyTurnOver;
    }

    public String getExecutiveId() {
        return ExecutiveId;
    }

    public void setExecutiveId(String executiveId) {
        ExecutiveId = executiveId;
    }

    public String getSizeOfShop() {
        return SizeOfShop;
    }

    public void setSizeOfShop(String sizeOfShop) {
        SizeOfShop = sizeOfShop;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getClusterId() {
        return ClusterId;
    }

    public void setClusterId(String clusterId) {
        ClusterId = clusterId;
    }

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String deleted) {
        Deleted = deleted;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getGroupNotification() {
        return GroupNotification;
    }

    public void setGroupNotification(String groupNotification) {
        GroupNotification = groupNotification;
    }

    public String getNotifications() {
        return Notifications;
    }

    public void setNotifications(String notifications) {
        Notifications = notifications;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }

    public String getDivisionId() {
        return DivisionId;
    }

    public void setDivisionId(String divisionId) {
        DivisionId = divisionId;
    }

    public String getRewardspoints() {
        return Rewardspoints;
    }

    public void setRewardspoints(String rewardspoints) {
        Rewardspoints = rewardspoints;
    }
}
