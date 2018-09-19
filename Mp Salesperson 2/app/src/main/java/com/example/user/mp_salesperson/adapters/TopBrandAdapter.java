package com.example.user.mp_salesperson.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.TopList;


import java.util.List;


/**
 * Created by Himanshu on 11/24/2016.
 */

public class TopBrandAdapter extends RecyclerView.Adapter<TopBrandAdapter.ViewHolder> {

   // private ImageLoader imageLoader;
    private Context context;
    private RecyclerView.Adapter adapter;

    Typeface face;
    //List of superHeroes
    List<TopList> listItem;

    private String TAG = "like";
    String PostId="";

    private RecyclerView recyclerView;
    public TopBrandAdapter(List<TopList> listItem, Context context){
        super();
        //Getting all the superheroes

        this.listItem = listItem;
        this.context = context;
//        face = Typeface.createFromAsset(context.getAssets(), "fonts/33535gillsansmt_0.ttf");
    }

    @Override
    public TopBrandAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_adapter, parent, false);
        TopBrandAdapter.ViewHolder viewHolder = new TopBrandAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TopBrandAdapter.ViewHolder holder, final int position) {

        final TopList  pojo =  listItem.get(position);


        holder.SubCategoryName.setText("Name:  "+pojo.getSubsubcategoryName());
       holder.TotalAmount.setText("Total Amount:  "+pojo.getTotalAmt());


        //holder.TitleNotification.setTypeface(face);
        //holder.NoticeTime.setTypeface(face);



    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

       // public NetworkImageView networkImageViewImageEngineer;

        public TextView SubCategoryName;
        public TextView TotalAmount;


        public ViewHolder(View itemView) {
            super(itemView);
           // networkImageViewImageEngineer=(NetworkImageView)itemView.findViewById(R.id.niv_pimage);

            SubCategoryName = (TextView) itemView.findViewById(R.id.SubCategoryName);
            TotalAmount = (TextView) itemView.findViewById(R.id.TotalAmount);

        }
    }


}