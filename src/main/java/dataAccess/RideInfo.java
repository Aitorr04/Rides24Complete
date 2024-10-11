package dataAccess;

import java.util.Date;

public class RideInfo {
    private final String from;
    private final String to;
    private final Date date;
    private final int nPlaces;
    private final float price;
    private final String driverName;

    /**
     * @param from        the origin location of a ride
     * @param to          the destination location of a ride
     * @param date        the date of the ride
     * @param nPlaces     available seats
     * @param driverEmail to which ride is added
     *
     *
     */
    public RideInfo(String from, String to, Date date, int nPlaces, float price, String driverName) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.nPlaces = nPlaces;
        this.price = price;
        this.driverName = driverName;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public int getnPlaces() {
        return nPlaces;
    }

    public float getPrice() {
        return price;
    }

    public String getDriverName() {
        return driverName;
    }
}
