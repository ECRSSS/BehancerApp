package com.elegion.test.behancer.ui.profile;


import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter<ProfileView> {

    private final ProfileView profileView;
    private final Storage mStorage;

    public ProfilePresenter(ProfileView profileView, Storage mStorage) {
        this.profileView = profileView;
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
                .doOnSubscribe(disposable -> profileView.showRefresh())
                .doFinally(() -> profileView.hideRefresh())
                .subscribe(
                        response -> {
                            User user=profileView.showProfile(response);
                            profileView.bind(user);

                        },
                        throwable -> {
                            profileView.showError();
                        }));
    }
}
