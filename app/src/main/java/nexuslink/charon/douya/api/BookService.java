package nexuslink.charon.douya.api;

import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.book.BookInf;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.bean.movie.MovieInf;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/11.
 */

public interface BookService {
    @GET("v2/book/{id}")
    Observable<BookInf> getSearchById(@Path("id") String id);

    @GET("v2/book/isbn/{isbn}")
    Observable<BookInf> getSearchByIsbn(@Path("isbn") String isbn);

    @GET("v2/book/search")
    Observable<BookData> getSearchByQ(@Query("q") String text);

    @GET("v2/book/search")
    Observable<BookData> getSearchByTag(@Query("tag") String tag);
}

