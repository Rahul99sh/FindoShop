package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import com.app.lets_go_splash.CreateAnim;
import com.app.lets_go_splash.OnAnimationListener;
import com.app.lets_go_splash.StarterAnimation;
import com.pro.findoshop.MainActivity;
import com.pro.findoshop.R;
import com.pro.findoshop.databinding.ActivityFirstScreenBinding;
import java.util.ArrayList;

public class FirstScreen extends AppCompatActivity {
    ActivityFirstScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_first_screen);
        startAnim();

    }
    void startAnim(){
        new Handler().postDelayed(() -> {
            new StarterAnimation(getAnimList(), new OnAnimationListener() {
                @Override
                public void onStartAnim() { // TODO::
                }

                @Override
                public void onRepeat() { // TODO::
                }

                @Override
                public void onEnd() {
                    binding.logo.setVisibility(View.GONE);
                    Intent i = new Intent(FirstScreen.this, MainActivity.class);

                    startActivity(i);
//                overridePendingTransition(R.anim.whole_animation, R.anim.no_animation);
                    finish();
                }
            }).startSequentialAnimation(binding.logo);
        },500);
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