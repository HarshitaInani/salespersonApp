package com.example.user.mp_salesperson.adapters;



import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.DialCustomerItem;
import java.util.ArrayList;
import android.widget.Filter;
import android.widget.Filterable;
/**
 * Created by Himanshu on 11/24/2016.
 */

public class DialListAdapter extends RecyclerView.Adapter<DialListAdapter.ViewHolder>  implements Filterable {

    // private ImageLoader imageLoader;
    private Context context;
    private RecyclerView.Adapter adapter;

    Typeface face;
    //List of superHeroes
    ArrayList<DialCustomerItem> listItem;
    ArrayList<DialCustomerItem> AlllistItem;
    ArrayList <DialCustomerItem> arraylist;
    private String TAG = "like";
    String PostId = "";
    private RecyclerView recyclerView;
    public DialListAdapter(ArrayList<DialCustomerItem> AlllistItem, ArrayList<DialCustomerItem> listItem, Context context) {
        super();
        //Getting all the superheroes

        this.listItem = listItem;
        this.AlllistItem = AlllistItem;
        this.context = context;
       // this.arraylist = new ArrayList<DialCustomerItem>();
        this.arraylist = listItem;
       // this.arraylist.addAll(listItem);
    }

    @Override
    public DialListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_my_list_adapter, parent, false);
        DialListAdapter.ViewHolder viewHolder = new DialListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DialListAdapter.ViewHolder holder, final int position) {


        int count = 0;

        final ArrayList<DialCustomerItem> dialMergeList = new ArrayList<DialCustomerItem>();


        for (int jj = 0; jj < AlllistItem.size(); jj++) {

            if (AlllistItem.get(jj).getSkcode().equalsIgnoreCase(listItem.get(position).getSkcode())) {
                String id = AlllistItem.get(jj).getId();
                String CustomerName = AlllistItem.get(jj).getCustomerName();
                String ShopName = AlllistItem.get(jj).getShopName();
                String Skcode = AlllistItem.get(jj).getSkcode();
                String Mobile = AlllistItem.get(jj).getMobile();
                dialMergeList.add(new DialCustomerItem(id, CustomerName, ShopName, Skcode, Mobile));
                count++;
                holder.Dial_Number.setText(String.valueOf(count));
                // System.out.println("Moq5555:::"+count);
            } else {

            }

        }

/*
        if(dialMergeList.size()==0)
        {
            String id = listItem.get(position).getId();
            String CustomerName = listItem.get(position).getCustomerName();
            String ShopName = listItem.get(position).getShopName();
            String Skcode = listItem.get(position).getSkcode();
            String Mobile =listItem.get(position).getMobile();
            dialMergeList.add(new DialCustomerItem(id,CustomerName,ShopName,Skcode,Mobile));
            holder.ShopName.setText("");

        }else
        {

            holder.ShopName.setText("");

        }*/

        final DialCustomerItem pojo = listItem.get(position);
        holder.ShopName.setText(pojo.getShopName());
        holder.SkCode.setText(pojo.getSkcode());
      //  holder.Mobile.setText(pojo.getMobile());
        holder.Mobile.setText("(+91)"+pojo.getMobile());
      /*  holder.Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("mobile:::"+pojo.getMobile());
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+pojo.getMobile()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
    }
});
*/

    }
   /* // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listItem.clear();
        if (charText.length() == 0) {
            listItem.addAll(arraylist);
        } else {
            for (DialCustomerItem wp : arraylist) {
                if (wp.getShopName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listItem.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


*/


    @Override
    public int getItemCount() {
        return listItem.size();
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                     listItem=arraylist;
                } else {

                    ArrayList<DialCustomerItem> filteredList = new ArrayList<>();

                    for (DialCustomerItem androidVersion : arraylist) {

                        if (androidVersion.getShopName().toLowerCase().contains(charString) || androidVersion.getSkcode().toLowerCase().contains(charString) || androidVersion.getMobile().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    listItem = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listItem = (ArrayList<DialCustomerItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder{

       // public NetworkImageView networkImageViewImageEngineer;

        public TextView ShopName;
        public TextView SkCode;
        public TextView Dial_Number;
        public TextView Mobile;
        public CardView card_view;


        public ViewHolder(View itemView) {
            super(itemView);
           // networkImageViewImageEngineer=(NetworkImageView)itemView.findViewById(R.id.niv_pimage);

            ShopName = (TextView) itemView.findViewById(R.id.shop_name);
            SkCode = (TextView) itemView.findViewById(R.id.sk_code);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            Dial_Number = (TextView) itemView.findViewById(R.id.Dial_Number);
            Mobile = (TextView) itemView.findViewById(R.id.mobile);

        }
    }

}