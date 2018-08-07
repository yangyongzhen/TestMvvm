package com.example.yang.testmvvm.model.data.local;

import android.support.annotation.NonNull;

import com.example.yang.testmvvm.model.bean.LoginData;
import com.example.yang.testmvvm.model.bean.LoginDetailData;
import com.example.yang.testmvvm.model.bean.LoginType;
import com.example.yang.testmvvm.model.data.LoginDataSource;

import io.reactivex.Observable;

/**
 * Created by CoderLengary
 */


public class LoginDataLocalSource implements LoginDataSource {
    @NonNull
    private static LoginDataLocalSource INSTANCE;


    private LoginDataLocalSource() {

    }

    public static LoginDataLocalSource getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new LoginDataLocalSource();
        }
        return INSTANCE;
    }


    @Override
    public Observable<LoginData> getRemoteLoginData(@NonNull String userName, @NonNull String password, @NonNull LoginType loginType) {
        //Not required because the {@link LoginDataRemoteSource}  has handled it
        return null;
    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(@NonNull int userId) {

        LoginDetailData loginDetailData = new LoginDetailData() ;
        return Observable.just(loginDetailData);
    }

}
