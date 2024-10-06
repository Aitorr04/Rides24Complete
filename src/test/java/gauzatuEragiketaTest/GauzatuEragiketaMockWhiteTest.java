package gauzatuEragiketaTest;

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
    static User u1, u2, u3;
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

        u1 = new User("u1", "1234", "Traveler");
        u1.setMoney(30.0);

        u2 = new User("u2", "1234", "Traveler");
        u2.setMoney(22.0);

        u3 = new User("u3", "1234", "Traveler");
        u3.setMoney(40.0);

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
        Mockito.doThrow(UserException.class).when(et).begin();
        Mockito.doThrow(PersistenceException.class).when(et).rollback();
        Mockito.doReturn(u1).when(userQueryMock).getSingleResult();

        try
        {
            assertFalse(dataAccess.gauzatuEragiketa("u1",12.04,true));
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    public void test2()
    {
        Mockito.doReturn(u1).when(userQueryMock).getSingleResult();
        assertTrue(dataAccess.gauzatuEragiketa("u1",12.04,true));
    }

    @Test
    public void test3()
    {
        Mockito.doReturn(u2).when(userQueryMock).getSingleResult();
        assertTrue(dataAccess.gauzatuEragiketa("u2",32.71,false));
    }

    @Test
    public void test4()
    {
        Mockito.doReturn(u3).when(userQueryMock).getSingleResult();
        assertTrue(dataAccess.gauzatuEragiketa("u3",25.00,false));
    }

    @Test
    public void test5()
    {
        Mockito.doReturn(null).when(userQueryMock).getSingleResult();
        assertFalse(dataAccess.gauzatuEragiketa("u4",12.04,true));
    }
}