package pr.whatEver.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import static pr.whatEver.Environment.*;
import static pr.whatEver.Environment.storeProcedure;

public class Restaurant {
    private String name;
    private String address;
    private long openHour;
    private long closeHour;

    public Restaurant(){}

    /**
     * Add restaurant
     * name, address, openHour, closeHour
     * @param name
     * @param address
     * @param openHour
     * @param closeHour
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Restaurant(String name, String address, long openHour, long closeHour) throws SQLException, ClassNotFoundException {
        this.name = name;
        this.address = address;
        this.openHour = openHour;
        this.closeHour = closeHour;

        storeProcedure = CreateConnection().prepareCall("{call AddRestaurant(?,?,?,?)}");
        storeProcedure.setString(1, getName());
        storeProcedure.setString(2, getAddress());
        storeProcedure.setLong(3, getOpenHour());
        storeProcedure.setLong(4, getCloseHour());
        storeProcedure.execute();
    }


    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOpenHour() {
        return openHour;
    }

    public long getCloseHour() {
        return closeHour;
    }
}
