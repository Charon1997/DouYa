package nexuslink.charon.douya.ui.activities;

import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.Movie;
import nexuslink.charon.douya.ui.adapter.MainPagerAdapter;
import nexuslink.charon.douya.ui.adapter.MainRecAdapter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IMainView;

public class MainActivity extends BaseActivity implements IMainView {
    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private List<View> mViewList;
    private MainPagerAdapter adapter;

    //临时数据
    private List<Movie> mMovieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("豆芽");
        setSupportActionBar(mToolbar);
        addDate();



        addView();

        adapter = new MainPagerAdapter(mViewList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void addView() {
        mViewList = new ArrayList<>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.recycler_main, null);
        mRecyclerView = (RecyclerView) view1.findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MainRecAdapter mRecAdapter = new MainRecAdapter(mMovieList);
        mRecyclerView.setAdapter(mRecAdapter);
        mViewList.add(view1);

        View view2 = LayoutInflater.from(this).inflate(R.layout.recycler_main, null);

        mViewList.add(view2);
    }

    private void addDate() {
        mMovieList = new ArrayList<>();
        for (int i = 0 ;i < 6 ; i++) {
            Movie movie = new Movie(R.drawable.test_movie_img,"速度与激情"+i,"Charon"+i,i);
            mMovieList.add(movie);
        }

    }
    @Override
    public void search() {

    }

    @Override
    public void exit() {

    }
}
