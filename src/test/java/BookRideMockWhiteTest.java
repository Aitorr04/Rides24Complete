import com.objectdb.o.UserException;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;


public class BookRideMockWhiteTest
{
    static DataAccess dataAccess;
    static Traveler t;
    static TypedQuery<Traveler> travelerQueryMock;

    protected MockedStatic<Persistence> persistenceMock;

    @Mock
    protected EntityManagerFactory entityManagerFactory;
    @Mock
    protected EntityManager db;
    @Mock
    protected EntityTransaction et;

    @Before
    public  void init()
    {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
        persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
                .thenReturn(entityManagerFactory);

        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
        Mockito.doReturn(et).when(db).getTransaction();

        travelerQueryMock = Mockito.mock(TypedQuery.class);
        Mockito.doReturn(travelerQueryMock).when(db).createQuery(Mockito.anyString(), Mockito.any());

        t = new Traveler("t1", "1234");
        t.setMoney(50);
        dataAccess = new DataAccess(db);
    }

    @After
    public  void tearDown()
    {
        persistenceMock.close();
    }

    @Test
    public void test1()
    {
        Mockito.doReturn(Arrays.asList(t)).when(travelerQueryMock).getResultList();

        Ride r = new Ride("A", "B", Date.from(Instant.now()), 2, 25, null);
        Mockito.doThrow(UserException.class).when(et).commit();
        Mockito.doThrow(RollbackException.class).when(et).rollback();

        assertTrue(dataAccess.bookRide("t1", r, 1, 2.5));
    }

    @Test
    public void test2()
    {
        Mockito.doReturn(new ArrayList<Traveler>()).when(travelerQueryMock).getResultList();
        assertFalse(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 25, null), 1, 2.5));
    }

    @Test
    public void test3()
    {
        Mockito.doReturn(Arrays.asList(t)).when(travelerQueryMock).getResultList();
        assertFalse(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 25, null), 5, 2.5));
    }

    @Test
    public void test4()
    {
        Mockito.doReturn(Arrays.asList(t)).when(travelerQueryMock).getResultList();
        assertFalse(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 100, null), 1, 2.5));
    }

    @Test
    public void test5()
    {
        Mockito.doReturn(Arrays.asList(t)).when(travelerQueryMock).getResultList();
        assertTrue(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 25, null), 1, 2.5));
    }
}
