package com.elegion.test.behancer.ui.profile;

import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.user.UserResponse;

import io.reactivex.annotations.NonNull;

public interface ProfileView extends BaseView {

    void showProfile(UserResponse response);
    void openProfileFragment(@NonNull String username);
}
