package GauzatuEragiketaTest;

import com.objectdb.o.UserException;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;

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


public class GauzatuEragiketaMockWhiteTest
{
    static DataAccess dataAccess;
    static User u;
    static TypedQuery<User> userQueryMock;

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

        userQueryMock = Mockito.mock(TypedQuery.class);
        Mockito.doReturn(userQueryMock).when(db).createQuery(Mockito.anyString(), Mockito.any());

        u = new User("u1", "123", "Traveler");
        u.setMoney(30.00);
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
        Mockito.doReturn(new ArrayList<Traveler>()).when(userQueryMock).getResultList();
        assertFalse(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 25, null), 1, 2.5));
    }

    @Test
    public void test3()
    {
        Mockito.doReturn(Arrays.asList(u)).when(userQueryMock).getResultList();
        assertFalse(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 25, null), 5, 2.5));
    }

    @Test
    public void test4()
    {
        Mockito.doReturn(Arrays.asList(t)).when(userQueryMock).getResultList();
        assertFalse(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 100, null), 1, 2.5));
    }

    @Test
    public void test5()
    {
        Mockito.doReturn(Arrays.asList(t)).when(userQueryMock).getResultList();
        assertTrue(dataAccess.bookRide("t1", new Ride("A", "B", Date.from(Instant.now()), 2, 25, null), 1, 2.5));
    }
}