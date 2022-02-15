package br.com.sijoga.facade;

import br.com.sijoga.bean.Parte;
import br.com.sijoga.dao.ParteDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ParteFacade {

    private static final ParteDao parteDao = new ParteDao();

    public static List<String> cadastrarParte(Parte parte) throws NoSuchAlgorithmException, DaoException {
        try {
            List<String> mensagens = new ArrayList();
            parte.setCpf(parte.getCpf().replace(".", "").replace("-", ""));
            parte.setNome(parte.getNome().trim().toUpperCase());
            if (parte.getFone() != null) {
                parte.setFone(parte.getFone().replace("(", "").replace(")", "").replace("-", ""));
            }
            parte.setEmail(parte.getEmail().trim());
            parte.setSenha(Seguranca.md5(parte.getSenha()));

            parte.getEndereco().setRua(parte.getEndereco().getRua().trim().toUpperCase());
            parte.getEndereco().setBairro(parte.getEndereco().getBairro().trim().toUpperCase());
            parte.getEndereco().setCep(parte.getEndereco().getCep().replace(".", "").replace("-", "").trim());
            if (parte.getEndereco().getComplemento() != null) {
                parte.getEndereco().setComplemento(parte.getEndereco().getComplemento().trim().toUpperCase());
            }

            if (!SijogaUtil.isCPF(parte.getCpf())) {
                mensagens.add("CPF Inválido");
            } else if (PessoaFacade.buscarPessoaCpf(parte.getCpf()) != null) {
                mensagens.add("CPF já cadastrado");
            }
            if (!Seguranca.isEmail(parte.getEmail())) {
                mensagens.add("Email Inválido");
            } else if (PessoaFacade.buscarPessoaEmail(parte.getEmail()) != null) {
                mensagens.add("Email já cadastrado");
            }
            if (SijogaUtil.idade(parte.getDataNascimento()) < 18) {
                mensagens.add("Cliente não pode ser menor de idade");
            }

            if (mensagens.isEmpty()) {
                parteDao.cadastrarParte(parte);
            }

            return mensagens;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("****Problemas com MD5 ao cadastrar novo cliente [Facade]****" + e);
            e.printStackTrace();
            throw e;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar novo cliente [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static List<Parte> listaClientes() throws DaoException {
        try {
            return parteDao.listaClientes();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar lista de clientes [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

}
