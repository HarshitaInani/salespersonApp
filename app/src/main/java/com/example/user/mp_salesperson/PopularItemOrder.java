package com.example.user.mp_salesperson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class PopularItemOrder extends AppCompatActivity {


    private ImageView ivItemImage;
    private TextView tvItemName;
    private TextView tvMoqMrp;
    private TextView tvRateMargins;
    private TextView tvSelectedItemPrice;
    private TextView tvselectedItemQuantity;
    private ImageView ivMinusImage;
    private ImageView ivPlusImage;
    private TextView tvItemHindiName;
    private TextView tvDpPoint;
    private TextView tvSelectUnitPrice;
    private Spinner spSelectItemName;
    RelativeLayout rl1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_item_order);
        init();

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(PopularItemOrder.this, CartActivity.class));
            }
        });
    }

    private void init() {
        rl1=(RelativeLayout)findViewById(R.id.rl1);
        ivItemImage = (ImageView)findViewById(R.id.item_row_item_logo_iv);
        tvItemName = (TextView)findViewById(R.id.itemlist_item_name);
        tvItemHindiName = (TextView)findViewById(R.id.itemlist_item_name_hindi);
        tvDpPoint = (TextView)findViewById(R.id.dp_point);
        tvMoqMrp = (TextView)findViewById(R.id.moq_mrp_tv);
        tvRateMargins = (TextView)findViewById(R.id.cost_margins_tv);
        tvSelectedItemPrice = (TextView)findViewById(R.id.item_list_row_total_cost_tv);
        tvselectedItemQuantity = (TextView)findViewById(R.id.item_row_quantity_tv);
        ivMinusImage = (ImageView)findViewById(R.id.item_row_minus_icon);
        ivPlusImage = (ImageView)findViewById(R.id.item_row_plus_icon);
        spSelectItemName=(Spinner)findViewById(R.id.spinner_itemname);
        tvSelectUnitPrice = (TextView)findViewById(R.id.unit_price);
    }
}
