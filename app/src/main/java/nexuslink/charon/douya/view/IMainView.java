package nexuslink.charon.douya.view;

import android.view.Menu;

import nexuslink.charon.douya.bean.Movie;
import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.movie.MovieData;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface IMainView {

    void search(Menu menu);

    void showLoading();

    void hideLoading();

    void toMovieInf(String id,String name);

    void exit();

    void addView();

    void initMovieView(MovieData data);

    void initBookView(BookData data);
    void showError();
}
