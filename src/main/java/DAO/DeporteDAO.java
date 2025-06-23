package DAO;

import Model.Deporte;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class DeporteDAO {

    public void guardar(Deporte deporte) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(deporte);
            tx.commit();
        }
    }

    public Deporte buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Deporte.class, id);
        }
    }

    public List<Deporte> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Deporte", Deporte.class).list();
        }
    }

    public void actualizar(Deporte deporte) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(deporte);
            tx.commit();
        }
    }

    public void eliminar(Deporte deporte) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(deporte);
            tx.commit();
        }
    }
}
