package br.com.sijoga.dao;

import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class FaseProcessoDao {

    public void cadastrarFaseProcesso(FaseProcesso faseProcesso) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(faseProcesso);
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar nova fase do processo [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar nova fase do processo [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public FaseProcesso buscaFaseProcessoId(int id) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT f FROM FaseProcesso f WHERE f.id = :idFaseProcesso");
            select.setParameter("idFaseProcesso", id);
            FaseProcesso fase = (FaseProcesso) select.uniqueResult();
            return fase;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar fase de processo por id [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar fase de processo por id [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public void atualizarFaseProcesso(FaseProcesso fase) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(fase);
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao atualizar fase de processo [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao atualizar fase de processo [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
