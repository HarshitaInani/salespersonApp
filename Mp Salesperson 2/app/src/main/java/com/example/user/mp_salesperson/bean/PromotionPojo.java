package com.example.user.mp_salesperson.bean;

/**
 * Created by User on 9/12/2017.
 */

public class PromotionPojo {

    String Id;
    String Name;
    String Discription;
    String LogoUrl;
    public PromotionPojo(String id, String name, String discription, String logoUrl) {
        this.Id = id;
        this.Name = name;
        this.Discription = discription;
        this.LogoUrl = logoUrl;

    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        LogoUrl = logoUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }


}