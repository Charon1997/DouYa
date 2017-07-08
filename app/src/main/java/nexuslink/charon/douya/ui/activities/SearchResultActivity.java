package nexuslink.charon.douya.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.Movie;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.OnRecItemClickListener;
import nexuslink.charon.douya.presenter.SearchPresenter;
import nexuslink.charon.douya.ui.adapter.MainPagerAdapter;
import nexuslink.charon.douya.ui.adapter.MainRecAdapter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IMainView;
import nexuslink.charon.douya.view.ISearchView;

/**
 * Created by Administrator on 2017/4/19.
 */

public class SearchResultActivity extends BaseActivity implements ISearchView {
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private RelativeLayout mRelativeLayout;
    private TextView mTextView;
    private int current;
    private SearchPresenter searchPresenter = new SearchPresenter(this);
    private String searchString;
    public final static String MOVIE_ID = "movieId";
    public final static String MOVIE_NAME = "movieName";
    public final static String BOOK_ID = "bookId";
    public final static String BOOK_NAME = "bookName";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchString = getIntent().getStringExtra("searchContent");
        current = getIntent().getIntExtra("currentNum",-1);
//        Toast.makeText(this, SearchString, Toast.LENGTH_SHORT).show();
        initView();

    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.search_progress);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.search_relative);
        mTextView = (TextView) findViewById(R.id.search_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        if (current == 0) {
            mToolbar.setTitle("电影");
            searchPresenter.getMovieInTheaters(searchString);
        } else if (current == 1) {
            mToolbar.setTitle("读书");
        }
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
    public void showLoading() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        mRelativeLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPresenter.getMovieInTheaters(searchString);
            }
        });
    }
    @Override
    public void toMovieInf(String id,String name) {
        //判断是读书还是电影
        Intent intent = null;
        Bundle bundle = new Bundle();
        if (current == 0){
            bundle.putString(MOVIE_ID, id);
            bundle.putString(MOVIE_NAME, name);
            intent = new Intent(SearchResultActivity.this,MovieInfActivity.class);
            intent.putExtras(bundle);
        } else {
            //图书
            bundle.putString(BOOK_ID, id);
            bundle.putString(BOOK_NAME, name);
        }
        startActivity(intent);
    }



    @Override
    public void addView(MovieData data) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MainRecAdapter mRecAdapter = new MainRecAdapter(data,this);
        mRecyclerView.setAdapter(mRecAdapter);
        mRecAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                searchPresenter.clickItem(position);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


}
