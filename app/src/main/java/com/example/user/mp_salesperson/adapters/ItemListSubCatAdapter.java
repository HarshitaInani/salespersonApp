package com.example.user.mp_salesperson.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;
import com.example.user.mp_salesperson.interfaces.ItemListSubCatAdapterInterface;

import java.util.ArrayList;

/**
 * Created by Krishna on 03-01-2017.
 */

public class ItemListSubCatAdapter extends RecyclerView.Adapter<ItemListSubCatAdapter.ViewHolder> {
    private ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList;
    private Context context;
    private TextView lastitemListSelectdSubCatTv;
    private ItemListSubCatAdapterInterface mItemListSubCatAdapterInterface;
    public ItemListSubCatAdapter(Context context, ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList, ItemListSubCatAdapterInterface mItemListSubCatAdapterInterface) {
        this.mSubCategoriesBeanArrayList = mSubCategoriesBeanArrayList;
        this.context = context;
        this.mItemListSubCatAdapterInterface = mItemListSubCatAdapterInterface;
    }

    @Override
    public ItemListSubCatAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_sub_cat_adapter_row, viewGroup, false);
        return new ItemListSubCatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemListSubCatAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_sub_cat_adpt_tv.setText(mSubCategoriesBeanArrayList.get(i).getSubcategoryName());
        if (lastitemListSelectdSubCatTv == null) {
            viewHolder.tv_sub_cat_adpt_tv.setSelected(true);
            lastitemListSelectdSubCatTv = viewHolder.tv_sub_cat_adpt_tv;
            mItemListSubCatAdapterInterface.subcategorySelected(mSubCategoriesBeanArrayList.get(i).getSubCategoryId());
        }
        viewHolder.tv_sub_cat_adpt_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastitemListSelectdSubCatTv != null)
                    lastitemListSelectdSubCatTv.setSelected(false);
                viewHolder.tv_sub_cat_adpt_tv.setSelected(true);
                lastitemListSelectdSubCatTv = viewHolder.tv_sub_cat_adpt_tv;
                mItemListSubCatAdapterInterface.subcategorySelected(mSubCategoriesBeanArrayList.get(i).getSubCategoryId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubCategoriesBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sub_cat_adpt_tv;

        public ViewHolder(View view) {
            super(view);
            tv_sub_cat_adpt_tv = (TextView) view.findViewById(R.id.sub_sub_cat_adpt_tv);
        }
    }
}
