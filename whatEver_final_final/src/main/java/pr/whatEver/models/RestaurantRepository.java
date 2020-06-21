package pr.whatEver.models;

public class RestaurantRepository {
    private String name;
    private String address;
    private long openHour;
    private long closeHour;

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
}
