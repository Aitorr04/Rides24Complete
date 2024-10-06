package getRidesByDriverTest;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import org.junit.*;
import org.mockito.Mockito;

import testOperations.TestDataAccess;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class GetRidesByDriverBDBlackTest {

    static DataAccess dataAccess = new DataAccess();
    static TestDataAccess testDA = new TestDataAccess();

    private Driver d1, d2, d3, d4, d5;
    private Ride r1;

    @Before
    public void init() {
        testDA.open();
        dataAccess.open();
        
        d1 = testDA.createTestDriver("driver1"); // Driver con viajes activos
        d2 = testDA.createTestDriver("driver2"); // Driver con viajes inactivos
        d3 = testDA.createTestDriver("driver3"); // Driver sin viajes
        d4 = testDA.createTestDriver("driver4"); // Lanza excepción al buscar
        d5 = testDA.createTestDriver("driver5"); // Excepción al obtener viajes
        
        // Creación de viajes
        r1 = testDA.createTestRide("driver1", "A", "B", Date.from(Instant.now()), 2, 25); // Viaje activo
        
        testDA.close();
    }

    @After
    public void tearDown() {
        dataAccess.close();
        testDA.open();
        testDA.removeDriver("driver1");
        testDA.removeDriver("driver2");
        testDA.removeDriver("driver3");
        testDA.removeDriver("driver4");
        testDA.removeDriver("driver5");
        testDA.close();
    }

    // Prueba 1: Driver con viajes activos
    @Test
    public void test1() {
        testDA.open();
        List<Ride> rides = dataAccess.getRidesByDriver("driver1");
        assertNotNull(rides);
        assertFalse(rides.isEmpty());
        testDA.close();
    }

    // Prueba 2: Driver con viajes inactivos
    @Test
    public void test2() {
        testDA.open();
        List<Ride> rides = dataAccess.getRidesByDriver("driver2");
        assertNotNull(rides);
        assertTrue(rides.isEmpty());
        testDA.close();
    }

    // Prueba 3: Driver sin viajes
    @Test
    public void test3() {
        testDA.open();
        List<Ride> rides = dataAccess.getRidesByDriver("driver3");
        assertNotNull(rides);
        assertTrue(rides.isEmpty());
        testDA.close();
    }

    // Prueba 4: Driver no existente
    @Test
    public void test4() {
        testDA.open();
        List<Ride> rides = dataAccess.getRidesByDriver("nonexistent");
        assertNull(rides);
        testDA.close();
    }

    // Prueba 5: Username null
    @Test
    public void test5() {
        testDA.open();
        List<Ride> rides = dataAccess.getRidesByDriver(null);
        assertNull(rides); 
        testDA.close();
    }


}
