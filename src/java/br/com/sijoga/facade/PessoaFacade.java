package br.com.sijoga.facade;

import br.com.sijoga.bean.Pessoa;
import br.com.sijoga.dao.PessoaDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;

public class PessoaFacade {

    private static final PessoaDao pessoaDao = new PessoaDao();

    public static Pessoa buscarPessoaCpf(String cpf) {
        try {
            cpf = cpf.replace(".", "").replace("-", "");
            if (SijogaUtil.isCPF(cpf)) {
                return pessoaDao.buscarPessoaCpf(cpf);
            } else {
                return null;
            }
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar pessoa por CPF";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static Pessoa buscarPessoaEmail(String email) {
        try {
            if (Seguranca.isEmail(email)) {
                return pessoaDao.buscarPessoaEmail(email);
            } else {
                return null;
            }
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar pessoa por email";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }
}
