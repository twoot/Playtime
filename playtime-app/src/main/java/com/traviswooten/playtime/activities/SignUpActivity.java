package com.traviswooten.playtime.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.traviswooten.playtime.R;

public class SignUpActivity extends Activity {

    private EditText username, password, confirmPassword;
    private Button registerButton;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progress = new ProgressDialog(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.password_confirm);
        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                String usernameText = username.getText().toString();

                if(passwordText.equals(confirmPasswordText) && !passwordText.equals("")) {
                    if(!usernameText.equals("")) {
                        registerUser(usernameText, passwordText);
                    } else {
                        Toast.makeText(view.getContext(), getString(R.string.incorrect_credentials), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(view.getContext(), getString(R.string.passwords_dont_match), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void registerUser(final String username, final String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        progress.show();

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    startMainActivity();
                } else {
                    if(e.getMessage().contains("already taken")) {
                        loginUser(username, password);
                    } else {
                        System.out.println(e.getMessage());
                        Toast.makeText(getApplicationContext(), "Sign in error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    startMainActivity();
                } else {
                    System.out.println(e.getMessage());
                    Toast.makeText(getApplicationContext(), "Sign in error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startMainActivity() {
        progress.dismiss();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
