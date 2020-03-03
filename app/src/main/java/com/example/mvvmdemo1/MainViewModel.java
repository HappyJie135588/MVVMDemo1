package com.example.mvvmdemo1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmdemo1.bean.BannerBean;
import com.example.mvvmdemo1.bean.ResponModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<List<BannerBean>> getBanners() {
        //因为用到LiveData，我觉得都不需要切换到主线程了。LiveData可以帮我们做
        //调用接口，返回我们的MutableLiveData<List<BannerBean>>
        final MutableLiveData<List<BannerBean>> liveData = new MutableLiveData<>();
        RetrofitManager.getInstance().getApiService().getBanner()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponModel<List<BannerBean>>>() {
                    @Override
                    public void accept(ResponModel<List<BannerBean>> listResponModel) throws Exception {
                        liveData.postValue(listResponModel.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        return liveData;
    }
}
