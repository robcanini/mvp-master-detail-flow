package it.miriade.corsi.android.mvp.view;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public interface RegistrationView {

    void alreadyRegistered(String email);
    void registered();
    void registrationError();

}
