package gauzatuEragiketaTest;

import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;

import org.junit.*;
import testOperations.TestDataAccess;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class GauzatuEragiketaDBBlackTest
{
    static DataAccess dataAccess = new DataAccess();
    //additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    private User u4, u5, u999;

    @Before
    public void init()
    {
        testDA.open();
        dataAccess.open();
        u4 = testDA.createTestUser("u4","456","Traveler",34.00);
        u5 = testDA.createTestUser("u5","567","Traveler",27.00);

        testDA.close();
    }

    @After
    public void tearDown()
    {
        dataAccess.close();
        testDA.open();
        testDA.removeUser("u4");
        testDA.removeUser("u5");
        testDA.close();
    }

    @Test
    public void test1()
    {
    	assertTrue(dataAccess.gauzatuEragiketa("u4", 20, true));
    }

    @Test
    public void test2()
    {
    	assertTrue(dataAccess.gauzatuEragiketa("u5", 82, false));
    }

    @Test
    public void test3()
    {
        assertFalse(dataAccess.gauzatuEragiketa("u99",0,false));
    }

    @Test
    public void test4()
    {
        assertFalse(dataAccess.gauzatuEragiketa("null", 0, false));
    }

    @Test
    public void test5()
    {
        assertFalse(dataAccess.gauzatuEragiketa("u5", -30, true));//Es un defecto
    }

    //VALORES LÍMITE

    @Test
    public void testVlamount1()
    {
        assertFalse(dataAccess.gauzatuEragiketa("u5", -1, true));//Es un defecto
    }

    @Test
    public void testVlamount2()
    {
        assertFalse(dataAccess.gauzatuEragiketa("u5", 0, true));//Es un defecto
    }

    @Test
    public void testVlamount3()
    {
        assertTrue(dataAccess.gauzatuEragiketa("u5", 1, true));
    }
}