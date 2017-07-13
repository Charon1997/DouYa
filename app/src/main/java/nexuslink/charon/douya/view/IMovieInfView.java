package nexuslink.charon.douya.view;



import nexuslink.charon.douya.bean.movie.MovieInf;

/**
 * Created by Charon on 2017/4/18.
 */

public interface IMovieInfView {
    //get信息
    String getName();

    void back();

    void loadView(String backImg, String mainImg, String cast, String director, double rating
    , String title, String originalTitle, String year, String country, String genres,int ratingCount
    ,String summary);

    void showLoading();

    void hideLoading();

    void showError();

    void initView(MovieInf data);
}
