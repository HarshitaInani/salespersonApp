package com.example.user.mp_salesperson.bean;

import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.SubSubCategoriesBean;

import java.util.ArrayList;


/**
 * Created by Krishna on 03-01-2017.
 */

public class SubSubCatItemListBean {
    ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeen;
    ArrayList<ItemList> mItemLists;
    ArrayList<ItemList> mItemRemoveLists;
    public SubSubCatItemListBean(ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeen, ArrayList<ItemList> mItemLists,ArrayList<ItemList> mItemRemoveLists) {
        this.mSubSubCategoriesBeen = mSubSubCategoriesBeen;
        this.mItemLists = mItemLists;
        this.mItemRemoveLists = mItemRemoveLists;
    }

    public ArrayList<ItemList> getmItemRemoveLists() {
        return mItemRemoveLists;
    }

    public void setmItemRemoveLists(ArrayList<ItemList> mItemRemoveLists) {
        this.mItemRemoveLists = mItemRemoveLists;
    }

    public ArrayList<SubSubCategoriesBean> getmSubSubCategoriesBeen() {
        return mSubSubCategoriesBeen;
    }

    public void setmSubSubCategoriesBeen(ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeen) {
        this.mSubSubCategoriesBeen = mSubSubCategoriesBeen;
    }

    public ArrayList<ItemList> getmItemLists() {
        return mItemLists;
    }

    public void setmItemLists(ArrayList<ItemList> mItemLists) {
        this.mItemLists = mItemLists;
    }
}
