package pr.whatEver.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import static pr.whatEver.Environment.*;

public class RestaurantRepository {
    private int resID;
    private String name;
    private String address;
    private long openHour;
    private long closeHour;

    /**
     * Get res information for admin
     * @param resName
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public RestaurantRepository(ResName resName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CreateStatement().executeQuery("select * from restaurant where name = " + "\"" + resName.getResName() + "\"");
        while(resultSet.next())
        {
            setResID(resultSet.getInt(1));
            setName(resultSet.getString(2));
            setAddress(resultSet.getString(3));
            setOpenHour(resultSet.getLong(4));
            setCloseHour(resultSet.getLong(5));
        }
    }

    public RestaurantRepository(String name, String address, long openHour, long closeHour){
        this.name = name;
        this.address = address;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public RestaurantRepository(int resID, String name, String address, long openHour, long closeHour){
        this.resID = resID;
        this.name = name;
        this.address = address;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }


    public int getResID() {
        return resID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getCloseHour() {
        return closeHour;
    }

    public long getOpenHour() {
        return openHour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCloseHour(long closeHour) {
        this.closeHour = closeHour;
    }

    public void setOpenHour(long openHour) {
        this.openHour = openHour;
    }
}
