package nexuslink.charon.douya.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_search, menu);
        search(menu);
        return true;
    }
    private void addDate() {
        mMovieList = new ArrayList<>();
        for (int i = 0 ;i < 6 ; i++) {
            Movie movie = new Movie(R.drawable.test_movie_img,"速度与激情"+i,"Charon"+i,i);
            mMovieList.add(movie);
        }

    }



    @Override
    public void search(Menu menu) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);//设置是否显示搜索按钮
        searchView.setQueryHint("查找");//设置提示信息
        searchView.setIconifiedByDefault(true);//设置搜索默认为图标
        //点击事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    Log.e("123onQueryTextSubmit","我是点击回车按钮");
                    searchView.setIconified(true);
                    int currentNum = mViewPager.getCurrentItem();

                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchContent",query);
                    bundle.putInt("currentNum",currentNum);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("123onQueryTextChange","我是内容改变");
                return false;
            }
        });
    }

    @Override
    public void exit() {

    }
}
