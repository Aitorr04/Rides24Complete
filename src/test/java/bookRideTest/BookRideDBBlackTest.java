package bookRideTest;

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
    }

    @After
    public void tearDown()
    {
        dataAccess.close();
        testDA.open();
        testDA.removeDriver("d1");
        testDA.removeTraveler(t1);
        testDA.removeTraveler(t3);
        testDA.removeTraveler(t4);
        testDA.removeTraveler(t5);
        testDA.removeTraveler(t6);
        testDA.close();
    }

    @Test
    public void test1()
    {
        testDA.open();
        int initialBookingCount = testDA.getBookingCount();
        if (!dataAccess.bookRide("t1", r1, 1, 2.5)) fail();
        else
        {
            assertTrue(testDA.getBookingCount() > initialBookingCount);
        }
        testDA.close();
    }

    @Test
    public void test2()
    {
        assertFalse(dataAccess.bookRide(null, r1, 1, 2.5));
    }

    @Test
    public void test3()
    {
        assertFalse(dataAccess.bookRide("t2", r1, 1, 2.5));
    }

    @Test
    public void test4()
    {
        assertFalse(dataAccess.bookRide("t1", null, 1, 2.5));
    }

    @Test
    public void test5()
    {
        assertFalse(dataAccess.bookRide("t1", new Ride("C", "D", Date.from(Instant.now()), 2, 100, r1.getDriver()), 1, 2.5));
    }

    @Test
    public void test6()
    {
        assertFalse(dataAccess.bookRide("t1", r1, -5, 2.5));
    }

    @Test
    public void test7()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 5, 2.5));
    }

    @Test
    public void test8()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, -2.5));
    }

    @Test
    public void test9()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, 100));
    }

    @Test
    public void test10()
    {
        assertFalse(dataAccess.bookRide("t3", r1, 1, 2.5));
    }

    //VALORES LÍMITE

    @Test
    public void testVlSeats1()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 0, 2.5));
    }

    @Test
    public void testVlSeats2()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 2, 2.5));
    }

    @Test
    public void testVlSeats3()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 3, 2.5));
    }

    @Test
    public void testVlDesk1()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, -1));
    }

    @Test
    public void testVlDesk2()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 0));
    }

    @Test
    public void testVlDesk3()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 1));
    }

    @Test
    public void testVlDesk4()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 24));
    }

    @Test
    public void testVlDesk5()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 25));
    }

    @Test
    public void testVlDesk6()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, 26));
    }

    @Test
    public void testVlMoney1()
    {
        assertFalse(dataAccess.bookRide("t4", r1, 1, 2.5));
    }

    @Test
    public void testVlMoney2()
    {
        assertTrue(dataAccess.bookRide("t5", r1, 1, 2.5));
    }

    @Test
    public void testVlMoney3()
    {
        assertTrue(dataAccess.bookRide("t6", r1, 1, 2.5));
    }
}
