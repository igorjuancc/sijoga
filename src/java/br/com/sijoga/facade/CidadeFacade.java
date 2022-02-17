package br.com.sijoga.facade;

import br.com.sijoga.bean.Cidade;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.dao.CidadeDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.SijogaUtil;
import java.util.List;

public class CidadeFacade {

    private static final CidadeDao cidadeDao = new CidadeDao();

    public static List<Estado> listaEstado() {
        try {
            return cidadeDao.listaEstado();
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar estados";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Cidade> listaCidadePorEstado(Estado estado) {
        try {
            return cidadeDao.listaCidadePorEstado(estado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar cidades por estado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }
}
