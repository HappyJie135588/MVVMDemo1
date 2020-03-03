package com.example.mvvmdemo1;

import com.example.mvvmdemo1.bean.BannerBean;
import com.example.mvvmdemo1.bean.ResponModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RetrofitApiService {
    //wanAndroid的banner
    @GET("banner/json")
    Observable<ResponModel<List<BannerBean>>> getBanner();
}
