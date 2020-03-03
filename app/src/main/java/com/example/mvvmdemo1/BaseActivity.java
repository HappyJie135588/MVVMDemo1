package com.example.mvvmdemo1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

//ViewDataBinding 是所有DataBinding的父类
public abstract class BaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {

    //获取当前activity布局文件,并初始化我们的binding
    protected abstract int getContentViewId();

    //处理逻辑业务
    protected abstract void processLogic();

    protected VM mViewModel;
    protected VDB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        //初始化我们的bingding
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        //给binding加上感知生命周期，AppCompatActivity就是lifeOwner，之前解释过了，不懂看前面
        binding.setLifecycleOwner(this);
        //创建我们的ViewModel
        createViewModel();
        processLogic();
    }

    private void createViewModel() {
        if (mViewModel == null) {
            Class modeClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modeClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modeClass = BaseViewModel.class;
            }
            mViewModel = (VM) ViewModelProviders.of(this).get(modeClass);
        }
    }


}
