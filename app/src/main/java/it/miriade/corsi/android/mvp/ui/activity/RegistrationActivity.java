package it.miriade.corsi.android.mvp.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.miriade.corsi.android.mvp.AppConst;
import it.miriade.corsi.android.mvp.R;
import it.miriade.corsi.android.mvp.model.entity.User;
import it.miriade.corsi.android.mvp.presenter.RegistrationPresenter;
import it.miriade.corsi.android.mvp.view.RegistrationView;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener, RegistrationView {

    @NotEmpty
    @Email
    private EditText regEmail;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC)
    private EditText regPassword;

    @ConfirmPassword
    private EditText regRePassword;

    @NotEmpty
    private EditText regFirstName;

    @NotEmpty
    private EditText regLastName;

    @NotEmpty
    private EditText regBirthday;

    private Validator validator;

    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_registration);
        fab.setOnClickListener(this);

        regEmail = (EditText) findViewById(R.id.reg_email);
        regPassword = (EditText) findViewById(R.id.reg_password);
        regRePassword = (EditText) findViewById(R.id.reg_re_password);
        regFirstName = (EditText) findViewById(R.id.reg_firstname);
        regLastName = (EditText) findViewById(R.id.reg_lastname);
        regBirthday = (EditText) findViewById(R.id.reg_birthday);
        regBirthday.setOnClickListener(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras() != null) {
            String requestedEmail = getIntent().getExtras().getString(AppConst.EMAIL_INTENT_EXTRA);
            regEmail.setText(requestedEmail);
            regPassword.requestFocus();
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        presenter = new RegistrationPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_registration: {
                validator.validate();
                break;
            }
            case R.id.reg_birthday: {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        regBirthday.setText(String.format(AppConst.DATE_PATTERN, selectedday, selectedmonth+1, selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select birthdate");
                mDatePicker.show();
                break;
            }
            default: break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onValidationSucceeded() {
        User user = new User();
        user.setEmail(regEmail.getText().toString());
        user.setPassword(regPassword.getText().toString());
        user.setFirstName(regFirstName.getText().toString());
        user.setLastName(regLastName.getText().toString());
        try {
            user.setBirthday(new SimpleDateFormat(AppConst.DATE_FORMAT).parse(regBirthday.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            user.setBirthday(new Date(0));
        }
        presenter.register(user);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void alreadyRegistered(String email) {
        new AlertDialog.Builder(this)
                .setTitle("Attenzione")
                .setMessage("Esiste già un utente registrato con questa email: " + email)
                .setPositiveButton("Effettua il login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RegistrationActivity.this.finish();
                    }
                })
                .setNegativeButton("Ho capito", null)
                .show();
    }

    @Override
    public void registered() {
        new AlertDialog.Builder(this)
                .setTitle("Congratulazioni!")
                .setMessage("La registrazione è andata a buon fine. Effettua l'accesso!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RegistrationActivity.this.finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        RegistrationActivity.this.finish();
                    }
                })
                .show();
    }

    @Override
    public void registrationError() {
        new AlertDialog.Builder(this)
                .setTitle("Attenzione")
                .setMessage("La registrazione è fallita")
                .setPositiveButton("Ok", null)
                .show();
    }

}
