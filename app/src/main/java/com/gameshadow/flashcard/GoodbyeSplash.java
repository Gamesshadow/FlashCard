package com.gameshadow.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GoodbyeSplash extends AppCompatActivity {
    // Duration of splash in milliseconds

    private final int SPLASH_DISPLAY = 3000;

    TextView slogan, slogan2;
    //Animations
    Animation middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodbye);

        TextView slogan = findViewById(R.id.slogan);
        TextView slogan2 = findViewById(R.id.slogan2);

        //Animation Calls
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);


        //-----------Setting Animations to the elements of Splash
        slogan.setAnimation(middleAnimation);
        slogan2.setAnimation(middleAnimation);
        /* Start the Note-Activity
         * and close Splash-Screen after SPLASH_DISPLAY milliseconds*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Note-Activity. */
                Intent mainIntent = new Intent(GoodbyeSplash.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY);
    }
}