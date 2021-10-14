package myapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import myapp.model.Person;

public class Dao {
	
	private EntityManagerFactory emf = null;
	
	public void init() {
		this.emf = Persistence.createEntityManagerFactory("pu_mybase");
	}
	
	public void close() {
		if(this.emf != null) {
			this.emf.close();
		}
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	private EntityManager newEntityManager() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		return em;
	}
	
	private void closeEntityManager(EntityManager em) {
		if(em != null) {
			if(em.isOpen()) {
				EntityTransaction t = em.getTransaction();
				if(t.isActive()) {
					try {
						t.rollback();
					} catch (PersistenceException e) {}
				}
				em.close();
			}
		}
	}
	
	public Person addPerson(Person p) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.persist(p);
			em.getTransaction().commit();
			System.out.println("addPerson with id=" + p.getId());
			return p;
		} finally {
			closeEntityManager(em);
		}
	}
	
	public Person findPerson(long id) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			Person p = em.find(Person.class, id);
			return p;
		} finally {
			closeEntityManager(em);
		}
	}
	
	public List<Person> findAllPersons() {
		EntityManager em = null;
		try {
			em = newEntityManager();
			String query = "select p from Person p";
			TypedQuery<Person> q = em.createQuery(query, Person.class);
			return q.getResultList();
		} finally {
			closeEntityManager(em);
		}
	}
	
	public List<Person> findAllNamedQuery() {
		EntityManager em = null;
		try {
			em = newEntityManager();
			Query query = em.createNamedQuery("Person.findAll");
			List<Person> liste = query.getResultList();
			for(Person p : liste) {
				System.out.println(p.toString());
			}
			return liste;
		} finally {
			closeEntityManager(em);
		}
	}
	
	public List<Person> findPersonsByFirstName(String firstName) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			Query query = em.createQuery("select p from Person p where p.firstName = ?0");
			query.setParameter(0, firstName);
			return query.getResultList();
		} finally {
			closeEntityManager(em);
		}
	}
	
	public void updatePerson(Person p) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			em.merge(p);
			em.getTransaction().commit();
			System.out.println("updatePerson with id=" + p.getId());
		} finally {
			closeEntityManager(em);
		}
	}
	
	public void removePerson(long id) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			Person p = em.find(Person.class, id);
			em.remove(p);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
		System.out.println("removePerson with id=" + id);
	}
	
	

}
