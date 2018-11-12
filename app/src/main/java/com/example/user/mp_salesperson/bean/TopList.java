package com.example.user.mp_salesperson.bean;

/**
 * Created by Krishna on 03-01-2017.
 */

public class TopList {


    String CompanyId;
    String WarehouseId;
    String Name;
    String TotalAmt;
    String CreatedDate;
    String Status;
    String SubsubcategoryName ;
    String CategoryName;




    public TopList(String companyId, String wareHouseId, String name, String totalAmt, String createdDate, String status, String subsubcategoryName, String categoryName) {
        this.CompanyId = companyId;
        this.WarehouseId = wareHouseId;
        this.Name = name;
        this.TotalAmt = totalAmt;
        this.CreatedDate = createdDate;
        this.Status = status;
        this.SubsubcategoryName = subsubcategoryName;
        this.CategoryName = categoryName;


    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        WarehouseId = warehouseId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getTotalAmt() {
        return TotalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        TotalAmt = totalAmt;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSubsubcategoryName() {
        return SubsubcategoryName;
    }

    public void setSubsubcategoryName(String subsubcategoryName) {
        SubsubcategoryName = subsubcategoryName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String toString()
    {
        return SubsubcategoryName;
    }

}
