package nexuslink.charon.douya.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.os.Handler;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.ui.base.BaseActivity;

/**
 * Created by Administrator on 2017/4/18.
 */

public class WelcomeActivity extends BaseActivity {
    private ImageView mIvLogo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2500);


    }

    private void initView() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        mIvLogo = (ImageView) findViewById(R.id.iv_welcome);
        mIvLogo.setAnimation(alphaAnimation);
    }
}
