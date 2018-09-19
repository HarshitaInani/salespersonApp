package com.example.user.mp_salesperson.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.shopbybrandbean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by User on 11-08-2018.
 */

public class ShopbycompanyAdapter extends RecyclerView.Adapter<ShopbycompanyAdapter.ViewHolder> {

    ArrayList<shopbybrandbean> mshopbrand;
    private Context context;
    int ivHeight;
    int ivWidth;
    FragmentManager fragmentManager;



    public ShopbycompanyAdapter(Context context, int rowIMageHeight, int rowIMageWidth, FragmentManager fragmentManager, ArrayList<shopbybrandbean> mshopbrand) {

        this.context = context;
        this.ivHeight = rowIMageHeight;
        this.ivWidth = rowIMageWidth;
        this.fragmentManager = fragmentManager;
        this.mshopbrand=mshopbrand;



    }



    @Override
    public ShopbycompanyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_frag_recycler_row3, parent, false);
        return new ShopbycompanyAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ShopbycompanyAdapter.ViewHolder holder,  final int i) {
        holder.tv_android.setText(mshopbrand.get(i).getSubcategoryName());
        if (!TextUtils.isNullOrEmpty(mshopbrand.get(i).getLogoUrl())) {
            Picasso.with(context).load(mshopbrand.get(i).getLogoUrl()).resize(ivHeight, ivWidth).into(holder.img_android);
        }
        else {
            Picasso.with(context).load(Constant.BASE_URL_Images + "images/catimages/" + mshopbrand.get(i).getCategoryid() + ".png").resize(ivWidth, ivHeight).into(holder.img_android);
        }
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(context, Constant.RETAILER_BEAN_PREF, context.MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
    holder.mRowRl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Fragment fragment = Fragment.instantiate(context,SubsubBrands.class);
            Bundle args = new Bundle();
            //args.putInt("baseCategoryId", Integer.parseInt(mPopularBrandBeenArrayList.get(i - mBaseCatSubCatBean.getmBaseCatBeanArrayList().size()).getCategoryid()));
            args.putString("Warehouseid", (mRetailerBean.getWarehouseid()));
            args.putString("SubcategoryName",(mshopbrand.get(i).getSubsubcategoryName()));
            /*args.putInt("selectedWarId", Integer.parseInt(mshopbrand.getWarehouseid()));*/
            args.putString("Categoryid",mshopbrand.get(i).getCategoryid());

           // fragment.setArguments(args);
          //  fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();

        }
    });

    }


    @Override
    public int getItemCount() {
        return mshopbrand.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android;
        private ImageView img_android;
        private RelativeLayout mRowRl;


        public ViewHolder(View itemView) {
            super(itemView);
            mRowRl = (RelativeLayout) itemView.findViewById(R.id.home_frag_row);
            tv_android = (TextView) itemView.findViewById(R.id.tv_android);
            img_android = (ImageView) itemView.findViewById(R.id.img_android);
        }
    }
}
