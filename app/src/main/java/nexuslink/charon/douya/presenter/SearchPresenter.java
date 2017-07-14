package nexuslink.charon.douya.presenter;

import android.os.Handler;
import android.util.Log;

import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.book.BookTag;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.HttpService;
import nexuslink.charon.douya.view.ISearchView;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/7.
 */

public class SearchPresenter {
    private final static String TAG = MainPresenter.class.getSimpleName();
    private ISearchView searchView ;
    private Subscriber<MovieData> movieSubscriber;
    private Subscriber<BookData> bookSubscriber;
    private MovieData myMovieData;
    private BookData myBookData;
    public SearchPresenter(ISearchView searchView) {
        this.searchView = searchView;
    }

    public void getMovieInTheaters(String searchString) {
        searchView.showLoading();
        movieSubscriber = new Subscriber<MovieData>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "加载数据完成");
                searchView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchView.showError();
                    }
                }, 1000);
                Log.d(TAG, "加载数据失败，Error:"+e.toString());
            }

            @Override
            public void onNext(MovieData movieData) {
                myMovieData = movieData;
                Log.d(TAG, "加载数据");
                if (movieData.getCount() != 0) {
                    searchView.addMovieView(movieData);
                    Log.d(TAG, "count" + movieData.getCount()+"size"+movieData.getSubjects().size());
                } else {
                    Log.d(TAG, "加载数据为0");
                }
            }
        };
        HttpService.getInstance().getSearchMovie(movieSubscriber,searchString,0,20);
    }

    public void getSearchBook(String searchString) {
        searchView.showLoading();
        bookSubscriber = new Subscriber<BookData>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "加载数据完成");
                searchView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchView.showError();
                    }
                }, 1000);
                Log.d(TAG, "加载数据失败，Error:"+e.toString());
            }

            @Override
            public void onNext(BookData bookData) {
                myBookData = bookData;
                Log.d(TAG, "加载数据");
                if (bookData.getCount() != 0) {
                    searchView.addBookView(bookData);
                } else {
                    Log.d(TAG, "加载数据为0");
                }
            }
        };
        if (BookTag.getBookTagList().contains(searchString)){
            //在Tag中
            HttpService.getInstance().getSearchBookByTag(bookSubscriber,searchString,0,20);
        }else {
            //没在Tag中
            HttpService.getInstance().getSearchBook(bookSubscriber, searchString,0,20);
        }
    }

    public void clickMovieItem(int position) {
        String id = myMovieData.getSubjects().get(position).getId();
        String name = myMovieData.getSubjects().get(position).getTitle();
        Log.d(TAG, "id:"+id);
        searchView.toMovieInf(id,name);
    }

    public void clickBookItem(int position) {
        String id = myBookData.getBooks().get(position).getId();
        String name = myBookData.getBooks().get(position).getTitle();
        Log.d(TAG, "id:"+id);
        searchView.toBookInf(id,name);
    }
}
