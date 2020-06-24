package pr.whatEver.models;

import pr.whatEver.Environment;

import java.sql.SQLException;
import java.sql.Types;

import static pr.whatEver.Environment.*;

public class MenuRepository {
    private String resName;
    private String foodName;

    /**
     * Get foodName, resName.
     * Get parameter from Menu
     * @param menu
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public MenuRepository(Menu menu) throws SQLException, ClassNotFoundException {
        storeProcedure = Environment.CreateConnection().prepareCall("{call GetMenu(?,?,?,?)}");
        storeProcedure.setInt(1, menu.getFoodID());
        storeProcedure.setInt(2, menu.getResID());
        storeProcedure.registerOutParameter(3, Types.VARCHAR);
        storeProcedure.registerOutParameter(4, Types.VARCHAR);
        storeProcedure.execute();
        foodName = storeProcedure.getString(3);
        resName = storeProcedure.getString(4);
    }

    public String getResName() {
        return resName;
    }

    public String getFoodName() {
        return foodName;
    }
}
