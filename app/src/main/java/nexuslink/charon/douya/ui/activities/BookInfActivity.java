package nexuslink.charon.douya.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import nexuslink.charon.douya.R;
import nexuslink.charon.douya.presenter.BookInfPresenter;
import nexuslink.charon.douya.ui.base.BaseActivity;
import nexuslink.charon.douya.view.IBookInfView;

import static nexuslink.charon.douya.bean.Constant.BOOK_ID;
import static nexuslink.charon.douya.bean.Constant.BOOK_NAME;

///**
// * Created by Charon on 2017/7/12.
// */

public class BookInfActivity extends BaseActivity implements IBookInfView {
    private final static String TAG = BookInfActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private String bookId = "", bookName = "";
    private ImageView mIvBackground, mIvMain;
    private BookInfPresenter bookInfPresenter = new BookInfPresenter(this);
    private ProgressBar mProgressBar;
    private TextView mTvError, mTvTitle, mTvTag, mTvAuthor, mTvPublisher, mTvPubYear, mTvRatingCount, mTvRating, mTvSeeMoreBookSummary, mTvBookSummary,
            mTvSeeMoreAuthorSummary, mTvAuthorSummary, mTvCatalogSummary, mTvSeeMoreCatalogSummary;
    private MaterialRatingBar mMrbRating;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinf);
        initData();
        initBaseView();
        bookInfPresenter.getBookInf(bookId);
    }

    private void initData() {
        bookId = getIntent().getStringExtra(BOOK_ID);
        bookName = getIntent().getStringExtra(BOOK_NAME);
    }

    private void initBaseView() {
        findId();
        toolbarLayout.setTitle(bookName);
        mToolbar.setTitle("图书");
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
        mToolbar = (Toolbar) findViewById(R.id.book_inf_toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.book_inf_toolbar_layout);
        mIvBackground = (ImageView) findViewById(R.id.book_inf_img_background);
        mIvMain = (ImageView) findViewById(R.id.book_inf_img_main);

        //error
        mProgressBar = (ProgressBar) findViewById(R.id.book_inf_progress);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.book_inf_progress_relativeLayout);
        mTvError = (TextView) findViewById(R.id.book_inf_error_text);

        mTvTitle = (TextView) findViewById(R.id.book_inf_text_title);
        mTvTag = (TextView) findViewById(R.id.book_inf_text_tag);
        mTvAuthor = (TextView) findViewById(R.id.book_inf_text_author);
        mTvPublisher = (TextView) findViewById(R.id.book_inf_text_publisher);
        mTvPubYear = (TextView) findViewById(R.id.book_inf_text_year);
        mTvRating = (TextView) findViewById(R.id.book_inf_text_rating);
        mTvRatingCount = (TextView) findViewById(R.id.book_inf_text_ratings_count);
        mTvBookSummary = (TextView) findViewById(R.id.book_inf_text_book_summary);
        mTvSeeMoreBookSummary = (TextView) findViewById(R.id.book_inf_text_more_book_summary);
        mTvAuthorSummary = (TextView) findViewById(R.id.book_inf_author_summary);
        mTvSeeMoreAuthorSummary = (TextView) findViewById(R.id.book_inf_text_more_author_summary);
        mTvCatalogSummary = (TextView) findViewById(R.id.book_inf_catalog_summary);
        mTvSeeMoreCatalogSummary = (TextView) findViewById(R.id.book_inf_text_more_catalog_summary);

        mMrbRating = (MaterialRatingBar) findViewById(R.id.book_inf_ratingbar);
    }

    @Override
    public String getName() {
        return bookName;
    }

    @Override
    public void back() {

    }

    @Override
    public void loadView(String backImg, String mainImg, String title, String tag, String author, double rating
            , String publisher, String publisherDate, int ratingCount, String bookSummary, String authorSummary, String catalogSummary) {
        Picasso.with(this).load(backImg).into(mIvBackground);
        Picasso.with(this).load(mainImg).into(mIvMain);


        mTvRatingCount.setText(ratingCount + "人");
        mMrbRating.setRating((float) (rating / 2));
        mTvRating.setText(rating + "");
        if (!title.equals(""))
            mTvTitle.setText(title);
        if (!tag.equals(""))
            mTvTag.setText(tag);
        if (!author.equals(""))
            mTvAuthor.setText(author);
        if (!publisher.equals(""))
            mTvPublisher.setText(publisher);
        if (!publisherDate.equals(""))
            mTvPubYear.setText(publisherDate);
        if (!bookSummary.equals(""))
            mTvBookSummary.setText(bookSummary);
        if (!authorSummary.equals(""))
            mTvAuthorSummary.setText(authorSummary);
        if (!catalogSummary.equals(""))
            mTvCatalogSummary.setText(catalogSummary);

        mTvSeeMoreBookSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvBookSummary.setMaxLines(20);
                mTvSeeMoreBookSummary.setVisibility(View.GONE);
            }
        });
        mTvSeeMoreAuthorSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvAuthorSummary.setMaxLines(20);
                mTvSeeMoreAuthorSummary.setVisibility(View.GONE);
            }
        });
        mTvSeeMoreCatalogSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvCatalogSummary.setMaxLines(20);
                mTvSeeMoreCatalogSummary.setVisibility(View.GONE);
            }
        });

        mTvBookSummary.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, mTvBookSummary.getLineCount() + "bookSize");
                if (mTvBookSummary.getLineCount() <= 4) {
                    mTvSeeMoreBookSummary.setVisibility(View.GONE);
                } else {
                    mTvSeeMoreBookSummary.setVisibility(View.VISIBLE);
                    mTvBookSummary.setMaxLines(4);
                }
            }
        });

        mTvAuthorSummary.post(new Runnable() {
            @Override
            public void run() {
                if (mTvAuthorSummary.getLineCount() <= 4) {
                    mTvSeeMoreAuthorSummary.setVisibility(View.GONE);
                } else {
                    mTvSeeMoreAuthorSummary.setVisibility(View.VISIBLE);
                    mTvAuthorSummary.setMaxLines(4);
                }
            }
        });

        mTvCatalogSummary.post(new Runnable() {
            @Override
            public void run() {
                if (mTvCatalogSummary.getLineCount() <= 4) {
                    mTvSeeMoreCatalogSummary.setVisibility(View.GONE);
                } else {
                    mTvSeeMoreCatalogSummary.setVisibility(View.VISIBLE);
                    mTvCatalogSummary.setMaxLines(4);
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
                bookInfPresenter.getBookInf(bookId);
            }
        });
    }

}
