package pr.whatEver.models;

import java.sql.SQLException;

import static pr.whatEver.Environment.*;

public class Menu {
    private int foodID;
    private int resID;

    /**
     * Add menu if not exist
     * Depend on foodID
     * @param foodID
     * @param resID
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Menu(int foodID, int resID) throws SQLException, ClassNotFoundException {
        this.foodID = foodID;
        this.resID = resID;

        storeProcedure = CreateConnection().prepareCall("{call AddMenu(?,?)}");
        storeProcedure.setInt(1, getResID());
        storeProcedure.setInt(2, getFoodID());
    }

    public int getResID() {
        return resID;
    }

    public int getFoodID() {
        return foodID;
    }
}
