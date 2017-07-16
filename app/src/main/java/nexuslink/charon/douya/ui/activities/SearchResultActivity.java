package nexuslink.charon.douya.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.OnRecItemClickListener;
import nexuslink.charon.douya.presenter.SearchPresenter;
import nexuslink.charon.douya.ui.adapter.MainBookRecAdapter;
import nexuslink.charon.douya.ui.adapter.MainMovieRecAdapter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.ui.provider.SearchProvider;
import nexuslink.charon.douya.view.ISearchView;

import static nexuslink.charon.douya.bean.Constant.BOOK_ID;
import static nexuslink.charon.douya.bean.Constant.BOOK_NAME;
import static nexuslink.charon.douya.bean.Constant.MOVIE_ID;
import static nexuslink.charon.douya.bean.Constant.MOVIE_NAME;
import static nexuslink.charon.douya.bean.Constant.VISIBLE_THRESHOLD;

///**
// * Created by Charon on 2017/4/19.
// */

public class SearchResultActivity extends BaseActivity implements ISearchView {
    public static boolean movieLoading = false;
    public static boolean bookLoading = false;
    public static int bookMoreCount = 0;
    public static int movieMoreCount = 0;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private RelativeLayout mRelativeLayout;
    private TextView mTextView;
    private int current;
    private SearchPresenter searchPresenter = new SearchPresenter(this);
    private String searchString;
    private MainMovieRecAdapter movieRecAdapter;
    private MainBookRecAdapter bookRecAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchString = getIntent().getStringExtra("searchContent");
        current = getIntent().getIntExtra("currentNum", -1);
//        Toast.makeText(this, SearchString, Toast.LENGTH_SHORT).show();
        Log.d("123", "进入了此界面");
        saveDate();
        initView();

    }

    private void saveDate() {
        //保存搜索
        Log.d("Search", "saveDate");
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchProvider.AUTHORITY, SearchProvider.MODE);
        suggestions.saveRecentQuery(searchString, null);
    }

    public void clearHistory() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchProvider.AUTHORITY, SearchProvider.MODE);
        suggestions.clearHistory();
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.search_progress);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.search_relative);
        mTextView = (TextView) findViewById(R.id.search_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        if (current == 0) {
            mToolbar.setTitle("电影");
            searchPresenter.getSearchMovie(searchString);
        } else if (current == 1) {
            mToolbar.setTitle("读书");
            searchPresenter.getSearchBook(searchString);
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
                if (current == 0)
                    searchPresenter.getSearchMovie(searchString);
                else
                    searchPresenter.getSearchBook(searchString);
            }
        });
    }

    @Override
    public void scrollFootToast(int i) {
        //Toast.makeText(this, "主人，你的网络有点不好哟", Toast.LENGTH_SHORT).show();
        if (i == 1) {
            movieRecAdapter.ifError(true);
        } else bookRecAdapter.ifError(true);
    }

    @Override
    public void toMovieInf(String id, String name) {
        //判断是读书还是电影
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID, id);
        bundle.putString(MOVIE_NAME, name);
        Intent intent = new Intent(SearchResultActivity.this, MovieInfActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void toBookInf(String id, String name) {
        //图书
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_ID, id);
        bundle.putString(BOOK_NAME, name);
        Intent intent = new Intent(SearchResultActivity.this, BookInfActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void addMovieView(MovieData data) {
        if (movieMoreCount == 0) {
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            movieRecAdapter = new MainMovieRecAdapter(data, this);
            mRecyclerView.setAdapter(movieRecAdapter);
        } else movieRecAdapter.addData(data);

        movieRecAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                searchPresenter.clickMovieItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                Log.d("123", "开始前loading" + movieLoading + "movieMore" + movieMoreCount + "last" + totalItemCount );
                if (!movieLoading && totalItemCount < (lastVisibleItem + VISIBLE_THRESHOLD) && dy > 0&& movieRecAdapter.ifMore() ) {
                    //未在加载、且还有3个就要到底了
                    movieMoreCount++;
                    Log.d("123", "开始后loading" + movieLoading + "movieMore" + movieMoreCount);
                    searchPresenter.getMoreMovie(searchString,movieMoreCount);
                    movieLoading = true;
                }
            }
        });
    }

    @Override
    public void addBookView(BookData data) {
        if (bookMoreCount == 0) {
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            bookRecAdapter = new MainBookRecAdapter(data, this);
            mRecyclerView.setAdapter(bookRecAdapter);
        } else bookRecAdapter.addData(data);

        bookRecAdapter.setOnItemClickListener(new OnRecItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                searchPresenter.clickBookItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                Log.d("123", "开始前loading" + bookLoading + "bookMore" + bookMoreCount + "last" + totalItemCount);
                if (!bookLoading && totalItemCount < (lastVisibleItem + VISIBLE_THRESHOLD) && dy > 0 && bookRecAdapter.ifMore()) {
                    //未在加载、且还有3个就要到底了
                    bookMoreCount++;
                    Log.d("123", "开始后loading" + bookLoading + "movieMore" + bookMoreCount);
                    searchPresenter.getMoreBook(searchString,bookMoreCount);
                    bookLoading = true;
                }
            }
        });
    }
}
