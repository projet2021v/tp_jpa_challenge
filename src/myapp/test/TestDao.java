package myapp.test;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import myapp.dao.Dao;
import myapp.model.Person;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDao {
	
	static Dao dao;
	static long id_temp;
	
	@BeforeClass
	public static void beforeAll() {
		dao = new Dao();
		dao.init();
	}
	
	@AfterClass
	public static void afterAll() {
		dao.close();
	}
	
	@Before
	public void setUp() {
		System.err.println("===================================================");
	}
	
	@After
	public void tearDown() {
		
	}
	
//	@Test
//	public void test01Connexion() {
//		EntityManager em = dao.getEmf().createEntityManager();
//		em.getTransaction().begin();
//		em.close();
//	}
	
	@Test
	public void test02addPerson() {
		Person p1 = new Person("Vincent", new Date());
		dao.addPerson(p1);
		id_temp = p1.getId();
	}
	
	@Test
	public void test03FindPerson() {
		Person p2 = dao.findPerson(id_temp);
		System.out.println(p2.toString());
	}
	
	@Test
	public void test04updatePerson() {
		Person p3 = dao.findPerson(id_temp);
		System.out.println("AVANT MODIF : " + p3.toString());
		p3.setFirstName("Michel");
		dao.updatePerson(p3);
		Person p4 = dao.findPerson(id_temp);
		System.out.println("APRES MODIF : " + p4.toString());
	}
	
	@Test
	public void test05removePerson() {
		dao.removePerson(id_temp);
	}
	
	@Test
	public void test06twoSamePersons() {
		Person p5 = new Person("Ben", new Date());
		Person p6 = new Person("Vince", new Date());
		dao.addPerson(p5);
		dao.addPerson(p6);
	}
	
	@Test
	public void test07namedQuery() {
		dao.findAllNamedQuery();
	}
	
	

}
