package com.example.user.mp_salesperson.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.mp_salesperson.MyOrderDetails;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.MyOderBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Krishna on 29-12-2016.
 */

public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {


    private ArrayList<MyOderBean> myOderBeanArrayList;
    private Context context;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public MyOrderRecyclerViewAdapter(Context context, ArrayList<MyOderBean> myOderBeanArrayList) {
        this.myOderBeanArrayList = myOderBeanArrayList;
        this.context = context;
    }

    @Override
    public MyOrderRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_orders_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        try {

            viewHolder.tvOrderNumber.setText("Order No: " + myOderBeanArrayList.get(i).getOrderId());
            viewHolder.tvOrderShopName.setText("Shop Name: " + myOderBeanArrayList.get(i).getShopName());
            viewHolder.tvOrderSKCode.setText("SK Code: " + myOderBeanArrayList.get(i).getSkcode());
            viewHolder.tvAmount.setText("Amount: " + myOderBeanArrayList.get(i).getTotalAmount());
            viewHolder.tvDeliveryCharges.setText("Delivery Charges: " + myOderBeanArrayList.get(i).getDeliveryCharge());
            viewHolder.tvOrderDate.setText("Order Date: " + mSimpleDateFormat.format(sdf.parse(myOderBeanArrayList.get(i).getCreatedDate())));
            viewHolder.tvApproxDeliverDate.setText("Approx Delivery Date: " + mSimpleDateFormat.format(sdf.parse(myOderBeanArrayList.get(i).getDeliverydate())));


          viewHolder.tvDpPoints.setText("DP points: "+myOderBeanArrayList.get(i).getDreamPoints());


            System.out.println("Status:::"+myOderBeanArrayList.get(i).getStatus());

            viewHolder.ivOrdered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_1));
            viewHolder.ivDispached.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_2));
            viewHolder.ivDelivered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_3));
            if (myOderBeanArrayList.get(i).getStatus().equalsIgnoreCase("Pending")) {
                viewHolder.ivOrdered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_1));
            } else if (myOderBeanArrayList.get(i).getStatus().equalsIgnoreCase("Shipped")) {
                viewHolder.ivDispached.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_selecte_2));
            } else if (myOderBeanArrayList.get(i).getStatus().equalsIgnoreCase("Delivered")) {
                viewHolder.ivDispached.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_selecte_2));
                viewHolder.ivDelivered.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.icn_selecte_3));
            }
            viewHolder.mRowRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, MyOrderDetails.class).putExtra("selectedPosition", i));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return myOderBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mRowRl;
        private TextView tvOrderNumber;
        private TextView tvOrderShopName;
        private TextView tvOrderSKCode;
        private TextView tvAmount;
        private TextView tvDeliveryCharges;
        private TextView tvOrderDate;
        private TextView tvApproxDeliverDate;

        private TextView tvDpPoints;


        ImageView ivOrdered;
        ImageView ivDispached;
        ImageView ivDelivered;



        public ViewHolder(View view) {
            super(view);
            mRowRl = (LinearLayout) view.findViewById(R.id.row_rl);
            tvOrderNumber = (TextView) view.findViewById(R.id.my_order_order_no);
            tvOrderShopName = (TextView) view.findViewById(R.id.my_order_shop_name);
            tvOrderSKCode = (TextView) view.findViewById(R.id.my_order_sk_code);
            tvOrderNumber = (TextView) view.findViewById(R.id.my_order_order_no);
            tvAmount = (TextView) view.findViewById(R.id.my_order_amount);
            tvDeliveryCharges = (TextView) view.findViewById(R.id.my_order_delivery_charges);
            tvOrderDate = (TextView) view.findViewById(R.id.my_order_order_date);
            tvApproxDeliverDate = (TextView) view.findViewById(R.id.my_order_approx_deliver_date);

           tvDpPoints = (TextView) view.findViewById(R.id.my_order_dp_points);

            ivOrdered = (ImageView) view.findViewById(R.id.my_order_iv_ordered);
            ivDispached = (ImageView) view.findViewById(R.id.my_order_iv_dispached);
            ivDelivered = (ImageView) view.findViewById(R.id.my_order_iv_delivered);

        }
    }

}
