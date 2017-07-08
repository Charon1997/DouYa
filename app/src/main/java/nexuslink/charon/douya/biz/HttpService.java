package nexuslink.charon.douya.biz;

import java.util.concurrent.TimeUnit;

import nexuslink.charon.douya.api.MovieService;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.bean.movie.MovieInf;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/7.
 */

public class HttpService {
    private static final int DEFAULT_TIMEOUT = 5;
    private static final String BASE_URL = " https://api.douban.com/";
    Retrofit retrofit;
    MovieService movieService;

    private HttpService() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(MovieService.class);
    }



    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpService INSTANCE = new HttpService();
    }

    //获取单例
    public static HttpService getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getInTheaters(Subscriber<MovieData> subscriber) {
        movieService.getInTheaters()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getSearchItem(Subscriber<MovieData> subscriber,String searchString) {
        movieService.getSearchMovie(searchString)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getMovieById(Subscriber<MovieInf> subscriber, String id) {
        movieService.getSearchById(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
