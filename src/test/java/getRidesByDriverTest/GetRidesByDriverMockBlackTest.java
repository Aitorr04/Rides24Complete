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
    static TypedQuery<Ride> query;

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
        d4 = new Driver("driver4", "1234"); // Driver no existente
        d5 = new Driver("driver5", "1234"); // Driver que lanza excepción al obtener viajes
        
        r1 = new Ride("A", "B", Date.from(Instant.now()), 2, 25, d1);

        dataAccess = new DataAccess(db);

        query = mock(TypedQuery.class);
        doReturn(query).when(db).createQuery(anyString(), eq(Ride.class));
    }

    @After
    public void tearDown() {
        persistenceMock.close();
    }

    // Prueba 1: Driver con viajes activos
    @Test
    public void test1() {
        Mockito.doReturn(Arrays.asList(r1)).when(query).getResultList();
        List<Ride> rides = dataAccess.getRidesByDriver("driver1");
        assertNotNull(rides);
        assertFalse(rides.isEmpty());
    }

    // Prueba 2: Driver con viajes inactivos
    @Test
    public void test2() {
        Mockito.doReturn(new ArrayList<>()).when(query).getResultList();
        List<Ride> rides = dataAccess.getRidesByDriver("driver2");
        assertNotNull(rides);
        assertTrue(rides.isEmpty());

    }

    // Prueba 3: Driver sin viajes
    @Test
    public void test3() {
        Mockito.doReturn(new ArrayList<>()).when(query).getResultList();
        List<Ride> rides = dataAccess.getRidesByDriver("driver3");
        assertNotNull(rides);
        assertTrue(rides.isEmpty());
    }

    // Prueba 4: Driver no existente
    @Test
    public void test4() {
        List<Ride> rides = dataAccess.getRidesByDriver("driver4");
        assertNull(rides);
    }

    // Prueba 5: Username null
    @Test
    public void test5() {
        List<Ride> rides = dataAccess.getRidesByDriver(null);
        assertNull(rides);

    }

    // Prueba 6: Excepción al obtener viajes
    @Test
    public void test6() {
        Mockito.doThrow(new PersistenceException()).when(query).getResultList();
        List<Ride> rides = dataAccess.getRidesByDriver("driver5");
        assertNull(rides);

    }

    
}
