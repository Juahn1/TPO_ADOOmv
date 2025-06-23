package DAO;



import Model.UsuarioDeporte;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class UsuarioDeporteDAO {

    public void guardar(UsuarioDeporte usuarioDeporte) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(usuarioDeporte);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public UsuarioDeporte buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(UsuarioDeporte.class, id);
        }
    }

    public void eliminar(UsuarioDeporte usuarioDeporte) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(usuarioDeporte);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<UsuarioDeporte> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UsuarioDeporte> query = session.createQuery("FROM UsuarioDeporte", UsuarioDeporte.class);
            return query.getResultList();
        }
    }
}
