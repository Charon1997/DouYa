package nexuslink.charon.douya.presenter;

import android.os.Handler;

import java.util.List;

import nexuslink.charon.douya.bean.book.BookInf;
import nexuslink.charon.douya.biz.HttpService;
import nexuslink.charon.douya.view.IBookInfView;
import rx.Subscriber;

/**
 * Created by Charon on 2017/7/13.
 */

public class BookInfPresenter {
    private final static String TAG = MainPresenter.class.getSimpleName();
    private IBookInfView bookInfView;
    private Subscriber<BookInf> subscriber;

    public BookInfPresenter(IBookInfView bookInfView) {
        this.bookInfView = bookInfView;
    }

    public void getBookInf(String id) {
        bookInfView.showLoading();
        subscriber = new Subscriber<BookInf>() {
            @Override
            public void onCompleted() {
                bookInfView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bookInfView.showError();
                    }
                }, 500);
            }

            @Override
            public void onNext(BookInf data) {
                String backImg = data.getImages().getSmall();
                String mainImg = data.getImages().getLarge();
                String title = summaryIsNull(data.getTitle());
                String tag = connectTag(data.getTags(), data.getTags().size());
                String author = "暂无作者";

                String publisher = summaryIsNull(data.getPublisher());
                String publisherDate = summaryIsNull(data.getPubdate());

                int ratingCount = data.getRating().getNumRaters();

                String bookSummary = summaryIsNull(data.getSummary());
                String authorSummary = summaryIsNull(data.getAuthor_intro());
                String catalogSummary = summaryIsNull(data.getCatalog());

                double rating = 0.0;

                if (data.getAuthor().size() > 0)
                    author = data.getAuthor().get(0);

                if (data.getRating().getAverage() > 0)
                    rating = data.getRating().getAverage();


                bookInfView.loadView(backImg, mainImg, title, tag, author, rating, publisher, publisherDate, ratingCount, bookSummary, authorSummary, catalogSummary);
            }
        };
        HttpService.getInstance().getBookById(subscriber, id);
    }

    private String summaryIsNull(String dataSummary) {
        String summary = "";
        if (!dataSummary.equals("")) {
            summary = dataSummary;
        }
        return summary;
    }


    private String connectTag(List<BookInf.TagsBean> data, int size) {
        if (size > 3) {
            size = 3;
        }
        String information = "";
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                information = information + data.get(i).getName() + "/";
            } else information += data.get(i).getName();
        }
        return information;
    }
}
