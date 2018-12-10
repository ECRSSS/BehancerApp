package com.elegion.test.behancer.ui.profile;


import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {

    private final Storage mStorage;

    public ProfilePresenter(Storage mStorage) {
        this.mStorage = mStorage;
    }

    public void getProfile(@NonNull String mUsername) {
        mCompositeDisposable.add(ApiUtils.getApiService().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        response -> {
                            getViewState().showProfile(response);
                        },
                        throwable -> {
                            getViewState().showError();
                        }));
    }

    public void openProfileFragment(String username) {
        getViewState().openProfileFragment(username);
    }
}
