package dataAccess;

import domain.Ride;

public class BookingData {
    private final String username;
    private final Ride ride;
    private final int seats;
    private final double desk;

    public BookingData(String username, Ride ride, int seats, double desk) {
        this.username = username;
        this.ride = ride;
        this.seats = seats;
        this.desk = desk;
    }

    public String getUsername() {
        return username;
    }

    public Ride getRide() {
        return ride;
    }

    public int getSeats() {
        return seats;
    }

    public double getDesk() {
        return desk;
    }
}
