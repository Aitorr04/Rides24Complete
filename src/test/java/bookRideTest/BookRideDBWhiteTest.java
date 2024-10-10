package bookRideTest;

import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import org.junit.After;
import org.junit.Before;
import org.junit.*;
import testOperations.TestDataAccess;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class BookRideDBWhiteTest
{
	/*
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
        testDA.open();
    }

    @After
    public void tearDown()
    {
        testDA.close();
        dataAccess.close();
        testDA.open();
        testDA.removeDriver("d1");
        testDA.removeDriver("d2");
        testDA.removeTraveler(t1);
        testDA.removeBookings();
        testDA.close();
    }

    //Prueba para reservar un viaje que no está en la base de datos
    @Test
    public void test1()
    {
        Ride rideInexistente = new Ride("A", "B", Date.from(Instant.now()), 5, 20, null);
        try
        {
            assertFalse(dataAccess.bookRide("t1", rideInexistente, 1, 2.5));
            assertTrue(testDA.getBookings("t1").isEmpty());
        }
        catch (Exception e)
        {
            fail();
        }
    }

    //Prueba para reservar un viaje con un usuario que no está en la base de datos
    @Test
    public void test2()
    {
        assertFalse(dataAccess.bookRide("traveler que no está en la bd", r1, 1, 2.5));
        assertTrue(testDA.getBookings("traveler que no está en la bd").isEmpty());
    }

    //Prueba para reservar más asientos de los disponibles en un viaje
    @Test
    public void test3()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 5, 2.5));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    //Prueba para reservar un viaje cuando el viajero no tiene dinero suficiente
    @Test
    public void test4()
    {
        assertFalse(dataAccess.bookRide("t1", r2, 1, 2.5));
        assertTrue(testDA.getBookings("t1").isEmpty());
    }

    //Prueba para verificar que se hace correctamente la reserva de un viaje
    @Test
    public void test5()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 2.5));
        assertFalse(testDA.getBookings("t1").isEmpty());
    }
    */
}
