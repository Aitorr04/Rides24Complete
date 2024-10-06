package getRidesByDriverTest;

import com.objectdb.o.UserException;
import dataAccess.DataAccess;
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

public class GetRidesByDriverMockTest {


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

        try {
            //define parameters
            String driverUsername="Driver Test";

            List<Ride> result =sut.getRidesByDriver(driverUsername);   

            assertNull(result);
        } catch (Exception e) {
            fail();
        }
    }

}