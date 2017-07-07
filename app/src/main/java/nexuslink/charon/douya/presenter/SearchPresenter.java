package nexuslink.charon.douya.presenter;

import android.os.Handler;
import android.util.Log;

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
    private Subscriber<MovieData> subscriber;
    public SearchPresenter(ISearchView searchView) {
        this.searchView = searchView;
    }

    public void getMovieInTheaters(String searchString) {
        searchView.showLoading();
        subscriber = new Subscriber<MovieData>() {
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
                Log.d(TAG, "加载数据");
                if (movieData.getCount() != 0) {
                    searchView.addView(movieData);
                    Log.d(TAG, "count" + movieData.getCount()+"size"+movieData.getSubjects().size());
                } else {
                    Log.d(TAG, "加载数据为0");
                }
            }
        };

        HttpService.getInstance().getSearchItem(subscriber,searchString);

    }
}
