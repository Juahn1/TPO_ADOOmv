package DAO;


import Model.Partido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PartidoDAO {

    private SessionFactory sessionFactory;

    public PartidoDAO() {
        // Crea SessionFactory usando hibernate.cfg.xml
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void crearPartido(Partido partido) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(partido);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Partido obtenerPartidoPorId(float id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Partido.class, id);
        }
    }

    public void actualizarPartido(Partido partido) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(partido);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void eliminarPartido(float id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Partido partido = session.get(Partido.class, id);
            if (partido != null) {
                session.delete(partido);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Partido> listarPartidos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Partido", Partido.class).list();
        }
    }

    public void cerrar() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
