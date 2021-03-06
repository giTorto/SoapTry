package introsde.document.test;


import introsde.document.model.Measure;
import introsde.document.model.Person;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class PersonTest {

	@Test
	public void readPersonListTest() {
		System.out.println("--> TEST: readPersonList");
		List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
				.getResultList();
		for (Person person : list) {
			System.out.println("--> Person = "+person.getFirstname());
		}
		assertTrue(list.size()>0);
	}



	// test for adding person without using the DAO object, but isntead using the entity manager 
	// created in the testing unit by the beforetest method
	@Test
	public void addPersonTest() {
		System.out.println("--> TEST: createPerson");
		// count people before starting
		List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
				.getResultList();
		int originalCount = list.size();
		
		// Arrange
		Person at = new Person();
		at.setFirstname("Pinci");
		at.setLastname("Palleto");


		System.out.println("--> TEST: createPerson => persisting the new person");
		// Act
		tx.begin();
		em.persist(at);
		tx.commit();
		
		// Assert
		assertNotNull("Id should not be null", at.getId());
		
		// Query using CriteriaBuilder and CriteriaQuery Example
		Long idNewPerson = at.getId();
		
		System.out.println("--> TEST: createPerson => querying for the new person");
		// 1. Prepare the criteria builder and the criteria query to work with the model you want
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		
		// setup a metamodel for the root of the query
		Metamodel m = em.getMetamodel();
		EntityType<Person> Person_ = m.entity(Person.class);
		Root<Person> person = cq.from(Person_);
		
		// prepare a parameter expression to setup parameters for the query
		ParameterExpression<Integer> p = cb.parameter(Integer.class);
		
		// prepare the query
		cq.select(person).where(cb.equal(person.get("id"), p));
		
		// execute the query and set the parameters with values
		TypedQuery<Person> tq = em.createQuery(cq);
		tq.setParameter(p, idNewPerson.intValue());

		// get the results
		List<Person> newPersonList = tq.getResultList();
		
		assertEquals("Table has one entities", 1, newPersonList.size());
		assertEquals("Table has correct name", "Pinci", newPersonList.get(0).getFirstname());

		System.out.println("--> TEST: createPerson => deleting the new person");
		em.getTransaction().begin();
		em.remove(newPersonList.get(0));
		em.getTransaction().commit();

		list = em.createNamedQuery("Person.findAll", Person.class)
				.getResultList();

		assertEquals("Table has no entity", originalCount, list.size());
	}

	// same adding person test, but using the DAO object
	@Test
	public void addPersonWithDaoTest() {
		System.out.println("--> TEST: addPersonWithDao");

		List<Person> list = Person.getAll();
		int personOriginalCount = list.size();
		
		Person p = new Person();
		p.setFirstname("Pinco");
		p.setLastname("Pallino");
		Calendar c = Calendar.getInstance();
		c.set(1984, 6, 21);

		System.out.println("--> TEST: addPersonWithDao ==> persisting person");
		Person.savePerson(p);
		assertNotNull("Id should not be null", p.getId());

		System.out.println("--> TEST: addPersonWithDao ==> getting the list");
		list = Person.getAll();
		assertEquals("Table has two entities", personOriginalCount+1, list.size());
		
		Person newPerson = Person.getPersonById(p.getId());

		System.out.println("--> TEST: addPersonWithDao ==> removing new person");
		Person.removePerson(newPerson);
		list = Person.getAll();
		assertEquals("Table has two entities", personOriginalCount, list.size());
	}


    @Test
    public void personMeasurements() {
        System.out.println("--> TEST: person measurment");
        List<Person> list = Person.getAll();
        List<Measure> measures = null;
        for(Person p : list) {
            measures=p.getMeasure();
            System.out.println(p.getLastname()+" has some measurments:");
            assertNotNull(p);
            for (Measure m: measures) {
                System.out.println("  " + m.getRealMeasure().getMeasureName()+" : "+ m.getMid());
                assertNotNull(m.getRealMeasure().getMeasureName());
            }
        }
        assertNotNull(list);
        assertNotNull(measures);

    }

	@BeforeClass
	public static void beforeClass() {
		System.out.println("Testing JPA on lifecoach database using 'introsde-jpa' persistence unit");
		emf = Persistence.createEntityManagerFactory("introsde-jpa");
		em = emf.createEntityManager();
	}

	@AfterClass
	public static void afterClass() {
		em.close();
		emf.close();
	}

	@Before
	public void before() {
		tx = em.getTransaction();
	}


	private static EntityManagerFactory emf;
	private static EntityManager em;
	private EntityTransaction tx;
}



