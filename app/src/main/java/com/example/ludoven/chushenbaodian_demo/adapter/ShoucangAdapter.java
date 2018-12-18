package com.example.ludoven.chushenbaodian_demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ListBean;
import com.example.ludoven.chushenbaodian_demo.bean.RecipeBean;

import java.util.List;

/**
 * 收藏 适配器
 */
public class ShoucangAdapter extends RecyclerView.Adapter<ShoucangAdapter.ViewHolder> {
    private List<ListBean>mList;
    private Context mContext;
    private ShoucangAdapter.MyItemClickListener mItemClickListener;

    public ShoucangAdapter(List<ListBean> list){
        this.mList=list;
    }
    @NonNull
    @Override
    public ShoucangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext= parent.getContext();
        }
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_shoucang,parent,false);
        //将全局的监听传递给holder
        ShoucangAdapter.ViewHolder holder = new ShoucangAdapter.ViewHolder(view, mItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoucangAdapter.ViewHolder holder, int position) {
             ListBean dataBean=mList.get(position);
        RecipeBean recipeBean=dataBean.getRecipe();
        String url = "www.ioix.top/csbd/bing.jpg";
        if (recipeBean.getImg()==null){
            url = "www.ioix.top/csbd/bing.jpg";
        }else {
            url=recipeBean.getImg();
        }
      
        Glide.with(mContext).load(url).into(holder.mImageView);
        holder.mTextView.setText(dataBean.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ShoucangAdapter.MyItemClickListener mMyItemClickListener;
        private ImageView mImageView;
        private TextView mTextView;
        public ViewHolder(View itemView,MyItemClickListener itemClickListener) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.shoucang_image);
            mTextView=itemView.findViewById(R.id.shoucang_name);
            //将全局的监听赋值给接口
            this.mMyItemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }



        /**
         * 实现OnClickListener接口重写的方法
         * @param view
         */

        @Override
        public void onClick(View view) {
            if (mMyItemClickListener != null) {
                mMyItemClickListener.onItemClick(view, getPosition());
            }

        }
    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }
    // item 的点击事件
    public void setItemClickListener(ShoucangAdapter.MyItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
