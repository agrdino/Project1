package pr.whatEver.models;

import java.io.Serializable;
import java.sql.*;

import static pr.whatEver.Environment.*;

public class Suggestion implements Serializable {
    private int resID;
    private int sgID = 0;
    private Long createDate;
    private int foodID;

    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Suggestion() throws ClassNotFoundException, SQLException {
        createDate = System.currentTimeMillis();
        storeProcedure = CreateConnection().prepareCall("{call FoodCount(?)}");
        storeProcedure.registerOutParameter(1, Types.INTEGER);
        storeProcedure.execute();
        int total = storeProcedure.getInt(1);

        foodID = 0;
        while (foodID == 0) foodID = random.nextInt(total + 1);

        storeProcedure = CreateConnection().prepareCall("{call GetFood(?,?,?)}");
        storeProcedure.setInt(1, foodID);
        storeProcedure.registerOutParameter(2, Types.VARCHAR);
        storeProcedure.registerOutParameter(3, Types.INTEGER);
        storeProcedure.execute();

        CreateStatement().execute("insert into suggestion(userID, foodId, createTime) " +
                "value (" + 2 + "," + foodID + "," + System.currentTimeMillis() + ");");
    }

    public FoodRepository ReturnFood() throws SQLException {
        FoodRepository food = new FoodRepository(storeProcedure.getString(2), storeProcedure.getInt(3));
        return food;
    }
}
