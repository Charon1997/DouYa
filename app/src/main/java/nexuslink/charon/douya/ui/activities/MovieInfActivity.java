package nexuslink.charon.douya.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IMovieInfView;

/**
 * Created by Administrator on 2017/4/20.
 */

public class MovieInfActivity extends BaseActivity implements IMovieInfView {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolbarLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieinf);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.movie_inf_toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.movie_inf_toolbar_layout);
        toolbarLayout.setTitle("速度与激情8");
        mToolbar.setTitle("电影");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void back() {

    }
}
