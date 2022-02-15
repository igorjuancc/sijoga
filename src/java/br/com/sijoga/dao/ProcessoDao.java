package br.com.sijoga.dao;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ProcessoDao {

    public void cadastrarProcesso(Processo processo) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(processo);
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo processo [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo processo [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public void atualizarProcesso(Processo processo) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(processo);
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao atualizar processo [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao atualizar processo [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public Processo buscaProcessoId(int id) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p WHERE p.id = :idProcesso");
            select.setParameter("idProcesso", id);
            Processo processo = (Processo) select.uniqueResult();
            return processo;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar processo por id [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar processo por id [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaTodosProcessosJuiz(Juiz juiz) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.juiz j WHERE j.id = :idJuiz ORDER BY p.dataInicio ASC");
            select.setParameter("idJuiz", juiz.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de juiz [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de juiz [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaTodosProcessosParte(Parte parte) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.promovente p1 JOIN p.promovido p2 WHERE (p1.id = :idParte OR p2.id = :idParte) AND p.vencedor = NULL ORDER BY p.dataInicio ASC");
            select.setParameter("idParte", parte.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de parte [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de parte [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaTodosProcessosAdvogado(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.advogadoPromovente p1 JOIN p.advogadoPromovido p2 WHERE p1.id = :idAdvogado OR p2.id = :idAdvogado ORDER BY p.dataInicio ASC");
            select.setParameter("idAdvogado", advogado.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de advogado [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de advogado [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaProcessosAdvogadoAbertos(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.advogadoPromovente p1 JOIN p.advogadoPromovido p2 WHERE (p1.id = :idAdvogado OR p2.id = :idAdvogado) AND (p.vencedor = NULL) ORDER BY p.dataInicio ASC");
            select.setParameter("idAdvogado", advogado.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de advogado em aberto [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de advogado em aberto [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaProcessosAdvogadoFechados(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.advogadoPromovente p1 JOIN p.advogadoPromovido p2 WHERE (p1.id = :idAdvogado OR p2.id = :idAdvogado) AND (p.vencedor != NULL) ORDER BY p.dataInicio ASC");
            select.setParameter("idAdvogado", advogado.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de advogado fechados [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de advogado fechados [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaProcessosAdvogadoPromovido(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.advogadoPromovido p1 WHERE p1.id = :idAdvogado ORDER BY p.dataInicio ASC");
            select.setParameter("idAdvogado", advogado.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de advogado promovido [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de advogado promovido [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaProcessosAdvogadoPromovente(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.advogadoPromovente p1 WHERE p1.id = :idAdvogado ORDER BY p.dataInicio ASC");
            select.setParameter("idAdvogado", advogado.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de advogado promovente [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de advogado promovente [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaProcessosAdvogadoPromovidoGanho(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.advogadoPromovido p1 WHERE p1.id = :idAdvogado AND p.vencedor = p.promovido ORDER BY p.dataInicio ASC");
            select.setParameter("idAdvogado", advogado.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de advogado promovido ganho [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de advogado promovido ganho [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }

    public List<Processo> listaProcessosAdvogadoPromoventeGanho(Advogado advogado) throws DaoException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query select = session.createQuery("SELECT p FROM Processo p JOIN p.advogadoPromovente p1 WHERE p1.id = :idAdvogado AND p.vencedor = p.promovente ORDER BY p.dataInicio ASC");
            select.setParameter("idAdvogado", advogado.getId());
            List<Processo> processos = select.list();
            return processos;
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao listar processos de advogado promovente ganho [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao listar processos de advogado promovente ganho [DAO]****", e);
        } finally {
            if (session != null) {
                session.getTransaction().commit();
                session.close();
            }
        }
    }
}
