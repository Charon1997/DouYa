package nexuslink.charon.douya.view;

import android.view.Menu;

/**
 * Created by Administrator on 2017/4/18.
 */

public interface IMainView {

    public void search(Menu menu);

    public void showLoading();

    public void hideLoading();

    public void toInf();

    public void exit();
}
