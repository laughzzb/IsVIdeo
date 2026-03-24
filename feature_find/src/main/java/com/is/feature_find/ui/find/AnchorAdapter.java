package com.is.feature_find.ui.find;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.is.feature_find.bean.ResFindAnchor;

import com.is.feature_find.databinding.ItemAnchorBinding;


import java.util.List;

public class AnchorAdapter extends RecyclerView.Adapter <AnchorAdapter.ViewHolder> {

    private List<ResFindAnchor> mDatas;

    @NonNull
    @Override
    public AnchorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());//获取inflater对象，查找布局
        ItemAnchorBinding binding = ItemAnchorBinding.inflate(inflater,parent,false);

        return new AnchorAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnchorAdapter.ViewHolder holder, int position) {
        ResFindAnchor anchor = mDatas.get(position);
        holder.binding.setData(anchor);
    }

    public void setDatas(List<ResFindAnchor> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas ==null ? 0 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ItemAnchorBinding binding;

        public ViewHolder(ItemAnchorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
