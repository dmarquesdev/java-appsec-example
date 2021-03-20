package com.appsec.java.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.appsec.java.exception.DAOException;
import com.appsec.java.model.Person;
import java.io.Serializable;

@Named
@SessionScoped
public class PersonDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3840717898121885755L;
	
	private static SessionFactory sessionFactory;
	
	private static SessionFactory buildSessionFactory() {
		if(sessionFactory == null) {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		
		return sessionFactory;
	}
	
	public void save(Person p) {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			if(p.getId() == null) {
				session.save(p);
			} else {
				session.update(p);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> list() {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		List<Person> result = null;
		
		try {
			tx = session.beginTransaction();
			result = session.createQuery("FROM Person").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public Person findByEmail(String email) {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		Person result = null;
		
		try {
			tx = session.beginTransaction();
			result = (Person) session.createQuery("FROM Person WHERE email = :email")
					.setParameter("email", email)
					.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public Person findByUsername(String username) {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		Person result = null;
		
		try {
			tx = session.beginTransaction();
			result = (Person) session.createQuery("FROM Person WHERE username = :username")
					.setParameter("username", username)
					.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public Person findById(Long id) {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		Person result = null;
		
		try {
			tx = session.beginTransaction();
			result = session.get(Person.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> listByName(String name, String orderBy) throws DAOException {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		List<Person> result = null;
		
		try {
			tx = session.beginTransaction();
			result = session.createQuery("FROM Person WHERE name LIKE :name ORDER BY " + orderBy)
					.setParameter("name", "%" + name + "%")
					.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException("Database error", e);
		} finally {
			session.close();
		}
		
		return result;
	}
	
	public void delete(Long id) {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			Person p = session.get(Person.class, id);
			session.delete(p);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Person login(String username, String password) throws DAOException {
		Session session = buildSessionFactory().openSession();
		Transaction tx = null;
		Person result = null;
		String hash = null;
		
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			hash = DatatypeConverter.printHexBinary(md5.digest()).toLowerCase();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			tx = session.beginTransaction();
			result = (Person) session.createQuery("FROM Person WHERE username = :username and password = :password")
					.setParameter("username", username)
					.setParameter("password", hash)
					.uniqueResult();
			if(result == null) {
				if (findByUsername(username) != null) {
					throw new DAOException("Username/Password is invalid");
				} else {
					throw new DAOException("User not found");
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException("Database error", e);
		} finally {
			session.close();
		}
		
		return result;
	}
}
