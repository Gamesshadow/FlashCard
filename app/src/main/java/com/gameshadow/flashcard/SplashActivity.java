package com.gameshadow.flashcard;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    TextView slogan;
    //Animations
    Animation middleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        slogan = findViewById(R.id.slogan);
        //Animation Calls
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        //-----------Setting Animations to the elements of Splash
        slogan.setAnimation(middleAnimation);
        /* Start the Note-Activity
         * and close Splash-Screen after SPLASH_DISPLAY in milliseconds*/
        int SPLASH_DISPLAY = 3000;
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Login-Activity. */
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY);
    }
}