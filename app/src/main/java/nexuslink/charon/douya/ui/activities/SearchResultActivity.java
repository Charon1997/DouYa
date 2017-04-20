package nexuslink.charon.douya.ui.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.Movie;
import nexuslink.charon.douya.biz.OnRecItemClickListener;
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
    private MainPagerAdapter adapter;
    private int current;
    //临时数据
    private List<Movie> mMovieList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String SearchContent = getIntent().getStringExtra("searchContent");
        current = getIntent().getIntExtra("currentNum",-1);
        Toast.makeText(this, SearchContent, Toast.LENGTH_SHORT).show();
        addDate();
        initView();
    }

    private void initView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        if (current == 0) {
            mToolbar.setTitle("电影");
        } else if (current == 1) {
            mToolbar.setTitle("读书");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MainRecAdapter mRecAdapter = new MainRecAdapter(mMovieList);
        mRecyclerView.setAdapter(mRecAdapter);
        mRecAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toInf();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }
    private void addDate() {
        mMovieList = new ArrayList<>();
        for (int i = 0 ;i < 6 ; i++) {
            Movie movie = new Movie(R.drawable.test_movie_img,"速度与激情"+i,"Charon"+i,i);
            mMovieList.add(movie);
        }

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toInf() {
        showLoading();
        //网络
        hideLoading();
        //判断是读书还是电影
        Intent intent = new Intent(SearchResultActivity.this,MovieInfActivity.class);
        startActivity(intent);
    }

    @Override
    public void back() {
        finish();
    }
}
