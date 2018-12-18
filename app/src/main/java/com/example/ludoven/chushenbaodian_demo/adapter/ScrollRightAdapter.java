package com.example.ludoven.chushenbaodian_demo.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ludoven.chushenbaodian_demo.R;
import com.example.ludoven.chushenbaodian_demo.bean.ScrollBean;

import java.util.List;

/**
 * 分类 右边的适配器
 */

public class ScrollRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {

    public ScrollRightAdapter(int layoutResId, int sectionHeadResId, List<ScrollBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ScrollBean item) {
        helper.setText(R.id.right_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScrollBean item) {
        ScrollBean.ScrollItemBean t = item.t;
        helper.setText(R.id.right_text, t.getText());
    }
}
