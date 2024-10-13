package bookRideTest;

import com.objectdb.o.UserException;
import dataAccess.BookingData;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import org.junit.*;
import org.mockito.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;


public class BookRideMockWhiteTest
{
    static DataAccess dataAccess;
    static Traveler t1;
    static Ride r1 = new Ride("A", "B", Date.from(Instant.now()), 2, 25, null);
    static TypedQuery<Traveler> query;

    protected MockedStatic<Persistence> persistenceMock;

    @Mock
    protected EntityManagerFactory entityManagerFactory;
    @Mock
    protected EntityManager db;
    @Mock
    protected EntityTransaction et;

    @Before
    public void init()
    {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
        persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
                .thenReturn(entityManagerFactory);

        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
        Mockito.doReturn(et).when(db).getTransaction();

        query = Mockito.mock(TypedQuery.class);
        Mockito.doReturn(query).when(db).createQuery(Mockito.anyString(), Mockito.any());
        Mockito.doReturn(Arrays.asList(t1)).when(query).getResultList();

        t1 = new Traveler("t1", "1234");
        t1.setMoney(50);
        dataAccess = new DataAccess(db);
    }

    @After
    public void tearDown()
    {
        persistenceMock.close();
    }

    //Prueba para reservar un viaje que no está en la base de datos
    @Test
    public void test1()
    {
        Mockito.doThrow(UserException.class).when(et).commit();
        Mockito.doThrow(RollbackException.class).when(et).rollback();
        Mockito.doReturn(Arrays.asList(t1)).when(query).getResultList();

        try
        {
            assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 1, 2.5)));
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
        Mockito.doReturn(new ArrayList<Traveler>()).when(query).getResultList();
        assertFalse(dataAccess.bookRide(new BookingData("t2", r1, 1, 2.5)));
    }

    //Prueba para reservar más asientos de los disponibles en un viaje
    @Test
    public void test3()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", r1, 5, 2.5)));
    }

    //Prueba para reservar un viaje cuando el viajero no tiene dinero suficiente
    @Test
    public void test4()
    {
        assertFalse(dataAccess.bookRide(new BookingData("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 100, null), 1, 2.5)));
    }

    //Prueba para verificar que se hace correctamente la reserva de un viaje
    @Test
    public void test5()
    {
        assertTrue(dataAccess.bookRide(new BookingData("t1", r1, 1, 2.5)));
    }
}
