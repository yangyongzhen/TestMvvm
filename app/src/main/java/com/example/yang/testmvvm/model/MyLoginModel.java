package com.example.yang.testmvvm.model;

import android.support.annotation.NonNull;

import com.example.yang.testmvvm.model.bean.LoginData;
import com.example.yang.testmvvm.model.bean.LoginType;
import com.example.yang.testmvvm.model.data.LoginDataRepository;
import com.example.yang.testmvvm.viewmodel.MyViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MyLoginModel implements BaseModel{

    private LoginDataRepository repository;
    private MyViewModel myViewModel;
    private CompositeDisposable compositeDisposable;

    public MyLoginModel(MyViewModel viewmodel,@NonNull LoginDataRepository loginDataRepository) {

        this.myViewModel = viewmodel;
        this.repository = loginDataRepository;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }
    @Override
    public void unSubscribe() {
        compositeDisposable.clear();
    }

    public void login(String username, String password, @NonNull LoginType loginType) {
        getLoginData(username, password,loginType);
    }

    private void getLoginData(String username,String password, @NonNull final LoginType loginType){

        Disposable disposable=repository.getRemoteLoginData(username, password,loginType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginData>() {

                    @Override
                    public void onNext(LoginData value) {
//                        if (!view.isActive()) {
//                            return;
//                        }
//                        if (value.getErrorCode()==-1){
//                            view.showLoginError(value.getErrorMsg());
//                        }else {
//                            view.saveUser2Preference(value.getData());
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        if (view.isActive()) {
//                            view.showNetworkError();
//                        }
                    }

                    @Override
                    public void onComplete() {
                        myViewModel.getName().postValue("onComplete ok!");
                    }
                });

        compositeDisposable.add(disposable);
    }
}
