package bookRideTest;

import dataAccess.BookingData;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import org.junit.*;
import testOperations.TestDataAccess;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class BookRideDBBlackTest
{
    static DataAccess dataAccess = new DataAccess();
    //additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    private Traveler t1, t3, t4, t5, t6;
    private Ride r1, r2;

    @Before
    public void init()
    {
        testDA.open();
        dataAccess.open();
        r1 = testDA.createTestRide("d1", "A", "B", Date.from(Instant.now()), 2, 25);
        t1 = testDA.createTestTraveler("t1", 50);
        t3 = testDA.createTestTraveler("t3", 5);
        t4 = testDA.createTestTraveler("t4", 21.5);
        t5 = testDA.createTestTraveler("t5", 22.5);
        t6 = testDA.createTestTraveler("t6", 23.5);
        testDA.close();
        testDA.open();
    }

    @After
    public void tearDown()
    {
        testDA.close();
        testDA.open();
        testDA.removeDriver("d1");
        testDA.removeTraveler(t1);
        testDA.removeTraveler(t3);
        testDA.removeTraveler(t4);
        testDA.removeTraveler(t5);
        testDA.removeTraveler(t6);
        testDA.removeBookings();
        testDA.close();
        dataAccess.close();
    }

    @Test
    public void test1()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t1", r1, 1, 2.5)));
        assertFalse(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void test2()
    {
        assertFalse(dataAccess.bookRide(new BookingData(null, r1, 1, 2.5)));
    }

    @Test
    public void test3()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t2", r1, 1, 2.5)));
        assertTrue(testDA.getBookings("t2").isEmpty());
    }

    @Test
    public void test4()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", null, 1, 2.5)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void test5()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", new Ride("C", "D", Date.from(Instant.now()), 2, 100, r1.getDriver()), 1, 2.5)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void test6()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, -5, 2.5)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void test7()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 5, 2.5)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void test8()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 1, -2.5)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void test9()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 1, 100)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void test10()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t3", r1, 1, 2.5)));
        assertTrue(testDA.getBookings("t3").isEmpty());
    }

    //VALORES LÍMITE

    @Test
    public void testVlSeats1()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 0, 2.5)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlSeats2()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t1", r1, 2, 2.5)));
        assertFalse(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlSeats3()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 3, 2.5)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlDesk1()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 1, -1)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlDesk2()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t1", r1, 1, 0)));
        assertFalse(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlDesk3()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t1", r1, 1, 1)));
        assertFalse(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlDesk4()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t1", r1, 1, 24)));
        assertFalse(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlDesk5()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t1", r1, 1, 25)));
        assertFalse(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlDesk6()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 1, 26)));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    @Test
    public void testVlMoney1()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t4", r1, 1, 2.5)));
        assertTrue(testDA.getBookings("t4").isEmpty());
    }

    @Test
    public void testVlMoney2()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t5", r1, 1, 2.5)));
        assertFalse(testDA.getBookings("t5").isEmpty());
    }

    @Test
    public void testVlMoney3()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t6", r1, 1, 2.5)));
        assertFalse(testDA.getBookings("t6").isEmpty());
    }
}
