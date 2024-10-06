package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Booking;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String name) {
		System.out.println(">> TestDataAccess: removeDriver");
		Driver d = db.find(Driver.class, name);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Driver createDriver(String name, String pass) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(name,pass);
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return driver;
    }
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
		
		public Driver addDriverWithRide(String name, String from, String to,  Date date, int nPlaces, float price) {
			System.out.println(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, name);
					if (driver==null) {
						System.out.println("Entra en null");
						driver=new Driver(name,null);
				    	db.persist(driver);
					}
				    driver.addRide(from, to, date, nPlaces, price);
					db.getTransaction().commit();
					System.out.println("Driver created "+driver);
					
					return driver;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return null;
	    }
		
		
		public boolean existRide(String name, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, name);
			if (d!=null) {
				return d.doesRideExists(from, to, date);
			} else 
			return false;
		}

		public Ride removeRide(String name, String from, String to, Date date ) {
			System.out.println(">> TestDataAccess: removeRide");
			Driver d = db.find(Driver.class, name);
			if (d!=null) {
				db.getTransaction().begin();
				Ride r= d.removeRide(from, to, date);
				db.getTransaction().commit();
				System.out.println("created rides" +d.getCreatedRides());
				return r;

			} else 
			return null;
		}

		public Ride createTestRide(String driverName, String from, String to, Date date, int nPlaces, float price)
		{
			return addDriverWithRide(driverName, from, to, date, nPlaces, price).getCreatedRides().get(0);
		}

		public Traveler createTestTraveler(String name, double money)
		{
			Traveler t = null;
			db.getTransaction().begin();
			if (db.find(Traveler.class, name) == null)
			{
				t = new Traveler(name, "1234");
				t.setMoney(money);
				db.persist(t);
			}
			db.getTransaction().commit();
			return t;
		}

		public void removeTraveler(Traveler t)
		{
			db.getTransaction().begin();
			if (db.find(Traveler.class, t.getUsername()) != null)
			{
				db.remove(db.find(Traveler.class, t.getUsername()));
			}
			db.getTransaction().commit();
		}

		public int getBookingCount()
		{
			return  db.createQuery("SELECT b FROM Booking b").getResultList().size();
		}

		public void setActiveRide(boolean active)
		{
			db.getTransaction().begin();
			db.createQuery("UPDATE Ride r SET r.active = :active").setParameter("active", active).executeUpdate();
			db.getTransaction().commit();
		}
		
		public User createUser(String name, String pass, String mota) {
			System.out.println(">> TestDataAccess: addUser");
			User user=null;
				db.getTransaction().begin();
				try {
					user=new User(name,pass,mota);
					db.persist(user);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return user;
	    }
				
		public boolean removeUser(String name) {
			System.out.println(">> TestDataAccess: removeUser");
			User u = db.find(User.class, name);
			if (u!=null) {
				db.getTransaction().begin();
				db.remove(u);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
		public User createTestUser(String userName, String pass, String mota, double money)
		{
			User u=createUser(userName,pass,mota);
			u.setMoney(money);
			return u;
		}

		public Driver createTestDriver(String name)
		{
			Driver d = null;
			db.getTransaction().begin();
			if (db.find(Driver.class, name) == null)
			{
				d = new Driver(name, "1234");
				db.persist(d);
			}
			db.getTransaction().commit();
			return d;
		}
}