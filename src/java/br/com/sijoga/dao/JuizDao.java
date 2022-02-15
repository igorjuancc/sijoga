package br.com.sijoga.dao;

import br.com.sijoga.bean.Juiz;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class JuizDao {

    public void cadastrarJuiz(Juiz juiz) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(juiz);
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo juiz [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo juiz [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public Juiz buscarJuizOab(int regOab) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();
            Query select = session.createQuery("FROM Juiz j WHERE j.registroOab = :regOab");
            select.setParameter("regOab", regOab);
            Juiz juiz = (Juiz) select.uniqueResult();
            return juiz;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar juiz por OAB [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar juiz por OAB [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Juiz> buscarJuizProcessos() throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT DISTINCT j FROM Juiz j");
            List<Juiz> juizes = select.list();
            return juizes;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar juiz e processos [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar juiz e prcessos [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
