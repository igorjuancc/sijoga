package br.com.sijoga.facade;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.dao.LoginDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;

public class LoginFacade {

    private static final LoginDao loginDao = new LoginDao();

    public static Advogado loginAdvogado(Advogado advogado) {
        try {
            if (!Seguranca.isEmail(advogado.getEmail()) || advogado.getSenha() == null) {
                return null;
            } else {
                return loginDao.loginAdvogado(advogado);
            }
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Problemas ao efetuar login de advogado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static Juiz loginJuiz(Juiz juiz) {
        try {
            if (!Seguranca.isEmail(juiz.getEmail()) || juiz.getSenha() == null) {
                return null;
            } else {
                return loginDao.loginJuiz(juiz);
            }
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Problemas ao efetuar login de juiz";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static Parte loginParte(Parte parte) {
        try {
            if (!Seguranca.isEmail(parte.getEmail()) || parte.getSenha() == null) {
                return null;
            } else {
                return loginDao.loginParte(parte);
            }
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Problemas ao efetuar login de parte";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }
}
