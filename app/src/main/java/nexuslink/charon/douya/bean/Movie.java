package nexuslink.charon.douya.bean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class Movie {
    private int img;
    private String movieName;
    private String actorName;
    private int grade;

    public Movie(int img, String movieName, String actorName, int grade) {
        this.img = img;
        this.movieName = movieName;
        this.actorName = actorName;
        this.grade = grade;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
