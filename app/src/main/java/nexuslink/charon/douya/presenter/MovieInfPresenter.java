package nexuslink.charon.douya.presenter;

import android.os.Handler;

import java.util.List;

import nexuslink.charon.douya.bean.movie.MovieInf;
import nexuslink.charon.douya.biz.HttpService;
import nexuslink.charon.douya.view.IMovieInfView;
import rx.Subscriber;

///**
// * Created by Charon on 2017/7/8.
// */

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
                }, 500);
            }

            @Override
            public void onNext(MovieInf data) {
                String backImg = data.getImages().getSmall();
                String mainImg = data.getImages().getLarge();
                String cast = connectCast(data.getCasts(), data.getCasts().size());
                String director = connectDirector(data.getDirectors(), data.getDirectors().size());
                String title = data.getTitle();
                String originalTitle = data.getOriginal_title();
                String year = data.getYear();
                String country = connectString(data.getCountries(), data.getCountries().size());
                String genres = connectString(data.getGenres(), data.getGenres().size());
                int ratingCount = data.getRatings_count();
                String summary = data.getSummary();
                double rating = 0.0;
//
//                if (data.getCasts().size() > 0) {
//                    cast = data.getCasts().get(0).getName();
//                } else cast = "暂无演员";
//                if (data.getDirectors().size() > 0) {
//                    director = data.getDirectors().get(0).getName();
//                } else director = "暂无导演";
                if (data.getRating().getAverage() != 0) {
                    rating = data.getRating().getAverage();
                }
                movieInfView.loadView(backImg, mainImg, cast, director, rating, title, originalTitle, year, country, genres, ratingCount, summary);
                movieInfView.initView(data);
            }
        };
        HttpService.getInstance().getMovieById(subscriber, id);
    }

    private String connectString(List<String> data, int size) {
        if (size > 3) {
            size = 3;
        }
        String information = "";
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                information = information + data.get(i) + "/";
            } else information += data.get(i);
        }
        return information;
    }

    private String connectCast(List<MovieInf.CastsBean> data, int size) {
        if (size > 3) {
            size = 3;
        }
        String information = "";
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                information = information + data.get(i) + "/";
            } else information += data.get(i);
        }
        return information;
    }

    private String connectDirector(List<MovieInf.DirectorsBean> data, int size) {
        if (size > 3) {
            size = 3;
        }
        String information = "";
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                information = information + data.get(i) + "/";
            } else information += data.get(i);
        }
        return information;
    }

}
