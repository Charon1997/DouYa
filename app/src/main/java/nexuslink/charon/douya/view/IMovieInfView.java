package nexuslink.charon.douya.view;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface IMovieInfView {
    //get信息
    String getName();

    void back();

    void loadView(String backImg,String mainImg,String cast,String director,double rating);

    void showLoading();

    void hideLoading();

    void showError();
}
