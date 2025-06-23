package DAO;

import Model.Ubicacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UbicacionDAO {

    private SessionFactory sessionFactory;

    public UbicacionDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void crearUbicacion(Ubicacion ubicacion) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(ubicacion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Ubicacion obtenerUbicacionPorId(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Ubicacion.class, id);
        }
    }

    public void actualizarUbicacion(Ubicacion ubicacion) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(ubicacion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void eliminarUbicacion(long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Ubicacion ubicacion = session.get(Ubicacion.class, id);
            if (ubicacion != null) {
                session.delete(ubicacion);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Ubicacion> listarUbicaciones() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Ubicacion", Ubicacion.class).list();
        }
    }

    public void cerrar() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

