package pr.whatEver.models;
import java.sql.SQLException;
import static pr.whatEver.Environment.*;

public class Review {
    private int sgID;
    private int rate;
    private long createTime;
    private boolean recommend;
    private String review;

    public Review(int sgID, int rate, long createTime, boolean recommend, String review) throws SQLException, ClassNotFoundException {
        this.sgID = sgID;
        this.rate = setRate(rate);
        this.createTime = createTime;
        this.recommend = recommend;
        this.review = review;

        storeProcedure = CreateConnection().prepareCall("{call CreateReview(?,?,?,?,?)}");
        storeProcedure.setInt(1, getSgID());
        storeProcedure.setString(2, getReview());
        storeProcedure.setLong(3, getCreateTime());
        storeProcedure.setInt(4, getRate());
        storeProcedure.setBoolean(5, getRecommend());
        storeProcedure.execute();
    }

    public boolean getRecommend(){
        return recommend;
    }
    public int getSgID() {
        return sgID;
    }
    public long getCreateTime() {
        return createTime;
    }
    public String getReview() {
        return review;
    }
    public int getRate() {
        return rate;
    }
    private int setRate(int rate){
        if (rate < 0) return 0;
        if (rate > 5) return 5;
        return rate;
    }
}
