package nexuslink.charon.douya.presenter;

import android.os.Handler;

import nexuslink.charon.douya.bean.movie.MovieInf;
import nexuslink.charon.douya.biz.HttpService;
import nexuslink.charon.douya.view.IMovieInfView;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/8.
 */

public class MovieInfPresenter {
    private final static String TAG = MainPresenter.class.getSimpleName();
    private IMovieInfView movieInfView;
    private Subscriber<MovieInf> subscriber;

    public MovieInfPresenter(IMovieInfView movieInfView) {
        this.movieInfView = movieInfView;
    }

    public void getMovieInf(String id) {
        movieInfView.showLoading();
        subscriber = new Subscriber<MovieInf>() {
            @Override
            public void onCompleted() {
                movieInfView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieInfView.showError();
                    }
                },500);
            }

            @Override
            public void onNext(MovieInf data) {
                String backImg = data.getImages().getSmall();
                String mainImg = data.getImages().getLarge();
                String cast,director= "";
                double rating = 0.0;
                if (data.getCasts().size() > 0) {
                     cast = data.getCasts().get(0).getName();
                } else cast = "暂无演员";
                if (data.getDirectors().size() > 0) {
                    director = data.getDirectors().get(0).getName();
                } else director = "暂无导演";
                if (data.getRating().getAverage() != 0) {
                    rating = data.getRating().getAverage();
                }
                movieInfView.loadView(backImg,mainImg,cast,director,rating);
            }
        };
        HttpService.getInstance().getMovieById(subscriber,id);
    }

}
