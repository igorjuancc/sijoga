package br.com.sijoga.dao;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class AdvogadoDao {

    public void cadastrarAdvogado(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(advogado);
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo advogado [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo advogado [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public Advogado buscarAdvogadoOab(int regOab) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Advogado a WHERE a.registroOab = :regOab");
            select.setParameter("regOab", regOab);
            Advogado advogado = (Advogado) select.uniqueResult();
            return advogado;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar advogado por OAB [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar advogado por OAB [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Advogado> listaAdvogados() throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Advogado a ORDER BY a.nome");
            List<Advogado> advogados = select.list();
            return advogados;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar lista de advogados [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar lista de advogados [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
