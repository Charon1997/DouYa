package nexuslink.charon.douya.api;

import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.book.BookInf;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Charon on 2017/7/11.
 */

public interface BookService {
    @GET("v2/book/{id}")
    Observable<BookInf> getSearchById(@Path("id") String id);

    @GET("v2/book/isbn/{isbn}")
    Observable<BookInf> getSearchByIsbn(@Path("isbn") String isbn);

    @GET("v2/book/search")
    Observable<BookData> getSearchByQ(@Query("q") String text,@Query("start") int start,@Query("count") int count);

    @GET("v2/book/search")
    Observable<BookData> getSearchByTag(@Query("tag") String tag,@Query("start") int start,@Query("count") int count);
}

