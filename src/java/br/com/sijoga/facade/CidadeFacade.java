package br.com.sijoga.facade;

import br.com.sijoga.bean.Cidade;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.dao.CidadeDao;
import br.com.sijoga.exception.DaoException;
import java.util.List;

public class CidadeFacade {
    private static final CidadeDao cidadeDao = new CidadeDao();
    
    public static List<Estado> listaEstado() throws DaoException {
        try {
            return cidadeDao.listaEstado();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao listar estados [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static List<Cidade> listaCidadePorEstado(Estado estado) throws DaoException {
        try {
            return cidadeDao.listaCidadePorEstado(estado);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao listar cidades por estado [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
