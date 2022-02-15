package br.com.sijoga.dao;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoginDao {

    public Advogado loginAdvogado(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Advogado a WHERE a.email = :email AND a.senha = :senha");
            select.setParameter("email", advogado.getEmail());
            select.setParameter("senha", advogado.getSenha());
            advogado = (Advogado) select.uniqueResult();
            return advogado;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar login de usuário advogado [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar login de usuário advogado [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public Juiz loginJuiz(Juiz juiz) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Juiz j WHERE j.email = :email AND j.senha = :senha");
            select.setParameter("email", juiz.getEmail());
            select.setParameter("senha", juiz.getSenha());
            juiz = (Juiz) select.uniqueResult();
            return juiz;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar login de usuário juiz [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar login de usuário juiz [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public Parte loginParte(Parte parte) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Parte p WHERE p.email = :email AND p.senha = :senha");
            select.setParameter("email", parte.getEmail());
            select.setParameter("senha", parte.getSenha());
            parte = (Parte) select.uniqueResult();
            return parte;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar login de usuário parte [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar login de usuário parte [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
