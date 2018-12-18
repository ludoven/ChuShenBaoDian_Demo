package com.example.ludoven.chushenbaodian_demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.bean.CaipuData;
import com.example.ludoven.chushenbaodian_demo.bean.DataBean;
import com.example.ludoven.chushenbaodian_demo.bean.ListBean;
import com.example.ludoven.chushenbaodian_demo.bean.RecipeBean;
import com.example.ludoven.chushenbaodian_demo.util.GlideRoundTransform;
import com.maning.mndialoglibrary.MProgressDialog;


import java.util.List;
import java.util.Random;

public class CaipuAdapter<T> extends RecyclerView.Adapter<CaipuAdapter.ViewHolder> {
    private List<T> mList;
    private Context mContext;
    private List<String> nameList;
    private int min=100;
    private int max=9999;
    private String [] imgText={"蟹乃食中珍味","茶饼嚼时香透齿","玉碗盛来琥珀光","江湖美食唯烧烤","粥鱼忽报千山晓"};
    private CaipuAdapter.MyItemClickListener mItemClickListener;
    private int resource;
    public CaipuAdapter(List<T>msgList,int resource){
        mList=msgList;
        this.resource=resource;
    }
    public CaipuAdapter(List<String> name,List<T>msgList,int resource){
        this.nameList=name;
        mList=msgList;
        this.resource=resource;
    }
    /*        Bitmap bitmap1=setTextToImg("蟹乃食中珍味",img1);
            Bitmap bitmap2=setTextToImg("茶饼嚼时香透齿",img2);
            Bitmap bitmap3=setTextToImg("玉碗盛来琥珀光",img3);
            Bitmap bitmap4=setTextToImg("江湖美食唯烧烤",img4);
            Bitmap bitmap5=setTextToImg("粥鱼忽报千山晓",img5);*/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CaipuAdapter.MyItemClickListener mMyItemClickListener;
        private ImageView mImageView;
        private TextView  caipu_name,caipu_imtro,caipu_buzhou,caipu_title,caipu_size;


        public ViewHolder(View view, MyItemClickListener itemClickListener) {
            super(view);
            if (resource==R.layout.item_tuijian){
                mImageView=view.findViewById(R.id.image_tuijian);
                caipu_name=view.findViewById(R.id.text_tuijian_title);
                caipu_imtro=view.findViewById(R.id.text_tuijian_jianjie);
            }else if (resource==R.layout.item_xiangqing){
                caipu_buzhou=view.findViewById(R.id.text_xq_buzhou);
            }else if (resource==R.layout.item_shoucang){
                mImageView=view.findViewById(R.id.shoucang_image);
            }else if (resource==R.layout.item_liebiao){
                mImageView=view.findViewById(R.id.image_liebiao);
                caipu_name=view.findViewById(R.id.text_liebiao_name);
                caipu_size=view.findViewById(R.id.text_liebiao_size);
            }else if (resource==R.layout.item_story){
                mImageView=view.findViewById(R.id.image_story);
                caipu_title=view.findViewById(R.id.text_story_title);
                caipu_size=view.findViewById(R.id.text_story_form);
            }else if (resource==R.layout.item_shouye_caidan){
                mImageView=view.findViewById(R.id.caidan_img);
                caipu_title=view.findViewById(R.id.caidan_text);
            }

            //将全局的监听赋值给接口
            this.mMyItemClickListener = itemClickListener;
            view.setOnClickListener(this);
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
    @NonNull
    @Override
    public CaipuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext=parent.getContext();
        }

        View view=LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        //将全局的监听传递给holder
        CaipuAdapter.ViewHolder holder = new CaipuAdapter.ViewHolder(view, mItemClickListener);

        return holder;
    }



    @Override
    public void onBindViewHolder( CaipuAdapter.ViewHolder holder, int position) {
        if (resource==R.layout.item_tuijian){
            ListBean listBean= (ListBean) mList.get(position);
            RecipeBean recipeBean=listBean.getRecipe();
            holder.caipu_name.setText(listBean.getName());
            holder.caipu_imtro.setText(recipeBean.getSumary());
            //用 Glide 加载网络图片
            String url=recipeBean.getImg();
            Glide.with(mContext).load(url).transform(new GlideRoundTransform(mContext,5)).into(holder.mImageView);
            MProgressDialog.dismissProgress();
        }else  if (resource ==R.layout.item_shoucang){
           DataBean caipu= (DataBean) mList.get(position);
            String img=caipu.getAlbums().get(0);
            Glide.with(mContext).load(img).transform(new GlideRoundTransform(mContext,5)).into(holder.mImageView);
        }else if (resource==R.layout.item_liebiao){
           /* DataBean caipu= (DataBean) mList.get(position);
            holder.caipu_name.setText(caipu.getTitle());
            String url=caipu.getAlbums().get(0);
            Glide.with(mContext).load(url).into(holder.mImageView);
            Random random=new Random();
            int num=random.nextInt(max)%(max-min+1)+min;
            int num2=random.nextInt(999)%(999-100+1)+100;
            holder.caipu_size.setText(num+"万浏览  "+num2+"万收藏");*/
           RecipeBean recipeBean= (RecipeBean) mList.get(position);
           holder.caipu_name.setText(nameList.get(position));
           String url=recipeBean.getImg();
           Glide.with(mContext).load(url).into(holder.mImageView);
            Random random=new Random();
            int num=random.nextInt(max)%(max-min+1)+min;
            int num2=random.nextInt(999)%(999-100+1)+100;
            holder.caipu_size.setText(num+"万浏览  "+num2+"万收藏");
            MProgressDialog.dismissProgress();
        }else if (resource==R.layout.item_story){
            RecipeBean caipuData= (RecipeBean) mList.get(position);
            holder.caipu_title.setText(caipuData.getTitle());
            holder.caipu_size.setText("来自 厨神宝典");
            String url=caipuData.getImg();
            Glide.with(mContext).load(url).into(holder.mImageView);
        }else if (resource==R.layout.item_shouye_caidan){
            String url= (String) mList.get(position);
            holder.caipu_title.setText(imgText[position]);
            Glide.with(mContext).load(url).into(holder.mImageView);
            MProgressDialog.dismissProgress();
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }





  // item 的点击事件
    public void setItemClickListener(MyItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
