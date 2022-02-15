package br.com.sijoga.facade;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.dao.AdvogadoDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AdvogadoFacade {

    private static final AdvogadoDao advogadoDao = new AdvogadoDao();

    public static List<String> cadastrarAdvogado(Advogado advogado) throws NoSuchAlgorithmException, DaoException {
        try {
            List<String> mensagens = new ArrayList();
            advogado.setCpf(advogado.getCpf().replace(".", "").replace("-", ""));
            advogado.setNome(advogado.getNome().trim().toUpperCase());
            if (advogado.getFone() != null) {
                advogado.setFone(advogado.getFone().replace("(", "").replace(")", "").replace("-", ""));
            }
            advogado.setEmail(advogado.getEmail().trim());
            advogado.setSenha(Seguranca.md5(advogado.getSenha()));

            advogado.getEndereco().setRua(advogado.getEndereco().getRua().trim().toUpperCase());
            advogado.getEndereco().setBairro(advogado.getEndereco().getBairro().trim().toUpperCase());
            advogado.getEndereco().setCep(advogado.getEndereco().getCep().replace(".", "").replace("-", "").trim());
            if (advogado.getEndereco().getComplemento() != null) {
                advogado.getEndereco().setComplemento(advogado.getEndereco().getComplemento().trim().toUpperCase());
            }

            if (!SijogaUtil.isCPF(advogado.getCpf())) {
                mensagens.add("CPF Inválido");
            } else if (PessoaFacade.buscarPessoaCpf(advogado.getCpf()) != null) {
                mensagens.add("CPF já cadastrado");
            }
            if (!Seguranca.isEmail(advogado.getEmail())) {
                mensagens.add("Email Inválido");
            } else if (PessoaFacade.buscarPessoaEmail(advogado.getEmail()) != null) {
                mensagens.add("Email já cadastrado");
            }
            if (SijogaUtil.idade(advogado.getDataNascimento()) < 18) {
                mensagens.add("Advogado não pode ser menor de idade");
            }
            if (advogado.getRegistroOab() == 0) {
                mensagens.add("Numero de registro na OAB inválido");
            } else if ((advogadoDao.buscarAdvogadoOab(advogado.getRegistroOab()) != null) || (JuizFacade.buscarJuizOab(advogado.getRegistroOab()) != null)) {
                mensagens.add("Número de registro da OAB já cadastrado");
            }

            if (mensagens.isEmpty()) {
                advogadoDao.cadastrarAdvogado(advogado);
            }

            return mensagens;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("****Problemas com MD5 ao cadastrar novo advogado [Facade]****" + e);
            e.printStackTrace();
            throw e;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar novo advogado [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static Advogado buscarAdvogadoOab(int regOab) throws DaoException {
        try {
            return advogadoDao.buscarAdvogadoOab(regOab);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar advogado por OAB [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static List<Advogado> listaAdvogados() throws DaoException {
        try {
            return advogadoDao.listaAdvogados();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar lista de advogados [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
