package pr.whatEver.controllers;

import org.springframework.web.bind.annotation.*;
import pr.whatEver.models.*;

import java.sql.*;
import java.util.ArrayList;

import static pr.whatEver.Environment.*;

@RestController
public class AdminController{
    /**
     * Test API
     * @return
     */
    @GetMapping("/hello-world")
    public String getHelloMessage(){
        return "Hello World";
    }

    /**
     * Add food to database
     * name, average price
     * @param food
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping("/add-food")
    public @ResponseBody Food AddFood(@RequestBody Food food) throws SQLException, ClassNotFoundException {
        return new Food(food.getName(), food.getPrice());
    }

    /**
     * Get restaurant info after select
     * @param resName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping("/get-restaurant")
    public @ResponseBody RestaurantRepository GetRestaurant(@RequestBody ResName resName) throws SQLException, ClassNotFoundException {
//        storeProcedure = CreateConnection().prepareCall("{call GetRestaurant(?,?,?,?,?,?)}");
//        storeProcedure.setString(1,resName.getResName());
//        storeProcedure.registerOutParameter(2, Types.INTEGER);
//        storeProcedure.registerOutParameter(3, Types.VARCHAR);
//        storeProcedure.registerOutParameter(4, Types.VARCHAR);
//        storeProcedure.registerOutParameter(5, Types.BIGINT);
//        storeProcedure.registerOutParameter(6, Types.BIGINT);
//        storeProcedure.execute();
//
//        return new RestaurantRepository(
//                storeProcedure.getInt(2),
//                storeProcedure.getString(3),
//                storeProcedure.getString(4),
//                storeProcedure.getLong(5),
//                storeProcedure.getLong(6)
//        );
        return new RestaurantRepository(resName);
    }

    /**
     * Add menu with foodID and resID
     * @param menu
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping("/add-menu")
    public @ResponseBody MenuRepository AddMenu (@RequestBody Menu menu) throws SQLException, ClassNotFoundException {
        return new MenuRepository(new Menu(menu.getFoodID(), menu.getResID()));
    }

    /**
     * Add restaurant
     * name, address, openHour, closeHour
     * @param restaurant
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping("/add-restaurant")
    public @ResponseBody RestaurantRepository AddRestaurant (@RequestBody Restaurant restaurant) throws SQLException, ClassNotFoundException {
        return new RestaurantRepository(new ResName((new Restaurant(restaurant.getName(), restaurant.getAddress(),restaurant.getOpenHour(), restaurant.getCloseHour())).getName()));
    }

    /**
     * Get review for confirm
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @GetMapping("/get-review")
    public ArrayList<ReviewInformation> GetReview() throws SQLException, ClassNotFoundException {
        ArrayList<ReviewInformation> listRvC = new ArrayList<>();
        ResultSet resultSet = CreateStatement().executeQuery(
                "select u.name, f.name, res.name, rw.rcID, rw.revieww, rw.rate, rw.recommend, rw.createTime, rw.rcID " +
                        "from user u, reviewCheck rw, food f, restaurant res " +
                        "where u.userID = rw.userID" +
                        " and f.foodID = rw.foodID" +
                        " and res.resID = rw.resID;");

        while(resultSet.next()){
            ReviewInformation reviewInformation = new ReviewInformation
                    (
                            resultSet.getInt(6),
                            resultSet.getString(1),
                            resultSet.getString(3),
                            resultSet.getLong(8),
                            resultSet.getBoolean(7),
                            resultSet.getString(5),
                            resultSet.getString(2),
                            resultSet.getInt(9)
                    );
            listRvC.add(reviewInformation);
        }
        return listRvC;
    }

    @PostMapping("/confirm-review")
    public void ConfirmReview(@RequestBody ReviewID rcID) throws SQLException {
        String s = "";
        ResultSet resultSet =  CreateStatement().executeQuery("select * from reviewCheck where reviewCheck.rcID = " + rcID.getRcID() + ";");
        while(resultSet.next()) {
            s = "insert into review(sgId, userID, foodID, resID, createTime, rate, recommend, revieww) " +
                    "value(" + resultSet.getInt(2) + "," +
                    resultSet.getInt(3) + "," +
                    resultSet.getInt(4) + "," +
                    resultSet.getInt(5) + "," +
                    resultSet.getLong(6) + "," +
                    resultSet.getInt(7) + "," +
                    resultSet.getBoolean(8) + "," + "\"" +
                    resultSet.getString(9) + "\");";
        }
        CreateStatement().execute(s);
//            CreateStatement().execute(
//                    "insert into review(sgId, userID, foodID, resID, createTime, rate, recommend, revieww) " +
//                            "value(" + resultSet.getInt(2) + "," +
//                            resultSet.getInt(3) + "," +
//                            resultSet.getInt(4) + "," +
//                            resultSet.getInt(5) + "," +
//                            resultSet.getLong(6) + "," +
//                            resultSet.getInt(7) + "," +
//                            resultSet.getBoolean(8) + "," + "\"" +
//                            resultSet.getString(9) + "\");"
//            );
        CreateStatement().execute("delete from reviewCheck where reviewCheck.rcID =" + rcID.getRcID());
    }
    public void DeleteReviewCheck(){

    }
}
