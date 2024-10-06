package gauzatuEragiketaTest;

import dataAccess.DataAccess;
import domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GauzatuEragiketaMockBlackTest
{
    protected MockedStatic<Persistence> persistenceMock;

    static DataAccess dataAccess;
    static User u4, u5;
    static TypedQuery<User> userQueryMock;

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

        userQueryMock = Mockito.mock(TypedQuery.class);
        Mockito.doReturn(userQueryMock).when(db).createQuery(Mockito.anyString(), Mockito.any());

        u4 = new User("u1", "1234", "Traveler");
        u4.setMoney(34.0);

        u5 = new User("u2", "1234", "Traveler");
        u5.setMoney(27.0);

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
        Mockito.doReturn(u4).when(userQueryMock).getSingleResult();
        assertTrue(dataAccess.gauzatuEragiketa("u4", 20, true));
    }

    @Test
    public void test2()
    {
        Mockito.doReturn(u5).when(userQueryMock).getSingleResult();
        assertTrue(dataAccess.gauzatuEragiketa("u5", 82, false));
    }

    @Test
    public void test3()
    {
        Mockito.doReturn(null).when(userQueryMock).getSingleResult();
        assertFalse(dataAccess.gauzatuEragiketa("u99",0,false));
    }

    @Test
    public void test4()
    {
        Mockito.doReturn(null).when(userQueryMock).getSingleResult();
        assertFalse(dataAccess.gauzatuEragiketa("null", 0, false));
    }

    @Test
    public void test5()
    {
        Mockito.doReturn(u5).when(userQueryMock).getSingleResult();
        assertFalse(dataAccess.gauzatuEragiketa("u5", -30, true));//Es un defecto
    }

    //VALORES LÍMITE

    @Test
    public void testVlamount1()
    {
        Mockito.doReturn(u5).when(userQueryMock).getSingleResult();
        assertFalse(dataAccess.gauzatuEragiketa("u5", -1, true));//Es un defecto
    }

    @Test
    public void testVlamount2()
    {
        Mockito.doReturn(u5).when(userQueryMock).getSingleResult();
        assertFalse(dataAccess.gauzatuEragiketa("u5", 0, true));//Es un defecto
    }

    @Test
    public void testVlamount3()
    {
        Mockito.doReturn(u5).when(userQueryMock).getSingleResult();
        assertTrue(dataAccess.gauzatuEragiketa("u5", 1, true));
    }
}
