package nexuslink.charon.douya.presenter;

import android.graphics.Movie;
import android.os.Handler;
import android.util.Log;

import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.HttpService;
import nexuslink.charon.douya.ui.activities.MainActivity;
import nexuslink.charon.douya.ui.adapter.MainMovieRecAdapter;
import nexuslink.charon.douya.view.IMainView;
import rx.Subscriber;

/**
 * Created by Charon on 2017/7/7.
 */

public class MainPresenter {
    public static int completed = 0;
    private final static String TAG = MainPresenter.class.getSimpleName();
    private IMainView mainView;
    private Subscriber<MovieData> movieSubscriber;
    private Subscriber<BookData> bookSubscriber;
    private MovieData myMovieData;
    private BookData myBookData;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    public void getMovieInTheaters() {
        mainView.showLoading();
        movieSubscriber = new Subscriber<MovieData>() {
            @Override
            public void onCompleted() {
                completed++;
                Log.d(TAG, "加载数据完成");
                if (completed == 2){
                    mainView.hideLoading();
                    completed = 0;
                }

            }

            @Override
            public void onError(Throwable e) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainView.showError();
                    }
                }, 1000);

                Log.d(TAG, "加载数据失败，Error:" + e.toString());

            }

            @Override
            public void onNext(MovieData movieData) {
                myMovieData = movieData;
                if (movieData.getCount() != 0) {
                    Log.d(TAG, "加载数据"+movieData.getSubjects().size());
                    mainView.initMovieView(movieData);
                } else {
                    Log.d(TAG, "加载数据为0");
                }
            }
        };

        HttpService.getInstance().getInTheaters(movieSubscriber,0);
    }

    public void getMoreMovie(int count) {
        movieSubscriber = new Subscriber<MovieData>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "加载数据完成");
                MainActivity.movieLoading = false;
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "加载数据失败"+e.toString());
                mainView.scrollFootToast();
                MainActivity.movieMoreCount--;
            }

            @Override
            public void onNext(final MovieData data) {
                mainView.initMovieView(data);
            }
        };
        HttpService.getInstance().getInTheaters(movieSubscriber,count*20);
    }

    public void getMoreBook(String tag,int count) {
        bookSubscriber = new Subscriber<BookData>() {
            @Override
            public void onCompleted() {
                MainActivity.bookLoading = false;
                Log.d(TAG, "加载数据完成");
            }

            @Override
            public void onError(Throwable e) {
                mainView.scrollFootToast();
                MainActivity.bookMoreCount--;

                Log.d(TAG, "加载数据失败，Error:" + e.toString());
            }

            @Override
            public void onNext(BookData bookData) {
                Log.d("123", bookData.getTotal() + "total");
                mainView.initBookView(bookData);
            }
        };
        Log.d(TAG, tag);
        HttpService.getInstance().getSearchBookByTag(bookSubscriber, tag,count*20,20);

    }

    public void getBookItem(String tag) {
        mainView.showLoading();
        bookSubscriber = new Subscriber<BookData>() {
            @Override
            public void onCompleted() {
                completed++;
                Log.d(TAG, "加载数据完成");
                if (completed == 2){
                    mainView.hideLoading();
                    completed = 0;
                }
            }

            @Override
            public void onError(Throwable e) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainView.showError();
                    }
                }, 1000);

                Log.d(TAG, "加载数据失败，Error:" + e.toString());
            }

            @Override
            public void onNext(BookData bookData) {
                myBookData = bookData;
                if (bookData.getBooks().size() != 0)
                    mainView.initBookView(bookData);
            }
        };
        Log.d(TAG, tag);
        HttpService.getInstance().getSearchBookByTag(bookSubscriber, tag,0,20);

    }


    public void clickMovieItem(int position) {
        String id = myMovieData.getSubjects().get(position).getId();
        String name = myMovieData.getSubjects().get(position).getTitle();
        Log.d(TAG, "id:" + id);
        mainView.toMovieInf(id, name);
    }

    public void clickBookItem(int position) {
        String id = myBookData.getBooks().get(position).getId();
        String name = myBookData.getBooks().get(position).getTitle();
        mainView.toBookInf(id, name);
    }



    public boolean ifMoreMovie(int position) {
        if (myMovieData.getTotal() - myMovieData.getSubjects().size() <= 0) {
            //总数-当前数量等于0
            return false;
        } else return true;
    }


}
