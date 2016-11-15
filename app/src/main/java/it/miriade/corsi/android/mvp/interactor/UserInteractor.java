package it.miriade.corsi.android.mvp.interactor;

import android.content.Context;

import java.util.List;

import it.miriade.corsi.android.mvp.model.DbHelper;
import it.miriade.corsi.android.mvp.model.dao.UserDao;
import it.miriade.corsi.android.mvp.model.entity.User;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public class UserInteractor {

    private UserDao dao;

    public UserInteractor(Context context) {
        this.dao = new UserDao(new DbHelper(context));
    }

    public boolean isUserRegistered(String email) {
        return dao.getUserByEmail(email) != null;
    }

    public User getUser(String email) {
        return dao.getUserByEmail(email);
    }

    public void saveUser(User user) throws Exception {
        dao.save(user);
    }

    public User getByCredentials(String email, String password) {
        return dao.getUserByEmailAndPassowrd(email, password);
    }

    public List<User> getAll() {
        return dao.getUsers();
    }

}
