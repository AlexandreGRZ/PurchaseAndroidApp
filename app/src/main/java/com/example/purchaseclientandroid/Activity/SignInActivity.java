package com.example.purchaseclientandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
import com.example.purchaseclientandroid.R;

public class SignInActivity extends AppCompatActivity {


    private TextView mTextView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        mTextView = findViewById(R.id.AlreadyAAccount);

        SpannableString mSpannableString = new SpannableString("Déjà inscrit ?");

        mSpannableString.setSpan(new UnderlineSpan(), 0, mSpannableString.length(), 0);

        mTextView.setText(mSpannableString);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation clickAnimation = AnimationUtils.loadAnimation(SignInActivity.this, R.anim.scale_animation);
                mTextView.startAnimation(clickAnimation);

                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}