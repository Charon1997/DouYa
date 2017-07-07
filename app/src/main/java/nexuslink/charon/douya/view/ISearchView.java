package nexuslink.charon.douya.view;

import nexuslink.charon.douya.bean.movie.MovieData;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface ISearchView {


    void showLoading();

    void hideLoading();

    void toInf();


    void addView(MovieData data);

    void showError();
}
