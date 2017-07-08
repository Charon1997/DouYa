package nexuslink.charon.douya.presenter;
import android.os.Handler;
import android.util.Log;

import nexuslink.charon.douya.bean.Movie;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.HttpService;
import nexuslink.charon.douya.view.IMainView;
import nexuslink.charon.douya.view.ISearchView;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MainPresenter {
    private final static String TAG = MainPresenter.class.getSimpleName();
    private IMainView mainView ;
    private Subscriber<MovieData> subscriber;
    private MovieData myData;
    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    public void getMovieInTheaters() {
        mainView.showLoading();
        subscriber = new Subscriber<MovieData>() {
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
                myData = movieData;
                if (movieData.getCount() != 0) {
                    Log.d(TAG, "加载数据");
                    mainView.initView(movieData);
                } else {
                    Log.d(TAG, "加载数据为0");
                }
            }
        };

        HttpService.getInstance().getInTheaters(subscriber);
    }

    public void clickItem(int position) {
        String id = myData.getSubjects().get(position).getId();
        String name = myData.getSubjects().get(position).getTitle();
        Log.d(TAG, "id:"+id);
        mainView.toMovieInf(id,name);
    }

}
