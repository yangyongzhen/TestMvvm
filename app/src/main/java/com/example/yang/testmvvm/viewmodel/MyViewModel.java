package com.example.yang.testmvvm.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.yang.testmvvm.model.MyLoginModel;
import com.example.yang.testmvvm.model.data.LoginDataRepository;
import com.example.yang.testmvvm.model.data.local.LoginDataLocalSource;
import com.example.yang.testmvvm.model.data.remote.LoginDataRemoteSource;

public class MyViewModel extends ViewModel {

    public MyLoginModel loginmodel;

    private  MutableLiveData<String> mName;

    public MyViewModel() {

        this.loginmodel = new MyLoginModel(this, LoginDataRepository.getInstance(
                LoginDataLocalSource.getInstance(),
                LoginDataRemoteSource.getInstance()
        ));
    }


    public MutableLiveData<String> getName() {
        if (mName == null) {
            mName = new MutableLiveData<String>();
        }
        return mName;
    }





}
