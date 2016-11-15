package it.miriade.corsi.android.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import it.miriade.corsi.android.mvp.AppConst;
import it.miriade.corsi.android.mvp.R;
import it.miriade.corsi.android.mvp.model.entity.User;
import it.miriade.corsi.android.mvp.presenter.LoginPresenter;
import it.miriade.corsi.android.mvp.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private LoginPresenter presenter;

    private EditText email;
    private EditText password;

    public enum InputType {
        email, password
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        FloatingActionButton fabSignup = (FloatingActionButton) findViewById(R.id.fabSignup);
        fabSignup.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        presenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
                this.presenter.login(email.getText().toString(), password.getText().toString());
                break;
            }
            case R.id.fabSignup: {
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
                break;
            }
            default: break;
        }
    }

    @Override
    public void validationFailed(InputType type) {
        switch (type) {
            case email: {
                email.setError("Devi specificare una email valida");
                break;
            }
            case password: {
                password.setError("Devi specificare una password");
                break;
            }
        }
    }

    @Override
    public void jumpToHome(User user) {
        Intent jumpIntent = new Intent(this, UserListActivity.class);
        jumpIntent.putExtra(AppConst.USER_INTENT_EXTRA, user);
        startActivity(jumpIntent);
    }

    @Override
    public void userNotRegistered(final String email) {
        new AlertDialog.Builder(this)
                .setTitle("Login fallito")
                .setMessage("Nessun utente registrato trovato per: " + email + ". Effettua la registrazione!")
                .setPositiveButton("Ok, mi registro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                        registrationIntent.putExtra(AppConst.EMAIL_INTENT_EXTRA, email);
                        startActivity(registrationIntent);
                    }
                })
                .setNegativeButton("Più tardi", null)
                .show();
    }

    @Override
    public void loginFailed() {
        new AlertDialog.Builder(this)
                .setTitle("Login fallito")
                .setMessage("L'email e la password inseriti non corrispondono ad un utente registrato. Verifica le tue credenziali e riprova")
                .setPositiveButton("Ok", null)
                .show();
    }

}
