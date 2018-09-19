package com.example.user.mp_salesperson.bean;

import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.CategoryBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;

import java.util.ArrayList;


/**
 * Created by Krishna on 30-12-2016.
 */

public class BaseCatSubCatBean {
    ArrayList<BaseCatBean> mBaseCatBeanArrayList;
    ArrayList<CategoryBean> mCategoryBeanArrayList;
    ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList;

    public BaseCatSubCatBean(ArrayList<BaseCatBean> mBaseCatBeanArrayList, ArrayList<CategoryBean> mCategoryBeanArrayList, ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList) {
        this.mBaseCatBeanArrayList = mBaseCatBeanArrayList;
        this.mCategoryBeanArrayList = mCategoryBeanArrayList;
        this.mSubCategoriesBeanArrayList = mSubCategoriesBeanArrayList;
    }

    public ArrayList<BaseCatBean> getmBaseCatBeanArrayList() {
        return mBaseCatBeanArrayList;
    }

    public void setmBaseCatBeanArrayList(ArrayList<BaseCatBean> mBaseCatBeanArrayList) {
        this.mBaseCatBeanArrayList = mBaseCatBeanArrayList;
    }

    public ArrayList<CategoryBean> getmCategoryBeanArrayList() {
        return mCategoryBeanArrayList;
    }

    public void setmCategoryBeanArrayList(ArrayList<CategoryBean> mCategoryBeanArrayList) {
        this.mCategoryBeanArrayList = mCategoryBeanArrayList;
    }

    public ArrayList<SubCategoriesBean> getmSubCategoriesBeanArrayList() {
        return mSubCategoriesBeanArrayList;
    }

    public void setmSubCategoriesBeanArrayList(ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList) {
        this.mSubCategoriesBeanArrayList = mSubCategoriesBeanArrayList;
    }
}
