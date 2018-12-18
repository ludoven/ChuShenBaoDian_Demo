package com.example.ludoven.chushenbaodian_demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.bean.MethodBean;
import com.example.ludoven.chushenbaodian_demo.bean.StepsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 详情界面 步骤的适配器
 */
public class StepsAdapter extends RecyclerView.Adapter {
    private LayoutInflater mLayoutInflater;
    private List<MethodBean> listItem;
    //构造函数 传入数据
    public StepsAdapter(Context context, List<MethodBean> listItem){
        mLayoutInflater=LayoutInflater.from(context);
        this.listItem=listItem;

    }
    class  Viewholder extends RecyclerView.ViewHolder{
      private TextView Text;
        public Viewholder(View itemView) {
            super(itemView);
          Text=itemView.findViewById(R.id.text_xq_buzhou);
        }

        public TextView getText() {
            return Text;
        }
    }
    @NonNull
    @Override
    //Viewholder 绑定 item的布局
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(mLayoutInflater.inflate(R.layout.item_xiangqing,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                      Viewholder viewholder= (Viewholder) holder;
                    //   viewholder.Title.setText((String) listItem.get(position).get("ItemTitle"));
                     /*  StepsBean stepsBean=listItem.get(position);
                       String step=stepsBean.getStep();
                       step=step.substring(2);
                       step=step.replaceAll("\\.","");
                       viewholder.Text.setText(step);*/
        MethodBean methodBean=listItem.get(position);
        String step=methodBean.getStep();
        viewholder.Text.setText(step);

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
