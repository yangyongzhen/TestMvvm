package com.example.yang.testmvvm.model.data;

import android.support.annotation.NonNull;

import com.example.yang.testmvvm.model.bean.LoginData;
import com.example.yang.testmvvm.model.bean.LoginDetailData;
import com.example.yang.testmvvm.model.bean.LoginType;

import io.reactivex.Observable;

/**
 * Created by CoderLengary
 */


public interface LoginDataSource {

    Observable<LoginData> getRemoteLoginData(@NonNull String userName, @NonNull String password, @NonNull LoginType loginType);

    Observable<LoginDetailData> getLocalLoginData(@NonNull int userId);



}
