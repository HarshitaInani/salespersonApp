package com.example.user.mp_salesperson.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.mp_salesperson.ActionTaskActivity;
import com.example.user.mp_salesperson.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 4/7/2017.
 */

public class ActionTaskAdapter extends RecyclerView.Adapter<ActionTaskAdapter.ViewHolder> {

    private JSONArray jsonArray;
    private JSONArray jsonArrayQ;

    private Context context;
    int count;


    ArrayList<String> isActiveList = new ArrayList<>();
    ArrayList<String> isDeletedList = new ArrayList<>();


    ArrayList<Integer> jsonquantityList = new ArrayList<>();




    ArrayList<String> actionNameList = new ArrayList<>();
    ArrayList<String> actionSkcodeList = new ArrayList<>();
    ArrayList<String> actionList = new ArrayList<>();

    ArrayList<String> actionCId = new ArrayList<>();

    ArrayList<String> actionTaskId = new ArrayList<>();

//    LinkedList<Integer> quantityList = new LinkedList<>();

    //JSONObject qJson = new JSONObject();



    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObjectQ = new JSONObject();


    int quantityCount = 1;


    public ActionTaskAdapter(Context context, JSONArray jsonArray, JSONArray jsonArrayQ, int count) {

        this.context = context;
        this.jsonArray = jsonArray;
        this.count = count;
        this.jsonArrayQ = jsonArrayQ;


    }

    @Override
    public ActionTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.action_task_row, viewGroup, false);
        return new ActionTaskAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ActionTaskAdapter.ViewHolder viewHolder, final int i) {

        //  Toast.makeText(context, "Adapter Array"+jsonArray.toString(), Toast.LENGTH_SHORT).show();


      //  viewHolder.setIsRecyclable(false);

        try {
            for (int j = 0; j < jsonArray.length(); j++) {

                jsonObject = jsonArray.getJSONObject(j);
                isActiveList.add(jsonObject.getString("active"));
                isDeletedList.add(jsonObject.getString("Deleted"));



                actionNameList.add(jsonObject.getString("ShopName"));
                actionList.add(jsonObject.getString("Action"));
                actionSkcodeList.add(jsonObject.getString("Skcode"));

                actionCId.add(jsonObject.getString("CustomerId"));

                actionTaskId.add(jsonObject.getString("ActionTaskid"));


            }

        } catch (Exception e) {

        }






        if (isActiveList.get(i).equals("false")) {

            viewHolder.currentLinearLayout.setVisibility(View.GONE);
            viewHolder.currentLinearLayout.setMinimumHeight(0);

            viewHolder.cardView.setVisibility(View.GONE);
         //   viewHolder.currentLinearLayout.set

        }

        if (isDeletedList.get(i).equals("true")) {

            viewHolder.currentLinearLayout.setVisibility(View.GONE);

            viewHolder.currentLinearLayout.setMinimumHeight(0);

            viewHolder.cardView.setVisibility(View.GONE);
        }




        viewHolder.tvName.setText(actionNameList.get(i).toString());
        viewHolder.tvActoin.setText(actionList.get(i).toString());
        viewHolder.tvSkCode.setText(actionSkcodeList.get(i).toString());



        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActionTaskActivity.class);
                intent.putExtra("name",actionNameList.get(i).toString());
                intent.putExtra("action",actionList.get(i).toString());
                intent.putExtra("actionCId",actionCId.get(i).toString());

                intent.putExtra("actionTaskId",actionTaskId.get(i).toString());


                context.startActivity(intent);



            }
        });


       // viewHolder.tvQty.setText("1");


  //      Quantity
//        viewHolder.tvQty.setText(quantityList.get(i).toString());
        //    viewHolder.tvQty.setText(jsonquantityList.get(i).toString());



       // Picasso.with(context).load(itemImageUrl.get(i).trim()).placeholder(R.drawable.top_img_bg).error(R.drawable.top_img_bg).into(viewHolder.itemImage);










    }

    @Override
    public int getItemCount() {


//        return itemListArrayList.size();

        return count;

    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName, tvSkCode, tvActoin;

        LinearLayout currentLinearLayout;

        CardView cardView;





        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.name);
            tvSkCode= (TextView) view.findViewById(R.id.sk_code);
            tvActoin= (TextView) view.findViewById(R.id.action);


            currentLinearLayout = (LinearLayout) view.findViewById(R.id.action_row_ll);


            cardView= (CardView) view.findViewById(R.id.cardView);


        }



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
