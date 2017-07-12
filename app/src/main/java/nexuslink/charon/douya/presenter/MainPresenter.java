package nexuslink.charon.douya.presenter;
import android.os.Handler;
import android.util.Log;

import java.util.Random;

import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.book.BookTag;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.HttpService;
import nexuslink.charon.douya.view.IMainView;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MainPresenter {
    private final static String TAG = MainPresenter.class.getSimpleName();
    private IMainView mainView ;
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
                Log.d(TAG, "加载数据完成");
                mainView.hideLoading();
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

                Log.d(TAG, "加载数据失败，Error:"+e.toString());

            }

            @Override
            public void onNext(MovieData movieData) {
                myMovieData = movieData;
                if (movieData.getCount() != 0) {
                    Log.d(TAG, "加载数据");
                    mainView.initMovieView(movieData);
                } else {
                    Log.d(TAG, "加载数据为0");
                }
            }
        };

        HttpService.getInstance().getInTheaters(movieSubscriber);
    }

    public void getBookItem(String tag) {
        bookSubscriber = new Subscriber<BookData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BookData bookData) {
                myBookData = bookData;
                if (bookData.getBooks().size() != 0)
                mainView.initBookView(bookData);
            }
        };
        Log.d(TAG, tag);
        HttpService.getInstance().getSearchBookByTag(bookSubscriber,tag);

    }




    public void clickMovieItem(int position) {
        String id = myMovieData.getSubjects().get(position).getId();
        String name = myMovieData.getSubjects().get(position).getTitle();
        Log.d(TAG, "id:"+id);
        mainView.toMovieInf(id,name);
    }

    public void clickBookItem(int position) {
        String id = myBookData.getBooks().get(position).getId();
        String name = myBookData.getBooks().get(position).getTitle();
        mainView.toBookInf(id, name);
    }

}
