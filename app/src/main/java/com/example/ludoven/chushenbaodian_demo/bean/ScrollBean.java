package com.example.ludoven.chushenbaodian_demo.bean;

import com.chad.library.adapter.base.entity.SectionEntity;


public class ScrollBean extends SectionEntity<ScrollBean.ScrollItemBean> {

    private ScrollBean.ScrollItemBean bean;
    public ScrollBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ScrollBean(ScrollBean.ScrollItemBean bean) {
        super(bean);
        this.bean=bean;
    }
    public ScrollBean.ScrollItemBean getBean(){
        return this.bean;
    }
    public static class ScrollItemBean {
        private String text;
        private String type;

        public ScrollItemBean(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
