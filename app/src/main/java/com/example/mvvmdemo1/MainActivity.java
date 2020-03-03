package com.example.mvvmdemo1;

import android.view.View;

import androidx.lifecycle.Observer;

import com.example.mvvmdemo1.bean.BannerBean;
import com.example.mvvmdemo1.databinding.ActivityMainBinding;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processLogic() {
        initBanner();
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBanner();
            }
        });
    }

    private void initBanner() {
        binding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        binding.banner.setImageLoader(new GlideImageLoader());
    }

    private void getBanner() {
        mViewModel.getBanners().observe(this, new Observer<List<BannerBean>>() {
            @Override
            public void onChanged(List<BannerBean> bannerBeans) {
                updateBanner(bannerBeans);
            }
        });
    }

    private void updateBanner(List<BannerBean> data) {
        if (data == null || data.size() <= 0) {
            return;
        }
        List<String> urls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            urls.add(data.get(i).getImagePath());
            titles.add(data.get(i).getTitle());
        }
        binding.banner.setBannerTitles(titles);
        binding.banner.setImages(urls);
        binding.banner.start();
    }


}
