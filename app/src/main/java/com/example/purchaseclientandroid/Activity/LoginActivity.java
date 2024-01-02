package com.example.purchaseclientandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
import com.example.purchaseclientandroid.Activity.Main.MainActivity;
import com.example.purchaseclientandroid.Language;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.R;
import com.example.purchaseclientandroid.networks.LoginManager;

import java.io.IOException;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    LoginManager loginManager;

    TextView mTextView;
    Button LoginBtn;

    EditText LoginEditText;

    EditText PasswordEditText;

    Model modeleOfApplication;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //String langue =  Language.getLangueFromProperties(getApplicationContext());
        // Log.d("Language", langue);
        Language.changeLanguage(getApplicationContext(), "fr");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        try {
            modeleOfApplication = Model.getInstance(getApplicationContext());
            loginManager = new LoginManager(getApplicationContext());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        mTextView = findViewById(R.id.NotSignInActualy);
        LoginBtn = findViewById(R.id.buttonLogin);
        LoginEditText = findViewById(R.id.editTextUsername);
        PasswordEditText = findViewById(R.id.editTextPassword);


        SpannableString mSpannableString = new SpannableString("Pas encore Inscrit ?");

        mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);

        mTextView.setText(mSpannableString);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation clickAnimation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.scale_animation);
                mTextView.startAnimation(clickAnimation);

                // Démarrer une nouvelle activité (YourNextActivity)
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                    loginManager.performLoginAsync(
                                    LoginEditText.getText().toString(),
                                    PasswordEditText.getText().toString(),
                                    new LoginManager.OnLoginCompleteListener() {
                                        @Override
                                        public void onLoginComplete() {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(LoginActivity.this, "Login Succes !", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onLoginFailed() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(LoginActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                            );
                        }
                    }

        );
    }
}
