package com.huanxi.renrentoutiao.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.bean.TxNum;

import java.util.List;

public class TxNumAdapter extends RecyclerView.Adapter<TxNumAdapter.MyViewHolder> {

    private List<TxNum> dataList;

    private LayoutInflater layoutInflater;
    private Context mContext;
    private int mWithDrawMoney = 0;

    public TxNumAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_tx_num, parent, false);
        return new MyViewHolder(itemView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TxNum item = getItem(position);
        int number = item.getNum();
//        float money_num = number;
        holder.withdrawLevel.setText(number + " 元");
        holder.withdrawItem.setTag(position);
        if (item.getLeft() > 0) {
            holder.withdrawItem.setEnabled(true);
            holder.withdrawNum.setEnabled(true);
            holder.withdrawLevel.setEnabled(true);

            if (item.isChecked()) {
                holder.withdrawItem.setSelected(true);
                holder.withdrawNum.setSelected(true);
                holder.withdrawLevel.setSelected(true);
            } else {
                holder.withdrawNum.setSelected(false);
                holder.withdrawLevel.setSelected(false);
                holder.withdrawItem.setSelected(false);
            }
        } else {
            holder.withdrawItem.setEnabled(false);
            holder.withdrawNum.setEnabled(false);
            holder.withdrawLevel.setEnabled(false);
        }
//        holder.withdrawNum.setText(String.format(mContext.getResources().getString(R.string.withdraw_num), item.getLeft()));

        holder.withdrawItem.setOnClickListener(view -> selectedItem(position));
    }

    private TxNum getItem(int position) {
        return dataList.get(position);
    }

    private void selectedItem(int position) {
        for (int i = 0; i < dataList.size(); i++) {
            if (i == position) {
                dataList.get(i).setChecked(true);
                mWithDrawMoney = dataList.get(i).getNum();
            } else {
                dataList.get(i).setChecked(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    public int getWithDrawMoney() {
        return mWithDrawMoney;
    }

    public void selectedDefaultItem() {
        if (dataList == null) {
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getLeft() > 0) {
                selectedItem(i);
                return;
            }
        }

    }

    public void setData(List<TxNum> data) {
        dataList = data;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView withdrawNum;
        private final LinearLayout withdrawItem;
        private TextView withdrawLevel;


        MyViewHolder(View itemView) {
            super(itemView);
            withdrawLevel = itemView.findViewById(R.id.withdraw_level);
            withdrawNum = itemView.findViewById(R.id.withdraw_num);
            withdrawItem = itemView.findViewById(R.id.withdraw_item);
        }
    }

}

   