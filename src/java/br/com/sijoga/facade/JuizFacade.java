package br.com.sijoga.facade;

import br.com.sijoga.bean.Juiz;
import br.com.sijoga.dao.JuizDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class JuizFacade {

    private static final JuizDao juizDao = new JuizDao();

    public static List<String> cadastrarJuiz(Juiz juiz) throws NoSuchAlgorithmException, DaoException {
        try {
            List<String> mensagens = new ArrayList();
            juiz.setCpf(juiz.getCpf().replace(".", "").replace("-", ""));
            juiz.setNome(juiz.getNome().trim().toUpperCase());
            if (juiz.getFone() != null) {
                juiz.setFone(juiz.getFone().replace("(", "").replace(")", "").replace("-", ""));
            }
            juiz.setEmail(juiz.getEmail().trim());
            juiz.setSenha(Seguranca.md5(juiz.getSenha()));

            juiz.getEndereco().setRua(juiz.getEndereco().getRua().trim().toUpperCase());
            juiz.getEndereco().setBairro(juiz.getEndereco().getBairro().trim().toUpperCase());
            juiz.getEndereco().setCep(juiz.getEndereco().getCep().replace(".", "").replace("-", "").trim());
            if (juiz.getEndereco().getComplemento() != null) {
                juiz.getEndereco().setComplemento(juiz.getEndereco().getComplemento().trim().toUpperCase());
            }

            if (!SijogaUtil.isCPF(juiz.getCpf())) {
                mensagens.add("CPF Inválido");
            } else if (PessoaFacade.buscarPessoaCpf(juiz.getCpf()) != null) {
                mensagens.add("CPF já cadastrado");
            }
            if (!Seguranca.isEmail(juiz.getEmail())) {
                mensagens.add("Email Inválido");
            } else if (PessoaFacade.buscarPessoaEmail(juiz.getEmail()) != null) {
                mensagens.add("Email já cadastrado");
            }
            if (SijogaUtil.idade(juiz.getDataNascimento()) < 18) {
                mensagens.add("Juiz não pode ser menor de idade");
            }
            if (juiz.getRegistroOab() == 0) {
                mensagens.add("Numero de registro na OAB inválido");
            } else if ((juizDao.buscarJuizOab(juiz.getRegistroOab()) != null) || (AdvogadoFacade.buscarAdvogadoOab(juiz.getRegistroOab()) != null)) {
                mensagens.add("Número de registro da OAB já cadastrado");
            }

            if (mensagens.isEmpty()) {
                juizDao.cadastrarJuiz(juiz);
            }

            return mensagens;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("****Problemas com MD5 ao cadastrar novo juiz [Facade]****" + e);
            e.printStackTrace();
            throw e;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar novo juiz [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Juiz buscarJuizOab(int regOab) throws DaoException {
        try {
            return juizDao.buscarJuizOab(regOab);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar juiz por OAB [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Juiz buscarJuizProcessos() throws DaoException {
        try {
            List<Juiz> juizes = juizDao.buscarJuizProcessos();
            Juiz rtn = new Juiz();
            
            if (juizes != null) {
                for (Juiz j : juizes) {
                    j.setProcessos(ProcessoFacade.listaTodosProcessosJuiz(j));
                }                
            }

            if (juizes != null) {
                if (rtn.getId() == 0) {
                    rtn = juizes.get(0);
                }                
                for (Juiz j : juizes) {
                    if (j.getProcessos().size() < rtn.getProcessos().size()){
                        rtn = j;
                    }
                }
            }

            return rtn;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar juiz por processo [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }
}
