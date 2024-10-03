import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.*;
import testOperations.TestDataAccess;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class BookRideDBWhiteTest
{
    static DataAccess dataAccess = new DataAccess();
    //additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    private Traveler t1;
    private Ride r1, r2;

    @Before
    public void init()
    {
        testDA.open();
        dataAccess.open();
        r1 = testDA.createTestRide("d1", "A", "B", Date.from(Instant.now()), 2, 25);
        r2 = testDA.createTestRide("d2", "C", "D", Date.from(Instant.now()), 2, 100);
        t1 = testDA.createTestTraveler("t1", 50);
        testDA.close();
    }

    @After
    public void tearDown()
    {
        dataAccess.close();
        testDA.open();
        testDA.removeDriver("d1");
        testDA.removeDriver("d2");
        testDA.removeTraveler(t1);
        testDA.close();
    }

    @Test
    public void test1()
    {
        Ride rideInexistente = new Ride("A", "B", Date.from(Instant.now()), 5, 20, null);
        assertFalse(dataAccess.bookRide("t1", rideInexistente, 1, 2.5));
    }

    @Test
    public void test2()
    {
        assertFalse(dataAccess.bookRide("traveler que no está en la bd", r1, 1, 2.5));
    }

    @Test
    public void test3()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 5, 2.5));
    }

    @Test
    public void test4()
    {
        assertFalse(dataAccess.bookRide("t1", r2, 1, 2.5));
    }

    @Test
    public void test5()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 2.5));
    }
}
