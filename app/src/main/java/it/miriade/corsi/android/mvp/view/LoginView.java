package it.miriade.corsi.android.mvp.view;

import it.miriade.corsi.android.mvp.model.entity.User;
import it.miriade.corsi.android.mvp.ui.activity.LoginActivity.InputType;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public interface LoginView {

    void validationFailed(InputType type);
    void jumpToHome(User user);
    void userNotRegistered(String email);
    void loginFailed();

}
