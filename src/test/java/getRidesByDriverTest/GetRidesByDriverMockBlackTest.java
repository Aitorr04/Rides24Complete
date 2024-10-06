package getRidesByDriverTest;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import org.junit.*;
import org.mockito.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GetRidesByDriverMockBlackTest {

    static DataAccess dataAccess;
    static Driver d1, d2, d3, d4, d5;
    static Ride r1;
    static TypedQuery<Driver> query;

    protected MockedStatic<Persistence> persistenceMock;

    @Mock
    protected EntityManagerFactory entityManagerFactory;
    @Mock
    protected EntityManager db;
    @Mock
    protected EntityTransaction et;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = mockStatic(Persistence.class);
        persistenceMock.when(() -> Persistence.createEntityManagerFactory(any()))
                .thenReturn(entityManagerFactory);

        doReturn(db).when(entityManagerFactory).createEntityManager();
        doReturn(et).when(db).getTransaction();

        d1 = new Driver("driver1", "1234"); // Driver con viajes activos
        d2 = new Driver("driver2", "1234"); // Driver con viajes inactivos
        d3 = new Driver("driver3", "1234"); // Driver sin viajes

        d1.addRide("A", "B", Date.from(Instant.now()), 2, 25);

        dataAccess = new DataAccess(db);

        query = Mockito.mock(TypedQuery.class);
        Mockito.doReturn(query).when(db).createQuery(Mockito.anyString(), Mockito.any());
    }

    @After
    public void tearDown() {
        persistenceMock.close();
    }

    // Prueba 1: Driver con viajes activos
    @Test
    public void test1() {
        Mockito.doReturn(d1).when(query).getSingleResult();
        List<Ride> rides = dataAccess.getRidesByDriver("driver1");
        assertNotNull(rides);
        assertFalse(rides.isEmpty());
    }

    // Prueba 2: Driver con viajes inactivos
    @Test
    public void test2() {
        Mockito.doReturn(d2).when(query).getSingleResult();
        List<Ride> rides = dataAccess.getRidesByDriver("driver2");
        assertNotNull(rides);
        assertTrue(rides.isEmpty());

    }

    // Prueba 3: Driver sin viajes
    @Test
    public void test3() {
        Mockito.doReturn(d3).when(query).getSingleResult();
        List<Ride> rides = dataAccess.getRidesByDriver("driver3");
        assertNotNull(rides);
        assertTrue(rides.isEmpty());
    }

    // Prueba 4: Driver no existente
    @Test
    public void test4() {
        Mockito.doReturn(null).when(query).getSingleResult();
        List<Ride> rides = dataAccess.getRidesByDriver("driver4");
        assertNull(rides);
    }

    // Prueba 5: Username null
    @Test
    public void test5() {
        Mockito.doReturn(null).when(query).getSingleResult();
        List<Ride> rides = dataAccess.getRidesByDriver(null);
        assertNull(rides);
    }
}
