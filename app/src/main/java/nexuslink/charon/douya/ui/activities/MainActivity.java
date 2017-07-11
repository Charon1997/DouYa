package nexuslink.charon.douya.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.OnRecItemClickListener;
import nexuslink.charon.douya.presenter.MainPresenter;
import nexuslink.charon.douya.ui.adapter.MainBookRecAdapter;
import nexuslink.charon.douya.ui.adapter.MainMovieRecAdapter;
import nexuslink.charon.douya.ui.adapter.MainPagerAdapter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IMainView;

import static nexuslink.charon.douya.ui.activities.SearchResultActivity.MOVIE_ID;
import static nexuslink.charon.douya.ui.activities.SearchResultActivity.MOVIE_NAME;

public class MainActivity extends BaseActivity implements IMainView {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private List<View> mViewList;
    private MainPagerAdapter adapter;
    private RecyclerView mRecyclerView1,mRecyclerView2;
    private RelativeLayout mRelativeLayout;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private MainPresenter mainPresenter = new MainPresenter(this);
    private long mExitTime ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_progress_relative);
        mProgressBar = (ProgressBar) findViewById(R.id.main_progress);
        mTextView = (TextView) findViewById(R.id.main_text);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("豆芽");
        setSupportActionBar(mToolbar);
        mainPresenter.getMovieInTheaters();
        mainPresenter.getBookItem();
        addView();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_search, menu);
        search(menu);
        return true;
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
        searchView.setQueryHint("输入想要搜索");//设置提示信息
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
    public void showLoading() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);
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
        mTextView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
    }


    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        mRelativeLayout.setVisibility(View.VISIBLE);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.getMovieInTheaters();
            }
        });
        mViewPager.setVisibility(View.GONE);
    }
    @Override
    public void toMovieInf(String id,String name) {
        int currentNum = mViewPager.getCurrentItem();
        Intent intent = null ;
        Bundle bundle = new Bundle();
        if (currentNum == 0) {
            bundle.putString(MOVIE_ID, id);
            bundle.putString(MOVIE_NAME, name);
            intent = new Intent(MainActivity.this, MovieInfActivity.class);
            intent.putExtras(bundle);
        } else {

        }

        startActivity(intent);
    }


    @Override
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出豆芽", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void addView() {
        //设置TabLayout
        mViewList = new ArrayList<>();

        View view1 = LayoutInflater.from(this).inflate(R.layout.recycler_main, null);
        mRecyclerView1 = (RecyclerView) view1.findViewById(R.id.main_recycler);
        mViewList.add(view1);

        View view2 = LayoutInflater.from(this).inflate(R.layout.recycler_main, null);
        mRecyclerView2 = (RecyclerView) view2.findViewById(R.id.main_recycler);
        mViewList.add(view2);

        adapter = new MainPagerAdapter(mViewList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initMovieView(MovieData data) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView1.setLayoutManager(manager);
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        MainMovieRecAdapter mRecAdapter = new MainMovieRecAdapter(data,this);
        mRecyclerView1.setAdapter(mRecAdapter);
        mRecAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mainPresenter.clickMovieItem(position);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void initBookView(BookData data) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView2.setLayoutManager(manager);
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        MainBookRecAdapter mRecAdapter = new MainBookRecAdapter(data,this);
        mRecyclerView2.setAdapter(mRecAdapter);
        mRecAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //mainPresenter.clickBookItem(position);
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
