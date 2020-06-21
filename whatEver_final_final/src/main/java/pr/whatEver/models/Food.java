package pr.whatEver.models;

import java.sql.SQLException;
import java.sql.Types;
import static pr.whatEver.Environment.*;

public class Food {
    private int foodID;
    private String name;
    private int price;

    public Food(String name, int price) throws SQLException, ClassNotFoundException {
        this.name = name;
        this.price = price;
        storeProcedure = CreateConnection().prepareCall("{call AddFood(?,?)}");
        storeProcedure.setString(1, getName());
        storeProcedure.setInt(2, getPrice());
        storeProcedure.execute();
//        storeProcedure = CreateConnection().prepareCall("{call GetNewFood(?,?)}");
//        storeProcedure.setString(1, getName());
//        storeProcedure.registerOutParameter(2, Types.INTEGER);
//        storeProcedure.execute();
//        setFoodID(storeProcedure.getInt(2));
    }
    public Food ReturnFood(){
        return this;
    }
    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }
    public int getFoodID() {
        return foodID;
    }
    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
}
