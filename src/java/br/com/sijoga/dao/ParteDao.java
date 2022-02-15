package br.com.sijoga.dao;

import br.com.sijoga.bean.Parte;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ParteDao {

    public void cadastrarParte(Parte parte) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(parte);
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo cliente [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo cliente [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Parte> listaClientes() throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Parte p ORDER BY p.nome");
            List<Parte> clientes = select.list();
            return clientes;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar lista de clientes [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar lista de clientes [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
