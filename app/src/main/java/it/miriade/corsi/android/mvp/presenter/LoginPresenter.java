package it.miriade.corsi.android.mvp.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import it.miriade.corsi.android.mvp.interactor.UserInteractor;
import it.miriade.corsi.android.mvp.model.entity.User;
import it.miriade.corsi.android.mvp.ui.activity.LoginActivity;
import it.miriade.corsi.android.mvp.ui.activity.LoginActivity.InputType;
import it.miriade.corsi.android.mvp.util.StringUtils;
import it.miriade.corsi.android.mvp.view.LoginView;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public class LoginPresenter {

    private LoginView view;
    private UserInteractor userInteractor;

    public LoginPresenter(LoginView view) {
        this.view = view;
        this.userInteractor = new UserInteractor((LoginActivity) view);
    }

    private boolean validate(String email, String password) {
        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.validationFailed(InputType.email);
        }
        if(TextUtils.isEmpty(password)) {
            view.validationFailed(InputType.password);
        }
        return !(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) && !TextUtils.isEmpty(password);
    }

    public void login(String email, String password) {
        if(!validate(email, password))
            return;
        if(!userInteractor.isUserRegistered(email))
            view.userNotRegistered(email);
        User user = userInteractor.getByCredentials(email, StringUtils.sha256(password));
        if(user == null) {
            view.loginFailed();
        } else {
            view.jumpToHome(user);
        }
    }

}
