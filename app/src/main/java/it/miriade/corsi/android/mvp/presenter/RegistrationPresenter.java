package it.miriade.corsi.android.mvp.presenter;

import it.miriade.corsi.android.mvp.interactor.UserInteractor;
import it.miriade.corsi.android.mvp.model.entity.User;
import it.miriade.corsi.android.mvp.ui.activity.RegistrationActivity;
import it.miriade.corsi.android.mvp.util.StringUtils;
import it.miriade.corsi.android.mvp.view.RegistrationView;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public class RegistrationPresenter {

    private RegistrationView view;
    private UserInteractor userInteractor;

    public RegistrationPresenter(RegistrationView view) {
        this.view = view;
        this.userInteractor = new UserInteractor((RegistrationActivity) view);
    }

    public void register(User user) {
        if(userInteractor.getUser(user.getEmail()) != null) {
            view.alreadyRegistered(user.getEmail());
            return;
        }
        try {
            user.setPassword(StringUtils.sha256(user.getPassword()));
            userInteractor.saveUser(user);
            view.registered();
        } catch (Exception e) {
            e.printStackTrace();
            view.registrationError();
        }
    }

}
