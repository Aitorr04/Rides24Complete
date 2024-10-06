package GauzatuEragiketaTest;

import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.*;
import testOperations.TestDataAccess;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class GauzatuEragiketaDBWhiteTest
{
    static DataAccess dataAccess = new DataAccess();
    //additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    private User u1;
    private User u2;
    private User u3;

    @Before
    public void init()
    {
        testDA.open();
        dataAccess.open();
        u1 = testDA.createTestUser("u1", "123", "Traveler", 30.00);
        u2 = testDA.createTestUser("u2", "234", "Traveler", 22.00);
        u3 = testDA.createTestUser("u3", "345", "Traveler", 40.00);
        testDA.close();
    }

    @After
    public void tearDown()
    {
        dataAccess.close();
        testDA.open();
        testDA.removeUser("u1");
        testDA.removeUser("u2");
        testDA.removeUser("u3");
        testDA.close();
    }

    @Test
    public void test1()
    {
    	try {
    	dataAccess.close();
    	 assertFalse(dataAccess.gauzatuEragiketa("u1",12.04,true));
    	 dataAccess.open();
    	}catch (Exception e) {
    		fail();
    	}
    }

    @Test
    public void test2()
    {
        assertTrue(dataAccess.gauzatuEragiketa("u1",12.04,true));
    }

    @Test
    public void test3()
    {
    	 assertTrue(dataAccess.gauzatuEragiketa("u2",32.71,false));
    }

    @Test
    public void test4()
    {
    	 assertTrue(dataAccess.gauzatuEragiketa("u3",25.00,false));
    }

    @Test
    public void test5()
    {
    	 assertFalse(dataAccess.gauzatuEragiketa("u4",12.04,true));
    }
}