package com.is.mediaPlayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.is.data_video.bean.ResVideoDetail;
import com.is.libbase.utils.TimeUtils;
import com.is.mediaPlayer.databinding.ItemSearchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

/**
 * 搜索页、收藏页样式相同，因此共用一个adapter
 * 但是要注意，收藏列表接口返回的数据，不是
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHodler> {
    private List<ResVideoDetail.ArchivesInfoBean> mDatas = new ArrayList<>();

    private ItemCLickListener itemCLickListener;
    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSearchBinding binding = ItemSearchBinding.inflate(inflater, parent, false);
        return new ViewHodler(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        ResVideoDetail.ArchivesInfoBean data = mDatas.get(position);
        holder.binding.setSearch(data);

        String tag = "#" +data.getTags();
        holder.binding.setTag(tag);

        //服务端返回的时间转成年月日的形式
        String time = TimeUtils.convertTimestampToDate(data.getCreatetime());
        holder.binding.setTime(time);
        holder.binding.executePendingBindings();

    }

    public void setDatas(List<ResVideoDetail.ArchivesInfoBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas !=null ?mDatas.size():0 ;
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        private final ItemSearchBinding binding;

        public ViewHodler(@NonNull ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemCLickListener!= null){
                        ResVideoDetail.ArchivesInfoBean bean = mDatas.get(getLayoutPosition());
                        itemCLickListener.onItemClick(bean.getId());
                    }
                }
            });

        }
    }

    public void setItemCLickListener(ItemCLickListener itemCLickListener) {
        this.itemCLickListener = itemCLickListener;
    }

    public interface ItemCLickListener{
        /**
         * 点击后进入视频详情页
         */
        void  onItemClick(int videoId);
    }
}
