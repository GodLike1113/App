package com.test.chuanyi.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Author:  zengfeng
 * Time  :  2019/7/4 16:48
 * Des   :
 */
public class ListViewAdapter extends BaseAdapter {
    public Context mContext;
    public List<String> mSrcList;

    public ListViewAdapter(Context context, List<String> src) {
        mContext = context;
        mSrcList = src;
    }

    public  void setData(List<String> list){
        mSrcList =list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSrcList == null ? 0 : mSrcList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSrcList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_layout, null);

            viewHolder.itemTv = convertView.findViewById(R.id.item_lv_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.itemTv.setText(mSrcList.get(position));
        return convertView;
    }

    static class ViewHolder {
        public TextView itemTv;
    }
}
