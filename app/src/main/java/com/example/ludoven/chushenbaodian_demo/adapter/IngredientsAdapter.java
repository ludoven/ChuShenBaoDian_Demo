package com.example.ludoven.chushenbaodian_demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.bean.IngredientsBean;

import java.util.List;

/**
 *  详情界面 的  配料  适配器
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    List<IngredientsBean> mList;
    private Context mContext;

     public IngredientsAdapter(List<IngredientsBean> mList){
         this.mList=mList;
     }
    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cailiao,parent,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
           IngredientsBean ingredientsBean =mList.get(position);
           holder.name.setText(ingredientsBean.getName());
           holder.weight.setText(ingredientsBean.getWeight());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,weight;
        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text_cailiao_name);
            weight=itemView.findViewById(R.id.text_cailiao_weight);
        }
    }
}
