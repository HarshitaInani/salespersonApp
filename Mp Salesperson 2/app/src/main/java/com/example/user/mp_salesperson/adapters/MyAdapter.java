package com.example.user.mp_salesperson.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;

import java.text.DecimalFormat;
import java.util.ArrayList;



/**
 * Created by User on 21-12-2017.
 */

public class MyAdapter extends BaseAdapter {

    Context c;
    ArrayList<ItemList> mitemList;

    public MyAdapter(Context context, ArrayList<ItemList> mitemList) {
        super();
        this.c = context;
        this.mitemList = mitemList;
    }

    @Override
    public int getCount() {
        return mitemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mitemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemList mItemListPojo = mitemList.get(position);
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View row = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView label = (TextView) row.findViewById(R.id.moq);
        TextView price = (TextView) row.findViewById(R.id.rs);
        TextView margin = (TextView) row.findViewById(R.id.margin);
        /*label.setText("MOQ "+mItemListPojo.getMinOrderQty());
        price.setText("RS "+ mItemListPojo.getUnitPrice());
        String text1 =*//* "<font color=#000> " + *//* "  Margins: " + (new DecimalFormat("##.##").format((((Double.parseDouble(mItemListPojo.getPrice()) - Double.parseDouble(mItemListPojo.getUnitPrice())) / Double.parseDouble(mItemListPojo.getPrice())) * 100))) + "%";
        margin.setText(  Html.fromHtml(text1));*/
        label.setText(mItemListPojo.getMinOrderQty());
        price.setText( mItemListPojo.getUnitPrice());
        String text1 = (new DecimalFormat("##.##").format((((Double.parseDouble(mItemListPojo.getPrice()) - Double.parseDouble(mItemListPojo.getUnitPrice())) / Double.parseDouble(mItemListPojo.getUnitPrice())) * 100))) + "%";
        margin.setText(  Html.fromHtml(text1));


        return row;


    }
}
