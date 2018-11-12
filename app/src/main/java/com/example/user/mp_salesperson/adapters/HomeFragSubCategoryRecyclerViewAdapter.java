package com.example.user.mp_salesperson.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.CategoryBean;
import com.example.user.mp_salesperson.fragment.HomeFragItemList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Krishna on 29-12-2016.
 */

public class HomeFragSubCategoryRecyclerViewAdapter extends RecyclerView.Adapter<HomeFragSubCategoryRecyclerViewAdapter.ViewHolder> {
    private ArrayList<CategoryBean> mCategoryBeanArrayList;
    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;

    public HomeFragSubCategoryRecyclerViewAdapter(Context context, ArrayList<CategoryBean> mCategoryBeanArrayList, int ivHeight, int ivWidth, FragmentManager fragmentManager) {
        this.mCategoryBeanArrayList = mCategoryBeanArrayList;
        this.context = context;
        this.ivHeight = ivHeight;
        this.ivWidth = ivWidth;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public HomeFragSubCategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_frag_inside_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeFragSubCategoryRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
       // System.out.println("Imagesssss::"+Constant.BASE_URL_Images + "images/catimages/" + mCategoryBeanArrayList.get(i).getCategoryid() + ".png");
        viewHolder.tv_android.setText(mCategoryBeanArrayList.get(i).getCategoryName());
        if (!TextUtils.isNullOrEmpty(mCategoryBeanArrayList.get(i).getLogoUrl()))
            Picasso.with(context).load(mCategoryBeanArrayList.get(i).getLogoUrl()).resize(ivWidth, ivHeight).into(viewHolder.img_android);
        else
            Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mCategoryBeanArrayList.get(i).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(viewHolder.img_android);
        viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                Fragment fragment = android.support.v4.app.Fragment.instantiate(context, HomeFragItemList.class.getName());
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
    public class ViewHolder extends RecyclerView.ViewHolder {
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
