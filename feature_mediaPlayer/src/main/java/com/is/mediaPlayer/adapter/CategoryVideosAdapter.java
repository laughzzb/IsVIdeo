package com.is.mediaPlayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.is.data_video.bean.ResCategoryVideoDetail;
import com.is.mediaPlayer.databinding.ItemCategoryVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryVideosAdapter extends RecyclerView.Adapter<CategoryVideosAdapter.ViewHoder> {

    private List<ResCategoryVideoDetail> mDatas = new ArrayList<>();//获取到数据
    private ItemClickListener mItemClickListener;

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryVideoBinding binding = ItemCategoryVideoBinding.inflate(inflater, parent, false);
        return new ViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVideosAdapter.ViewHoder holder, int position) {
        ResCategoryVideoDetail info = mDatas.get(position);
        holder.binding.setData(info);
        holder.binding.executePendingBindings();
    }

    public void setDatas(List<ResCategoryVideoDetail> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();//刷新，一定要有
    }
    public void setItemClickListenner(ItemClickListener itemClickListenner) {
        this.mItemClickListener = itemClickListenner;
    }
    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private ItemCategoryVideoBinding binding;

        public ViewHoder(@NonNull ItemCategoryVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ResCategoryVideoDetail info = mDatas.get(getLayoutPosition());
                    mItemClickListener.onItemClick(info.getId());//获取id
                    binding.ivLikes.setOnClickListener(v -> {
                        mItemClickListener.onLikeClick(getLayoutPosition());
                    });
                    binding.ivCollection.setOnClickListener(v -> {
                        mItemClickListener.onCollectionClick(getLayoutPosition());
                    });
                    binding.ivComments.setOnClickListener(v -> {
                        mItemClickListener.onCommentClick(getLayoutPosition());
                    });
                }
            });
        }
    }

    public interface ItemClickListener {
        /**
         * 点击后传递被点击的视频id
         */
        void onItemClick(int id);

        /**
         * @param position 传递数据在列表中的位置
         */
        void onLikeClick(int position);

        void onCollectionClick(int position);

        void onCommentClick(int position);

    }
}
