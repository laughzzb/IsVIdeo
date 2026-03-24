package com.is.feature_piaza.adaper;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.is.feature_piaza.R;
import com.is.feature_piaza.bean.PlazaXBannerData;
import com.is.feature_piaza.bean.ResPlaza;
import com.is.feature_piaza.databinding.ItemBannerBinding;
import com.is.feature_piaza.databinding.ItemImageBinding;
import com.is.libbase.utils.GlideUtils;
import com.stx.xhb.androidx.XBanner;

import java.util.ArrayList;
import java.util.List;

public class PlazaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_BANNER = 1;//banner
    private static final int ITEM_TYPE_IMAGE = 2 ; //常规item类型
    private List<ResPlaza.PlazaDetail> mLists;
    private ArrayList<PlazaXBannerData> mBannerDatas;//banner数据
    private PlazaItemListener mListener;
    private ResPlaza mBannerData;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if(ITEM_TYPE_BANNER == viewType){
            ItemBannerBinding bannerBinding = ItemBannerBinding.inflate(layoutInflater, parent, false);
            BannerViewHodel viewHolder= new BannerViewHodel(bannerBinding);
            return viewHolder;
        }else {
            ItemImageBinding imageBinding = ItemImageBinding.inflate(layoutInflater, parent, false);
            ImageViewHodel viewHolder = new ImageViewHodel(imageBinding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == ITEM_TYPE_BANNER ){
            //顶部的第一行
            BannerViewHodel viewHolder = (BannerViewHodel) holder;
            ItemBannerBinding binding = viewHolder.bannerBinding;
            //设置占位符
            binding.xbanner.setBannerPlaceholderImg(R.mipmap.ic_cat, ImageView.ScaleType.CENTER_CROP);
            //一屏多页
            binding.xbanner.setIsClipChildrenMode(true);
            //设置banner数据以及自定义banner每页的布局
            binding.xbanner.setBannerData(R.layout.layout_banner_child,mBannerDatas);
            binding.xbanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                   ImageView imageView = view.findViewById(R.id.image_view);
                  TextView tvTittle = view.findViewById(R.id.tv_title);
                   TextView tvLabel = view.findViewById(R.id.tv_label);

                    PlazaXBannerData data = mBannerDatas.get(position);
                    GlideUtils.loadImage(data.getXBannerUrl(),imageView);
                    tvTittle.setText(data.getXBannerTitle());
                    tvLabel.setText(data.getDescription());
                }
            });
            //设置指示器
            binding.xbanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    binding.tvIndicator.setText(String.valueOf(position+1));
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }else {
            //常规数据
            ImageViewHodel viewHolder = (ImageViewHodel) holder;
            ResPlaza.PlazaDetail detail = mLists.get(position-1);
            viewHolder.binding.setData(detail);

        }


    }

    @Override
    public int getItemViewType(int position) {
        return position == 0? ITEM_TYPE_BANNER:ITEM_TYPE_IMAGE;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mBannerDatas!= null&& mBannerDatas.size()>0){
            count+=1;
        }

        if (mLists !=null&& mLists.size()>0){
            count +=mLists.size();
        }
        return count;
    }

    public void setDatas(List<ResPlaza> data) {
        if (data!=null&& data.size()>=2){

            mBannerData = data.get(0);
            mBannerDatas = conberXBannerDatas(mBannerData);

            ResPlaza imagerData = data.get(1);
            mLists = imagerData.getLists();


            //刷新
            notifyDataSetChanged();
        }

    }

    /**
     * 因为xbanner需要接收特定的数据类型，所以要把服务器返回的数据转成xbanner可以接收的数据类型
     * @param data
     * @return
     */
    private ArrayList<PlazaXBannerData> conberXBannerDatas(ResPlaza data) {
        if (data.getLists() != null) {
            List<ResPlaza.PlazaDetail> lists = data.getLists();
            if (lists.size()>0) {
                ArrayList<PlazaXBannerData> xBannerDatas = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    ResPlaza.PlazaDetail detail = lists.get(i);
                    PlazaXBannerData bannerData = new PlazaXBannerData(detail.getImage()
                            ,detail.getName(),detail.getDescription());
                    xBannerDatas.add(bannerData);
                }
                return xBannerDatas;
            }
        }
        return null;
    }

    public class ImageViewHodel extends RecyclerView.ViewHolder {

        private final ItemImageBinding binding;

        public ImageViewHodel(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        int position = getLayoutPosition();
                        //
                        ResPlaza.PlazaDetail plazaDetail = mLists.get(position - 1);
                        mListener.onImageClick(plazaDetail);
                    }
                }
            });
        }
    }

    public class BannerViewHodel extends RecyclerView.ViewHolder {

        private final ItemBannerBinding bannerBinding;

        public BannerViewHodel(ItemBannerBinding binding) {
            super(binding.getRoot());
            bannerBinding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        int position = getLayoutPosition();
                        itemClick(position);
                    }
                }
            });
            binding.xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    if (mListener!=null){
                        itemClick(position);
                    }
                }
            });
        }
        private void itemClick(int position) {
            List<ResPlaza.PlazaDetail> lists = mBannerData.getLists();
            ResPlaza.PlazaDetail plazaDetail = lists.get(position);
            mListener.onBannerClick(plazaDetail);

        }

    }


    public interface PlazaItemListener{

        /**
         * 头部广告点击
         */
        void onBannerClick(ResPlaza.PlazaDetail detail);

        /**
         * 底部图片点击
         */
        void onImageClick(ResPlaza.PlazaDetail detail);
    }

    //要有set方法，外部才能调用
    public void setListener(PlazaItemListener mListener) {
        this.mListener = mListener;
    }
}
