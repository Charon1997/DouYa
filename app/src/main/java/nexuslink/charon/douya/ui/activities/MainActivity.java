package nexuslink.charon.douya.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
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
import java.util.Random;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.book.BookTag;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.OnRecItemClickListener;
import nexuslink.charon.douya.biz.OnShakeListener;
import nexuslink.charon.douya.biz.ShakeListener;
import nexuslink.charon.douya.presenter.MainPresenter;
import nexuslink.charon.douya.ui.adapter.MainBookRecAdapter;
import nexuslink.charon.douya.ui.adapter.MainMovieRecAdapter;
import nexuslink.charon.douya.ui.adapter.MainPagerAdapter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IMainView;

import static nexuslink.charon.douya.bean.Constant.BOOK_ID;
import static nexuslink.charon.douya.bean.Constant.BOOK_NAME;
import static nexuslink.charon.douya.bean.Constant.MOVIE_ID;
import static nexuslink.charon.douya.bean.Constant.MOVIE_NAME;
import static nexuslink.charon.douya.bean.Constant.VISIBLE_THRESHOLD;

public class MainActivity extends BaseActivity implements IMainView {
    public static int bookMoreCount = 0;
    public static int movieMoreCount = 0;
    Vibrator mVibrator;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private List<View> mViewList;
    private MainPagerAdapter adapter;
    private RecyclerView mRecyclerView1, mRecyclerView2;
    private RelativeLayout mRelativeLayout;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private MainPresenter mainPresenter = new MainPresenter(this);
    private long mExitTime;
    private ShakeListener mShakeListener;
    public static boolean movieLoading = false;
    public static boolean bookLoading = false;
    private MainMovieRecAdapter movieAdapter = null;
    private MainBookRecAdapter bookAdapter = null;
    private static String bookTag;

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
        addView();
        mainPresenter.getMovieInTheaters();
        mainPresenter.getBookItem(chooseTag());
    }

    public String chooseTag() {
        Random random = new Random();
        bookTag = BookTag.BOOK_TAG[random.nextInt(BookTag.BOOK_TAG.length)];
        return bookTag;
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

        searchView.setQueryHint("输入想要搜索的内容");


        searchView.setIconifiedByDefault(true);//设置搜索默认为图标
        //点击事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    Log.e("123onQueryTextSubmit", "我是点击回车按钮");
                    searchView.setIconified(true);
                    int currentNum = mViewPager.getCurrentItem();

                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchContent", query);
                    bundle.putInt("currentNum", currentNum);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("123onQueryTextChange", "我是内容改变");
                return false;
            }
        });
    }

    @Override
    public void showLoading() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);
        mRecyclerView1.setVisibility(View.GONE);
        mRecyclerView2.setVisibility(View.GONE);
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
        mRecyclerView1.setVisibility(View.VISIBLE);
        mRecyclerView2.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        mRelativeLayout.setVisibility(View.VISIBLE);
        mRecyclerView1.setVisibility(View.GONE);
        mRecyclerView2.setVisibility(View.GONE);

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.getMovieInTheaters();
                mainPresenter.getBookItem(chooseTag());
            }
        });
        mViewPager.setVisibility(View.GONE);
    }

    @Override
    public void shakeHand() {
        mShakeListener = new ShakeListener(this);

        mShakeListener.setOnShakeListener(new OnShakeListener() {
            @Override
            public void onShake() {
                mShakeListener.stop();

                if (mViewPager.getCurrentItem() == 1) {
                    startVibrate();
                    mainPresenter.getBookItem(chooseTag());
                    MainPresenter.completed = 1;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mShakeListener.start();
                    }
                }, 1000);
            }
        });

    }

    @Override
    public void scrollFootToast(int i) {
        //Toast.makeText(this, "主人，网络不太好哟", Toast.LENGTH_SHORT).show();
        if (i == 1) {
            movieAdapter.ifError(true);
        } else bookAdapter.ifError(true);
    }

    @Override
    public void initMoreMovieView() {

    }

    private void startVibrate() {
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);//震动
        mVibrator.vibrate(new long[]{300, 200, 300, 200}, -1);
    }

    @Override
    public void toMovieInf(String id, String name) {
        Bundle bundle = new Bundle();

        bundle.putString(MOVIE_ID, id);
        bundle.putString(MOVIE_NAME, name);
        Intent intent = new Intent(MainActivity.this, MovieInfActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void toBookInf(String id, String name) {
        Bundle bundle = new Bundle();

        bundle.putString(BOOK_ID, id);
        bundle.putString(BOOK_NAME, name);
        Intent intent = new Intent(MainActivity.this, BookInfActivity.class);
        intent.putExtras(bundle);

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
        if (movieMoreCount == 0) {
            final RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            mRecyclerView1.setLayoutManager(manager);
            mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
            movieAdapter = new MainMovieRecAdapter(data, this);
            mRecyclerView1.setAdapter(movieAdapter);
        } else
            movieAdapter.addData(data);


        movieAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mainPresenter.clickMovieItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                Log.d("123", "开始前loading" + movieLoading + "movieMore" + movieMoreCount + "last" + totalItemCount);
                if (!movieLoading && totalItemCount < (lastVisibleItem + VISIBLE_THRESHOLD) && dy > 0 && movieAdapter.ifMore()) {
                    //未在加载、且还有3个就要到底了
                    movieAdapter.ifError(false);
                    movieMoreCount++;
                    Log.d("123", "开始后loading" + movieLoading + "movieMore" + movieMoreCount);
                    mainPresenter.getMoreMovie(movieMoreCount);
                    movieLoading = true;
                }
            }
        });
    }

    @Override
    public void initBookView(BookData data) {
        if (bookMoreCount == 0) {
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            mRecyclerView2.setLayoutManager(manager);
            mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
            bookAdapter = new MainBookRecAdapter(data, this);
            mRecyclerView2.setAdapter(bookAdapter);
        } else bookAdapter.addData(data);

        bookAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mainPresenter.clickBookItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        mRecyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                Log.d("123", "开始前loading" + bookLoading + "bookMore" + bookMoreCount + "last" + totalItemCount);
                if (!bookLoading && totalItemCount < (lastVisibleItem + VISIBLE_THRESHOLD) && dy > 0 && bookAdapter.ifMore()) {
                    //未在加载、且还有3个就要到底了
                    bookAdapter.ifError(false);
                    bookMoreCount++;
                    Log.d("123", "开始后loading" + bookLoading + "movieMore" + bookMoreCount);
                    mainPresenter.getMoreBook(bookTag, bookMoreCount);
                    bookLoading = true;
                }
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

    @Override
    protected void onStart() {
        super.onStart();
        shakeHand();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mShakeListener.stop();
    }

}
