package com.example.user.mp_salesperson.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubcatimageFragment extends Fragment {



    private ArrayList<ItemList> itemListArrayList;
    ImageView ivItemImage;
    private TextView tvItemName;
    String name, image;
    Bundle bundle = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        image = getArguments().getString("image");
        System.out.println("name11" + name);
        System.out.println("name12" + image);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_subcatimage, container, false);
        ivItemImage = (ImageView) view.findViewById(R.id.item_row_item_logo_iv);
        tvItemName = (TextView)view. findViewById(R.id.itemlist_item_name);


 Picasso.with(getActivity()).load(image).resize(400, 400).into(ivItemImage);
        //  Picasso.with(context).load(R.drawable.shampoo_and_conditioner).into( ivItemImage);
        tvItemName.setText(name);

   return view;

    }


}