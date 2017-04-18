package nexuslink.charon.douya.view;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface ISearchView {
    public void search();

    public void showLoading();

    public void hideLoading();

    public void showItem();

    public void hideItem();

    public void clickItem();

    public void back();
}
