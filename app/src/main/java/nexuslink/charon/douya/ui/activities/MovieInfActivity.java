package nexuslink.charon.douya.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.movie.MovieInf;
import nexuslink.charon.douya.biz.OnRecItemClickListener;
import nexuslink.charon.douya.presenter.MovieInfPresenter;
import nexuslink.charon.douya.ui.adapter.MovieInfRecAdapter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IMovieInfView;

/**
 * Created by Charon on 2017/4/20.
 */

public class MovieInfActivity extends BaseActivity implements IMovieInfView {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private String movieId = "", movieName = "";
    private ImageView mIvBackground, mIvMain;
    private MovieInfPresenter movieInfPresenter = new MovieInfPresenter(this);
    private ProgressBar mProgressBar;
    private TextView mTvError, mTvTitle, mTvGenres, mTvCountry, mTvOriginalTitle, mTvPubYear, mTvRatingCount, mTvRating, mTvSeeMoreSummary, mTvSummary;
    private MaterialRatingBar mMrbRating;
    private RelativeLayout mRelativeLayout;
    private RecyclerView mRvCast;

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
        findId();
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

    private void findId() {
        mToolbar = (Toolbar) findViewById(R.id.movie_inf_toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.movie_inf_toolbar_layout);
        mIvBackground = (ImageView) findViewById(R.id.movie_inf_img_background);
        mIvMain = (ImageView) findViewById(R.id.movie_inf_img_main);
        mProgressBar = (ProgressBar) findViewById(R.id.movie_inf_progress);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.movie_inf_progress_RelativeLayout);
        mTvError = (TextView) findViewById(R.id.movie_inf_error_text);

        mTvTitle = (TextView) findViewById(R.id.movie_inf_text_title);
        mTvOriginalTitle = (TextView) findViewById(R.id.movie_inf_text_original_title);
        mTvGenres = (TextView) findViewById(R.id.movie_inf_text_genres);
        mTvCountry = (TextView) findViewById(R.id.movie_inf_text_countries);
        mTvPubYear = (TextView) findViewById(R.id.movie_inf_text_year);
        mTvRating = (TextView) findViewById(R.id.movie_inf_text_rating);
        mTvRatingCount = (TextView) findViewById(R.id.movie_inf_text_ratings_count);
        mMrbRating = (MaterialRatingBar) findViewById(R.id.movie_inf_ratingbar);

        mTvSummary = (TextView) findViewById(R.id.movie_inf_text_summary);
        mTvSeeMoreSummary = (TextView) findViewById(R.id.movie_inf_text_more_summary);
        mRvCast = (RecyclerView) findViewById(R.id.movie_inf_rv_director_cast);
    }

    public void initView(MovieInf data) {
        mRvCast.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        mRvCast.setItemAnimator(new DefaultItemAnimator());
        MovieInfRecAdapter mRecAdapter = new MovieInfRecAdapter(this, data);
        mRvCast.setAdapter(mRecAdapter);
        mRecAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //mainPresenter.clickItem(position);
                Toast.makeText(MovieInfActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void loadView(String backImg, String mainImg, String cast, String director, double rating
            , String title, String originalTitle, String year, String country, String genres, int ratingCount
            , String summary) {
        Picasso.with(this).load(backImg).into(mIvBackground);
        Picasso.with(this).load(mainImg).into(mIvMain);

        mTvTitle.setText(title);
        mTvOriginalTitle.setText(originalTitle);
        mTvGenres.setText(genres);
        mTvCountry.setText(country);
        mTvPubYear.setText(year);
        mTvRating.setText(rating + "");
        mTvRatingCount.setText(ratingCount + "人");
        mMrbRating.setRating((float) (rating / 2));

        mTvSummary.setText(summary);
        mTvSeeMoreSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvSummary.setMaxLines(20);
                mTvSeeMoreSummary.setVisibility(View.GONE);
                //Toast.makeText(MovieInfActivity.this, "看更多", Toast.LENGTH_SHORT).show();
            }
        });

        mTvSummary.post(new Runnable() {
            @Override
            public void run() {
                if (mTvSummary.getLineCount() <= 4) {
                    mTvSeeMoreSummary.setVisibility(View.GONE);
                } else {
                    mTvSeeMoreSummary.setVisibility(View.VISIBLE);
                    mTvSummary.setMaxLines(4);
                }
            }
        });


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

    @Override
    public void back() {

    }

    @Override
    public String getName() {
        return movieName;
    }


}
