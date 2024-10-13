package bookRideTest;

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
import static org.junit.Assert.assertFalse;

public class BookRideMockBlackTest
{
    static DataAccess dataAccess;
    static Traveler t1, t3, t4, t5, t6;
    static Ride r1;
    static TypedQuery<Traveler> query;

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

        t1 = new Traveler("t1", "1234");
        t1.setMoney(50);

        t3 = new Traveler("t3", "1234");
        t3.setMoney(5);

        t4 = new Traveler("t4", "1234");
        t4.setMoney(21.5);

        t5 = new Traveler("t5", "1234");
        t5.setMoney(22.5);

        t6 = new Traveler("t6", "1234");
        t6.setMoney(23.5);

        r1 = new Ride("A", "B", Date.from(Instant.now()), 2, 25, null);
        dataAccess = new DataAccess(db);

        query = Mockito.mock(TypedQuery.class);
        Mockito.doReturn(query).when(db).createQuery(Mockito.anyString(), Mockito.any());
        Mockito.doReturn(Arrays.asList(t1)).when(query).getResultList();
    }

    @After
    public void tearDown()
    {
        persistenceMock.close();
    }

    @Test
    public void test1()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 2.5));
    }

    @Test
    public void test2()
    {
        Mockito.doReturn(null).when(query).getResultList();
        assertFalse(dataAccess.bookRide(null, r1, 1, 2.5));
    }

    @Test
    public void test3()
    {
        Mockito.doReturn(new ArrayList<Traveler>()).when(query).getResultList();
        assertFalse(dataAccess.bookRide("t2", r1, 1, 2.5));
    }

    @Test
    public void test4()
    {
        assertFalse(dataAccess.bookRide("t1", null, 1, 2.5));
    }

    @Test
    public void test5()
    {
        assertFalse(dataAccess.bookRide("t1", new Ride("C", "D", Date.from(Instant.now()), 2, 100, r1.getDriver()), 1, 2.5));
    }

    @Test
    public void test6()
    {
        assertFalse(dataAccess.bookRide("t1", r1, -5, 2.5));
    }

    @Test
    public void test7()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 5, 2.5));
    }

    @Test
    public void test8()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, -2.5));
    }

    @Test
    public void test9()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, 100));
    }

    @Test
    public void test10()
    {
        Mockito.doReturn(Arrays.asList(t3)).when(query).getResultList();
        assertFalse(dataAccess.bookRide("t3", r1, 1, 2.5));
    }

    //VALORES LÍMITE

    @Test
    public void testVlSeats1()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 0, 2.5));
    }

    @Test
    public void testVlSeats2()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 2, 2.5));
    }

    @Test
    public void testVlSeats3()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 3, 2.5));
    }

    @Test
    public void testVlDesk1()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, -1));
    }

    @Test
    public void testVlDesk2()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 0));
    }

    @Test
    public void testVlDesk3()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 1));
    }

    @Test
    public void testVlDesk4()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 24));
    }

    @Test
    public void testVlDesk5()
    {
        assertTrue(dataAccess.bookRide("t1", r1, 1, 25));
    }

    @Test
    public void testVlDesk6()
    {
        assertFalse(dataAccess.bookRide("t1", r1, 1, 26));
    }

    @Test
    public void testVlMoney1()
    {
        Mockito.doReturn(Arrays.asList(t4)).when(query).getResultList();
        assertFalse(dataAccess.bookRide("t4", r1, 1, 2.5));
    }

    @Test
    public void testVlMoney2()
    {
        Mockito.doReturn(Arrays.asList(t5)).when(query).getResultList();
        assertTrue(dataAccess.bookRide("t5", r1, 1, 2.5));
    }

    @Test
    public void testVlMoney3()
    {
        Mockito.doReturn(Arrays.asList(t6)).when(query).getResultList();
        assertTrue(dataAccess.bookRide("t6", r1, 1, 2.5));
    }
}
