package nexuslink.charon.douya.view;

/**
 * Created by Charon on 2017/4/18.
 */

public interface IBookInfView {
    //get信息
    String getName();

    void back();

    void loadView(String backImg, String mainImg, String title, String tag, String author, double rating
            , String publisher, String publisherDate, int ratingCount, String bookSummary, String authorSummary, String catalogSummary);

    void showLoading();

    void hideLoading();

    void showError();

}
