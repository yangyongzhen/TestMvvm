package com.example.yang.testmvvm.model.data.remote;

import android.support.annotation.NonNull;

import com.example.yang.testmvvm.model.bean.LoginData;
import com.example.yang.testmvvm.model.bean.LoginDetailData;
import com.example.yang.testmvvm.model.bean.LoginType;
import com.example.yang.testmvvm.model.data.LoginDataSource;
import com.example.yang.testmvvm.retrofit.RetrofitClient;
import com.example.yang.testmvvm.retrofit.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by CoderLengary
 */


public class LoginDataRemoteSource implements LoginDataSource {
    @NonNull
    private static LoginDataRemoteSource INSTANCE;

    private LoginDataRemoteSource(){

    }

    public static LoginDataRemoteSource getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new LoginDataRemoteSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<LoginData> getRemoteLoginData(@NonNull String userName, @NonNull String password, @NonNull LoginType loginType) {
       Observable<LoginData> loginDataObservable = null;
        if (loginType== LoginType.TYPE_REGISTER){
            loginDataObservable = RetrofitClient.getInstance()
                    .create(RetrofitService.class)
                    .register(userName, password, password);
        }else if (loginType==LoginType.TYPE_LOGIN){
            loginDataObservable=RetrofitClient.getInstance()
                    .create(RetrofitService.class)
                    .login(userName, password);
        }
        return loginDataObservable
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<LoginData>() {
            @Override
            public void accept(LoginData loginData) throws Exception {
                if (loginData.getErrorCode()!=-1||loginData.getData() != null) {
                    // It is necessary to build a new realm instance
                    // in a different thread.
                    //save data
                }
            }
        });

    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(@NonNull int userId) {
        //Not required because the {@link LoginDataLocalSource} has handled it
        return null;
    }



}
