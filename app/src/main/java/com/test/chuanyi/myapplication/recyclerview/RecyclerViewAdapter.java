package com.test.chuanyi.myapplication.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.chuanyi.myapplication.R;

import java.util.List;

/**
 * Author:  zengfeng
 * Time  :  2019/4/28 15:12
 * Des   :
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> srcList;

    public RecyclerViewAdapter(Context context, List<String> srcList) {
        mContext = context;
        this.srcList = srcList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d("vivi", "onCreateViewHolder : viewType = " + viewType);
        MyViewHolder viewHolder1 = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, viewGroup, false));
        switch (viewType) {
            case 0:
                cViewType = 0;
                break;
            case 1:
                cViewType = 1;
                break;
        }
        return viewHolder1;
    }

    int cViewType = -1;

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        Log.d("vivi", "onBindViewHolder bind : position = " + position + ",ViewType =" + cViewType);
        if (cViewType == 0) {
            myViewHolder.itemTv.setText(srcList.get(position));

            if (onItemClickListener != null) {
                myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClickListener(v, position);
                    }
                });
            }
            if (onItemLongClickListener != null) {
                myViewHolder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemLongClickListener.onItemLongClickListener(v, position);
                        return true; //消费事件
                    }
                });
            }
            //TextView 设置点击事件
            if (onItemTextClickListener != null) {
                myViewHolder.itemTv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemTextClickListener.onTextClickListener(v, position);
                    }
                });

                myViewHolder.itemTv2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemTextClickListener.onTextLongClickListener(v, position);
                        return true;
                    }
                });
            }
        } else if (cViewType == 1) {
            myViewHolder.itemTv.setText(srcList.get(position)+"haha");
        }


    }

    @Override
    public int getItemViewType(int position) {
      if(position % 2 == 0 ){
          Log.d("vivi", "getItemViewType position = " + position + ",ViewType =0");
          return 0;
      }
        Log.d("vivi", "getItemViewType position = " + position + ",ViewType =1");
      return 1;
    }

    @Override
    public int getItemCount() {
        return srcList == null ? 0 : srcList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemTextClickListener(OnItemTextClickListener onItemTextClickListener) {
        this.onItemTextClickListener = onItemTextClickListener;
    }

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemTextClickListener onItemTextClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }

    public interface OnItemTextClickListener {
        void onTextClickListener(View v, int position);

        void onTextLongClickListener(View v, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemTv;
        TextView itemTv2;
        View rootView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            if (cViewType == 0) {
//                rootView = itemView.findViewById(R.id.root_item_view);
//                itemTv = itemView.findViewById(R.id.recycler_item_tv);
//                itemTv2 = itemView.findViewById(R.id.recycler_item_tv2);
//            } else if (cViewType == 1) {
//                itemTv_1 = itemView.findViewById(R.id.recycler_item_1_tv);
//            }

                rootView = itemView.findViewById(R.id.root_item_view);
                itemTv = itemView.findViewById(R.id.recycler_item_tv);
                itemTv2 = itemView.findViewById(R.id.recycler_item_tv2);

        }
    }
}
