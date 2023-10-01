package com.gameshadow.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GoodbyeSplash extends AppCompatActivity {
    //Animations
    Animation middleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye);

        TextView slogan = findViewById(R.id.slogan);
        TextView slogan2 = findViewById(R.id.slogan2);
        //ImageView heart = findViewById(R.id.);

        //Animation Calls
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        //Setting Animations to the elements of Splash
        slogan.setAnimation(middleAnimation);
        slogan2.setAnimation(middleAnimation);

        /* Start the Note-Activity
         * and close Splash-Screen after SPLASH_DISPLAY milliseconds*/
        int SPLASH_DISPLAY = 3000;
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Next-Activity. */
            Intent mainIntent = new Intent(GoodbyeSplash.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY);
    }
}