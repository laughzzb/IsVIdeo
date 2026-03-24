package com.is.feature_find.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.is.feature_find.bean.ResFindTopic;
import com.is.feature_find.bean.ResTopic;
import com.is.feature_find.databinding.ItemTopicInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHodler> {
    private List<ResTopic> mDatas = new ArrayList<>();

    public void setDatas(List<ResTopic> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTopicInfoBinding binding = ItemTopicInfoBinding.inflate(inflater, parent, false);
        return new ViewHodler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        ResTopic resTopic = mDatas.get(position);
        holder.binding.setTopicinfo(resTopic);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas.isEmpty() ? 0:mDatas.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {

        private final ItemTopicInfoBinding binding;

        public ViewHodler(@NonNull ItemTopicInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
