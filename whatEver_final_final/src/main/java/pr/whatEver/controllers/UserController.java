package pr.whatEver.controllers;

import org.springframework.web.bind.annotation.*;
import pr.whatEver.models.*;
import static pr.whatEver.Environment.*;

import java.sql.*;
import java.util.ArrayList;

@RestController
public class UserController {
    /**
     * Create suggestion by random foodID
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @GetMapping("/create-suggestion")
    public FoodRepository CreateSuggestion() throws ClassNotFoundException, SQLException {
        return (new Suggestion()).ReturnFood();
    }


    /**
     * Create review by using @postMapping
     * @param  rw
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping(value = "/create-review")
    public @ResponseBody Review CreateReview(@RequestBody ReviewRepository rw) throws SQLException, ClassNotFoundException {
        return new Review(rw.getSgID(), rw.getRate(), rw.getRecommend(), rw.getReview());
    }

    /**
     * Return restaurant who has this food
     * Depend on openHour and closeHour
     * @param foodID - class for get foodID by read json
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping("/get-restaurantList")
    public @ResponseBody ArrayList<RestaurantRepository> GetRestaurantList(@RequestBody FoodID foodID) throws SQLException, ClassNotFoundException {
        ArrayList<RestaurantRepository> listRes = new ArrayList<>();
        ResultSet resultSet = CreateStatement().executeQuery("select name, address, openHour, closeHour from restaurant inner join menu using (resID) where foodID =" + foodID.getFoodID() + " and restaurant.openHour <= hour(now()) and restaurant.closeHour >= hour(now());");
        while(resultSet.next())
        {
            RestaurantRepository res = new RestaurantRepository
                    (
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getLong(3),
                            resultSet.getLong(4)
                    );
            listRes.add(res);
        }
        return listRes;
    }

    /**
     * Get review about food by foodID and resID
     * @param foodReview
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping("get-allReview")
    public @ResponseBody ArrayList<ReviewInformation> GetAllReview(@RequestBody FoodReview foodReview) throws SQLException, ClassNotFoundException {
        ArrayList<ReviewInformation> listRv = new ArrayList<>();
        ResultSet resultSet = CreateStatement().executeQuery(
                "select u.name, rw.revieww, rw.rate, rw.recommend, rw.createTime " +
                        "from user u, reviewCheck rw where u.userID = " + foodReview.getUserID() +
                        " and rw.foodID = " + foodReview.getFoodID() +
                        " and rw.resID = " + foodReview.getResID() + ";");

        while(resultSet.next()){
            ReviewInformation reviewInformation = new ReviewInformation
                    (
                            resultSet.getInt(3),
                            resultSet.getString(1),
                            resultSet.getLong(5),
                            resultSet.getBoolean(4),
                            resultSet.getString(2)
                    );
            listRv.add(reviewInformation);
        }
        return listRv;
    }

    @PostMapping("/add-suggestionOfficial")
    public @ResponseBody void AddSuggestion(@RequestBody SuggestionOfficial suggestionID) throws SQLException {
        storeProcedure = CreateConnection().prepareCall("{call AddSuggestionOffical(?,?)};");
        storeProcedure.setInt(1, suggestionID.getSgID());
        storeProcedure.setInt(2, suggestionID.getResID());
        storeProcedure.execute();
    }
}
