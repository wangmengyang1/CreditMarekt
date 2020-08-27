package com.cjqb.caijiqianbao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.stepEnd.Loan_info;
import com.cjqb.caijiqianbao.view.LoanEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopVIPAdapter extends RecyclerView.Adapter<ShopVIPAdapter.ShopVIPHolder> {


    private Context context;
    private List<LoanEntry> loan_info;
    private LayoutInflater inflater;

    public ShopVIPAdapter(Context context, List<LoanEntry> loan_info) {
        this.context = context;
        this.loan_info = loan_info;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShopVIPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.shop_vip_adapter , null);
        return new ShopVIPHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ShopVIPHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.contains("payload")){
            if (loan_info.get(position).isCheck()){
                holder.name.setBackgroundResource(R.drawable.bg_4sp_dce1e4_shape);
            }else {
                holder.name.setBackgroundResource(R.drawable.bg_4sp_f08f2e_shape);
            }
        }else {
            onBindViewHolder(holder , position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ShopVIPHolder holder, int position) {
        if (loan_info.get(position).isCheck()){
            holder.name.setBackgroundResource(R.drawable.bg_4sp_dce1e4_shape);
        }else {
            holder.name.setBackgroundResource(R.drawable.bg_4sp_f08f2e_shape);
        }

        holder.name.setText(loan_info.get(position).getLoan_info().getLoan_time() + "");
        holder.count.setText(loan_info.get(position).getLoan_info().getAmount() + "");


        holder.itemView.setOnClickListener(view -> {
            loan_info.get(position).setCheck(!loan_info.get(position).isCheck());
            notifyItemChanged(position , "payload");
        });

    }

    @Override
    public int getItemCount() {
        return loan_info.size();
    }

     class ShopVIPHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView count;

         ShopVIPHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.name);
            count= itemView.findViewById(R.id.count);
        }
    }

}
