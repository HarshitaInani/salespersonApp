package com.example.user.mp_salesperson.bean;

/**
 * Created by User on 11-08-2018.
 */

public class shopbybrandbean {
    private String LogoUrl, feedName, content;
    private int rating;
    String SubsubCategoryid,SubsubcategoryName,Categoryid,Warehouseid,SubCategoryId,SubcategoryName;
    public shopbybrandbean(String name, String LogoUrl, String SubsubCategoryid, String SubsubcategoryName, String Categoryid, String SubCategoryId ) {
        this.feedName = name;
        this.SubsubCategoryid = SubsubCategoryid;
        this.SubsubcategoryName = SubsubcategoryName;
        this.Categoryid = Categoryid;
        this.Warehouseid = Warehouseid;
        this.SubCategoryId = SubCategoryId;
        this.SubcategoryName = SubcategoryName;
        this.LogoUrl=LogoUrl;
        this.rating = rating;




    }


    public String getSubcategoryName() {
        return SubcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        SubcategoryName = subcategoryName;
    }

    public String getContent() {
        return content;
    }

    public void setLogoUrl(String imgURL) {
        this.LogoUrl = imgURL;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public String getFeedName() {
        return feedName;
    }

    public int getRating() {
        return rating;
    }


    public String getWarehouseid() {
        return Warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        Warehouseid = warehouseid;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getSubsubcategoryName() {
        return SubsubcategoryName;
    }

    public void setSubsubcategoryName(String subsubcategoryName) {
        SubsubcategoryName = subsubcategoryName;
    }

    public String getSubsubCategoryid() {
        return SubsubCategoryid;
    }

    public void setSubsubCategoryid(String subsubCategoryid) {
        SubsubCategoryid = subsubCategoryid;
    }

    public String getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        SubCategoryId = subCategoryId;
    }

}
