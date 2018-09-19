package com.example.user.mp_salesperson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by User on 17-09-2018.
 */

public class subcatimageActivity extends AppCompatActivity {
    private ArrayList<ItemList> itemListArrayList;
    ImageView ivItemImage;
    private TextView tvItemName;
    String name, image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcatimage);
        name = getIntent().getExtras().getString("name");
        image = getIntent().getExtras().getString("image");
        ivItemImage = (ImageView)findViewById(R.id.item_row_item_logo_iv);
        tvItemName = (TextView) findViewById(R.id.itemlist_item_name);
        Picasso.with(this).load(image).resize(400, 400).into(ivItemImage);
        //Picasso.with(context).load(R.drawable.choclates_and_confectionary)/*.resize(300, 300)*/.into( ivItemImage);
        tvItemName.setText(name);

    }
}