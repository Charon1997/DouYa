package nexuslink.charon.douya.api;

import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.bean.movie.MovieInf;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
/**
 * Created by Administrator on 2017/7/7.
 */

public interface MovieService {
    @GET("v2/movie/in_theaters")
    Observable<MovieData> getInTheaters();

    @GET("v2/movie/top250")
    Observable<MovieData> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("v2/movie/search")
    Observable<MovieData> getSearchMovie(@Query("q") String text);

    @GET("v2/movie/subject/{id}")
    Observable<MovieInf> getSearchById(@Path("id") String id);
}

