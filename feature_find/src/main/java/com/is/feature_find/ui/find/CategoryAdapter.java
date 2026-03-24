package com.is.feature_find.ui.find;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.is.data_video.bean.ResFindCategory;
import com.is.feature_find.databinding.ItemCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter <CategoryAdapter.ViewHolder> {

    private List<ResFindCategory> mDatas;
    private CategoryListenner mListenner;


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());//获取inflater对象，查找布局
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        ResFindCategory category = mDatas.get(position);
        holder.binding.setData(category);
    }

    public void setDatas(List<ResFindCategory> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas ==null ? 0 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ItemCategoryBinding binding;

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (mListenner != null) {
                    int position = getLayoutPosition();
                    mListenner.onCategroyItemClick(mDatas.get(position));
                }

            });
        }
    }

    public interface CategoryListenner {
        void onCategroyItemClick(ResFindCategory category);
    }

    public void setListenner(CategoryListenner listenner) {
        this.mListenner = listenner;
    }
}
