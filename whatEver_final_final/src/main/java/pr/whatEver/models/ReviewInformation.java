package pr.whatEver.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import static pr.whatEver.Environment.*;

public class ReviewInformation {
    private int rcID;
    private int rate;
    private String userName;
    private String resName;
    private String foodName;
    private long createTime;
    private boolean recommend;
    private String review;

    public ReviewInformation(int rate, String userName, long createTime, boolean recommend, String review) throws SQLException {
        this.rate = rate;
        this.userName = userName;
        this.createTime = createTime;
        this.recommend = recommend;
        this.review = review;
    }

    public ReviewInformation(int rate, String userName, String resName, long createTime, boolean recommend, String review, String foodName, int rcID) throws SQLException {
        this.rate = rate;
        this.userName = userName;
        this.resName = resName;
        this.createTime = createTime;
        this.recommend = recommend;
        this.review = review;
        this.foodName = foodName;
        this.rcID = rcID;
    }

    public int getRate() {
        return rate;
    }

    public String getUserName() {
        return userName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public boolean getRecommend() {
        return recommend;
    }

    public String getReview() {
        return review;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getResName() {
        return resName;
    }

    public int getRcID() {
        return rcID;
    }
}
