package getRidesByDriverTest;

import com.objectdb.o.UserException;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import org.junit.*;
import org.mockito.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class GetRidesByDriverMockWhiteTest {


    static DataAccess dataAccess;
    static Driver d1, d3;
    static TypedQuery<Driver> query;

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

        d1 = new Driver("d1", "1234"); //Driver con viajes
        d1.addRide("A", "B", Date.from(Instant.now()), 2, 25);

        d3 = new Driver("d3", "1234"); //Driver sin viajes

        query = Mockito.mock(TypedQuery.class);
        Mockito.doReturn(query).when(db).createQuery(Mockito.anyString(), Mockito.any());

        dataAccess = new DataAccess(db);
    }

    @After
    public void tearDown()
    {
        persistenceMock.close();
    }


    @Test
    public void test1()
    {
        Mockito.doReturn(null).when(query).getSingleResult();
        try
        {
            assertNull(dataAccess.getRidesByDriver("d2"));
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void test2()
    {
        Mockito.doReturn(d3).when(query).getSingleResult();
        try
        {
            List<Ride> result = dataAccess.getRidesByDriver("d3");
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void test3()
    {
        d1.getCreatedRides().get(0).setActive(false);
        Mockito.doReturn(d1).when(query).getSingleResult();
        try
        {
            List<Ride> result = dataAccess.getRidesByDriver("d1");
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void test4()
    {
        Mockito.doReturn(d1).when(query).getSingleResult();
        try
        {
            List<Ride> result = dataAccess.getRidesByDriver("d1");
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
        catch(Exception e)
        {
            fail();
        }
    }
}