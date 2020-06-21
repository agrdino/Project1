package pr.whatEver.models;

public class ReviewRepository{
        public int sgID;
        public int rate;
        public boolean recommend;
        public String review;
        public int getSgID() {
                return sgID;
        }
        public int getRate() {
                return rate;
        }
        public String getReview() {
                return review;
        }
        public boolean getRecommend(){
                return recommend;
        }
}
