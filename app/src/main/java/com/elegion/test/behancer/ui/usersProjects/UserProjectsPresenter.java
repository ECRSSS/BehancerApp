package com.elegion.test.behancer.ui.usersProjects;

import com.arellomobile.mvp.InjectViewState;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class UserProjectsPresenter extends BasePresenter<UserProjectsView> {

    private final Storage mStorage;

    public UserProjectsPresenter(Storage storage) {
        mStorage = storage;
    }

    public void getProjects(String username) {
        mCompositeDisposable.add(
                ApiUtils.getApiService().getUsersProjects(username)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertProjects)
                        .onErrorReturn(throwable ->
                                ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> getViewState().showRefresh())
                        .doFinally(getViewState()::hideRefresh)
                        .subscribe(
                                response -> getViewState().showProjects(response.getProjects()),
                                throwable -> getViewState().showError())
        );
    }
}
