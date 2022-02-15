package br.com.sijoga.facade;

import br.com.sijoga.bean.Pessoa;
import br.com.sijoga.dao.PessoaDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;

public class PessoaFacade {
    private static final PessoaDao pessoaDao = new PessoaDao();
    
    public static Pessoa buscarPessoaCpf(String cpf) throws DaoException {
        try {
            cpf = cpf.replace(".", "").replace("-", "");
            if (SijogaUtil.isCPF(cpf)) {
                return pessoaDao.buscarPessoaCpf(cpf);
            } else {
                return null;
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao buscar pessoa por cpf [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static Pessoa buscarPessoaEmail(String email) throws DaoException {
        try {
            if (Seguranca.isEmail(email)) {
                return pessoaDao.buscarPessoaEmail(email);                                
            } else {
                return null;
            }            
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao buscar pessoa por email [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }    
}
