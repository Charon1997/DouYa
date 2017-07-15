package nexuslink.charon.douya.view;

import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.movie.MovieData;

/**
 * Created by Charon on 2017/4/18.
 */

public interface ISearchView {


    void showLoading();

    void hideLoading();

    void toMovieInf(String id, String name);

    void toBookInf(String id, String name);

    void addMovieView(MovieData data);

    void addBookView(BookData data);

    void showError();

    void scrollFootToast();
}
