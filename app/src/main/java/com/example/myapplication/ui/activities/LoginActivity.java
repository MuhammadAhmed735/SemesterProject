package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


        private MaterialTextView headingTextView;
        private TextInputLayout emailLayout;
        private TextInputLayout passwordLayout;

        private TextInputEditText email_editText;
        private TextInputEditText pass_editText;

        private MaterialButton loginButton;
        private MaterialButton registerButton;
        private MaterialButton forgotPassButton;

        private String emailAddress;
        private String password;

        private final static int MINIMUM_PASSWORD_LENGTH= 6;
        private final static int MAXIMUM_PASSWORD_LENGTH= 15;
        private final static String emailPattern =android.util.Patterns.EMAIL_ADDRESS.toString();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            initializeViews();
            setClickListeners();
            setTextChangeListeners();
        }

        public void initializeViews()
        {
            headingTextView  = findViewById(R.id.login_heading);
            emailLayout      = findViewById(R.id.email_layout);
            passwordLayout   = findViewById(R.id.password_layout);

            email_editText   = findViewById(R.id.email_edit_text);
            pass_editText    = findViewById(R.id.password_edit_text);

            loginButton      = findViewById(R.id.login_button);
            registerButton   = findViewById(R.id.register_button);
            forgotPassButton = findViewById(R.id.forgotPassword);
        }
        public void setClickListeners()
        {
            loginButton.setOnClickListener(this);
            registerButton.setOnClickListener(this);
            forgotPassButton.setOnClickListener(this);

        }
        public void setTextChangeListeners()
        {
            pass_editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence input, int start, int before, int count) {

                    int passwordLength = input.length();

                    if (passwordLength < MINIMUM_PASSWORD_LENGTH) {
                        passwordLayout.setError("Password is too short");
                    }
                    else if (passwordLength > MAXIMUM_PASSWORD_LENGTH) {
                        passwordLayout.setError("Password is too long");
                    }
                    else {
                        passwordLayout.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            email_editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence input, int start, int before, int count) {

                    emailLayout.setError(null);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        @Override
        public void onClick(View view) {

            int viewId = view.getId();

            if(viewId == loginButton.getId())
            {
                validateCredentials();
            }
            else if(viewId == registerButton.getId())
            {
                startSignUpActivity();
            }
            else if(viewId == forgotPassButton.getId())
            {
                forgetPassword();
            }
        }

        public void startSignUpActivity()
        {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
        public void validateCredentials()
        {
            emailAddress = email_editText.getText().toString().trim();
            password     = pass_editText.getText().toString().trim();

            Boolean isEmpty = false;

            Boolean isValidEmail = false;
            Boolean isValidPassword = false;
            if(emailAddress.isEmpty())
            {
                emailLayout.setError("Please Enter Email Address");
                isValidEmail = false;
            }
            else {
                emailLayout.setError(null);
                if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())
                {
                    emailLayout.setError("Invalid Email Address");
                }
                else
                {
                    isValidEmail = true;
                }
            }
            if(password.isEmpty())
            {
                passwordLayout.setError("Please Enter Password");
                isValidPassword = false;
            }
            else {
                passwordLayout.setError(null);
                if(password.length()<MINIMUM_PASSWORD_LENGTH || password.length()>MAXIMUM_PASSWORD_LENGTH){
                    passwordLayout.setError("Invalid Password");
                }
                else {
                    isValidPassword = true;
                }
            }

            if(isValidEmail && isValidPassword)
            {
                emailLayout.setError(null);
                passwordLayout.setError(null);

                Intent intent = new Intent(this,StudentDashbaordActivity.class);
                startActivity(intent);
            }

        /*

            Boolean isInValid = false;
            if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() && !emailAddress.isEmpty())
            {
                isInValid = true;
                emailLayout.setError("Invalid Email Address");
            }
            else {
                //emailLayout.setError(null);
            }
            if((password.length()<MINIMUM_PASSWORD_LENGTH || password.length()>MAXIMUM_PASSWORD_LENGTH) && !password.isEmpty())
            {
                isInValid = true;
                passwordLayout.setError("Invalid Password length");
            }
            else {
             //   passwordLayout.setError(null);
            }
            if(isInValid)
            {
                emailLayout.setError(null);
                passwordLayout.setError(null);
                Toast.makeText(this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
            }
            */

        }

        public void forgetPassword()
        {

        }

    }