package br.com.sijoga.dao;

import br.com.sijoga.bean.Cidade;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class CidadeDao {

    public List<Estado> listaEstado() throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Estado e ORDER BY e.nome");
            List<Estado> estados = select.list();
            return estados;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar estados [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar estados [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Cidade> listaCidadePorEstado(Estado estado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("FROM Cidade c WHERE c.estado.id = :estadoId ORDER BY c.nome");
            select.setParameter("estadoId", estado.getId());
            List<Cidade> cidades = select.list();
            return cidades;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cidades listar cidades por estado [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cidades listar cidades por estado [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
