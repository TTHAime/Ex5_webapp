package ex5_backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ex5_backend.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author user
 */
public class StudentTable {

    public static void insertStudent(Student sd) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(sd);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static void updateStudent(Student sd) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        EntityManager em = emf.createEntityManager();
        Student fromDb = em.find(Student.class, sd.getId());
        fromDb.setName(sd.getName());
        fromDb.setGpa(sd.getGpa());
        em.getTransaction().begin();
        try {
            em.persist(fromDb);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static Student findStudentById(Integer id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        EntityManager em = emf.createEntityManager();
        Student sd = em.find(Student.class, id);
        em.close();
        return sd;
    }

    public static List<Student> findAllStudent() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        EntityManager em = emf.createEntityManager();
        List<Student> StudentList = (List<Student>) em.createNamedQuery("Student.findAll").getResultList();
        em.close();
        return StudentList;
    }

    public static List<Student> findStudentByName(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Student.findByName");
        query.setParameter("name", name);
        List<Student> StudentList = (List<Student>) query.getResultList();
        em.close();
        return StudentList;
    }

    public static void removeStudent(Student sd) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        EntityManager em = emf.createEntityManager();
        Student fromDb = em.find(Student.class, sd.getId());
        em.getTransaction().begin();
        try {
            em.remove(fromDb);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

    public static boolean existsId(Integer id) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        if (id == null) {
            return false;
        }
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Student.class, id) != null;
        } finally {
            em.close();
        }
    }

    public static boolean existsName(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApp_Ex5PU");
        if (name == null) {
            return false;
        }
        EntityManager em = emf.createEntityManager();
        try {
            Long c = em.createQuery(
                    "SELECT COUNT(s) FROM Student s WHERE LOWER(s.name) = :n", Long.class)
                    .setParameter("n", name.toLowerCase())
                    .getSingleResult();
            return c != 0;
        } finally {
            em.close();
        }
    }

}
