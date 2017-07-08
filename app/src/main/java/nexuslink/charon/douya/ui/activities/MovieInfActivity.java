package nexuslink.charon.douya.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.presenter.MovieInfPresenter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IMovieInfView;

/**
 * Created by Administrator on 2017/4/20.
 */

public class MovieInfActivity extends BaseActivity implements IMovieInfView {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private String movieId="",movieName="";
    private ImageView mIvBackground,mIvMain;
    private MovieInfPresenter movieInfPresenter = new MovieInfPresenter(this);
    private ProgressBar mProgressBar;
    private TextView mTvError,mTvCast;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieinf);
        initData();
        initBaseView();
        movieInfPresenter.getMovieInf(movieId);
    }

    private void initData() {
        movieId = getIntent().getStringExtra(SearchResultActivity.MOVIE_ID);
        movieName = getIntent().getStringExtra(SearchResultActivity.MOVIE_NAME);
    }

    private void initBaseView() {
        mToolbar = (Toolbar) findViewById(R.id.movie_inf_toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.movie_inf_toolbar_layout);
        mIvBackground = (ImageView) findViewById(R.id.movie_inf_img_background);
        mIvMain = (ImageView) findViewById(R.id.movie_inf_img_main);
        mProgressBar = (ProgressBar) findViewById(R.id.movie_inf_progress);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.movie_inf_progress_RelativeLayout);
        mTvError = (TextView) findViewById(R.id.movie_inf_error_text);
        mTvCast = (TextView) findViewById(R.id.movie_inf_text_castname);

        toolbarLayout.setTitle(movieName);
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
        return movieName;
    }

    @Override
    public void back() {

    }

    @Override
    public void loadView(String backImg, String mainImg, String cast, String director, double rating) {
        Picasso.with(this).load(backImg).into(mIvBackground);
        Picasso.with(this).load(mainImg).into(mIvMain);
        mTvCast.setText(cast);
    }

    @Override
    public void showLoading() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTvError.setVisibility(View.GONE);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void hideLoading() {
        mRelativeLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mTvError.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTvError.setVisibility(View.VISIBLE);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieInfPresenter.getMovieInf(movieId);
            }
        });
    }
}
