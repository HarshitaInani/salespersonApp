package com.example.user.mp_salesperson.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.BaseCatSubCatBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.CategoryBean;
import com.example.user.mp_salesperson.fragment.HomeFragItemList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by User on 08-08-2018.
 */

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {
    private ArrayList<CategoryBean> mCategoryBeanArrayList;
    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;
    private BaseCatSubCatBean mBaseCatSubCatBean;
    public GroceryAdapter(Context context, BaseCatSubCatBean mBaseCatSubCatBean, ArrayList<CategoryBean> mCategoryBeanArrayList, int ivHeight, int ivWidth, FragmentManager fragmentManager) {
        this.mCategoryBeanArrayList = mCategoryBeanArrayList;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
        this.mBaseCatSubCatBean = mBaseCatSubCatBean;
    }



    @Override
    public GroceryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_recycler_row2, viewGroup, false);
        return new GroceryAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(GroceryAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_android.setText(mCategoryBeanArrayList.get(i).getCategoryName());
        // System.out.println("Logo Url::"+mCategoryBeanArrayList.get(i).getLogoUrl());
        // System.out.println("Logo Url1::"+Constant.BASE_URL_Images + "images/catimages/" + mCategoryBeanArrayList.get(i).getCategoryid() + ".png");
        if (!TextUtils.isNullOrEmpty(mCategoryBeanArrayList.get(i).getLogoUrl()))

            Picasso.with(context).load(mCategoryBeanArrayList.get(i).getLogoUrl()).into(viewHolder.img_android);
        else
            Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mCategoryBeanArrayList.get(i).getCategoryid() + ".png").into(viewHolder.img_android);
        viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {

                Fragment fragment = Fragment.instantiate(context, HomeFragItemList.class.getName());
                Bundle args = new Bundle();
                args.putInt("selectedCategoryId", Integer.parseInt(mCategoryBeanArrayList.get(i).getCategoryid()));
                args.putInt("selectedSubCatId", -1);
//                args.putInt("selectedWarId", Integer.parseInt(mCategoryBeanArrayList.get(i).getWarehouseid()));
                args.putInt("selectedWarId", 1);


                fragment.setArguments(args);
                fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryBeanArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_android;
        private ImageView img_android;
        private RelativeLayout mRowRl;

        public ViewHolder(View view) {
            super(view);
            mRowRl = (RelativeLayout) view.findViewById(R.id.home_frag_row);
            tv_android = (TextView) view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}
