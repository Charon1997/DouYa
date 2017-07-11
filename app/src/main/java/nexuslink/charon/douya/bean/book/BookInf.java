package nexuslink.charon.douya.bean.book;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class BookInf {

    /**
     * rating : {"max":10,"numRaters":95,"average":"9.4","min":0}
     * subtitle : 新版
     * author : ["吴淼"]
     * pubdate : 2012-9
     * tags : [{"count":31,"name":"漫画","title":"漫画"},{"count":19,"name":"奇幻","title":"奇幻"},{"count":18,"name":"吴淼","title":"吴淼"},{"count":15,"name":"塔希里亚故事集","title":"塔希里亚故事集"},{"count":11,"name":"中国","title":"中国"},{"count":5,"name":"魔法","title":"魔法"},{"count":5,"name":"西方奇幻","title":"西方奇幻"},{"count":5,"name":"塔希里亚","title":"塔希里亚"}]
     * origin_title :
     * image : https://img3.doubanio.com/mpic/s24412503.jpg
     * binding : 平装
     * translator : []
     * catalog :
     * pages : 222
     * images : {"small":"https://img3.doubanio.com/spic/s24412503.jpg","large":"https://img3.doubanio.com/lpic/s24412503.jpg","medium":"https://img3.doubanio.com/mpic/s24412503.jpg"}
     * alt : https://book.douban.com/subject/17604305/
     * id : 17604305
     * publisher : 接力出版社
     * isbn10 : 7544826449
     * isbn13 : 9787544826440
     * title : 塔希里亚故事集
     * url : https://api.douban.com/v2/book/17604305
     * alt_title :
     * author_intro :
     * summary : 《幻•影小说:塔希里亚故事集(新版)》一改魔幻文学惯常创作思路，摒弃虚幻无谓的格斗厮杀，而以深邃的探索、思索见长。与这些思索遥相呼应的，是风格独具的绘画。作者吴淼利用剪影绘画技巧，营造出一个深沉凝重的塔希里亚世界。它是简约的，又是丰润的。这是魔幻的，也是烂漫的。《幻•影小说:塔希里亚故事集(新版)》不仅开创“幻·影小说”之先河，也在一个另类魔法传奇故事中，感悟生命，思索生命，创造生命。
     * series : {"id":"4057","title":"塔希里亚故事集"}
     * price : 26.00元
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private SeriesBean series;
    private String price;
    private List<String> author;
    private List<TagsBean> tags;
    private List<?> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public SeriesBean getSeries() {
        return series;
    }

    public void setSeries(SeriesBean series) {
        this.series = series;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<?> getTranslator() {
        return translator;
    }

    public void setTranslator(List<?> translator) {
        this.translator = translator;
    }

    public static class RatingBean {
        /**
         * max : 10
         * numRaters : 95
         * average : 9.4
         * min : 0
         */

        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/spic/s24412503.jpg
         * large : https://img3.doubanio.com/lpic/s24412503.jpg
         * medium : https://img3.doubanio.com/mpic/s24412503.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class SeriesBean {
        /**
         * id : 4057
         * title : 塔希里亚故事集
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TagsBean {
        /**
         * count : 31
         * name : 漫画
         * title : 漫画
         */

        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
