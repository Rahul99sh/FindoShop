package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.lets_go_splash.CreateAnim;
import com.app.lets_go_splash.OnAnimationListener;
import com.app.lets_go_splash.StarterAnimation;
import com.pro.findoshop.MainActivity;
import com.pro.findoshop.R;
import java.util.ArrayList;

public class FirstScreen extends AppCompatActivity {
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        logo = findViewById(R.id.logo);
        new Handler().postDelayed(this::startAnim,2000);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(FirstScreen.this, Onboarding.class);
            startActivity(i);
            overridePendingTransition(R.anim.no_animation, R.anim.fade_out);
            finish();
        }, 3400);


    }
    void startAnim(){
            new StarterAnimation(getAnimList(), new OnAnimationListener() {
                @Override
                public void onStartAnim() {
                }

                @Override
                public void onRepeat() {
                }

                @Override
                public void onEnd() {
                    logo.setVisibility(View.GONE);
                }
            }).startSequentialAnimation(logo);
    }
    private ArrayList<Animation> getAnimList() {
        ArrayList<Animation> animList = new ArrayList<>();

        // We need to add INSTANCE when ever we need to access a object file in kotlin from java class
        // This denotes that CreateAnim is a singleton file and can able to have only one instance

        animList.add(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.no_animation));
        animList.add(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.zoom_out_fast));
        animList.add(CreateAnim.INSTANCE.createAnimation(getApplicationContext(), R.anim.fade_in_sp));

        return animList;
    }
}