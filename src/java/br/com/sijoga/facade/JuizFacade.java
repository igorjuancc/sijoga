package br.com.sijoga.facade;

import br.com.sijoga.bean.Juiz;
import br.com.sijoga.dao.JuizDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.exception.EnderecoException;
import br.com.sijoga.exception.JuizException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import br.com.sijoga.validator.EnderecoValidator;
import br.com.sijoga.validator.PessoaValidator;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class JuizFacade {

    private static final JuizDao juizDao = new JuizDao();

    public static void cadastrarJuiz(Juiz juiz) throws JuizException, EnderecoException {
        try {
            if (juiz != null) {
                juiz.setCpf((juiz.getCpf() != null) ? juiz.getCpf().replace(".", "").replace("-", "") : "");
                juiz.setNome((juiz.getNome() != null) ? juiz.getNome().trim().toUpperCase() : "");
                juiz.setFone((juiz.getFone() != null) ? juiz.getFone().replace("(", "").replace(")", "").replace("-", "") : null);
                juiz.setEmail((juiz.getEmail() != null) ? juiz.getEmail().trim() : "");
                juiz.setSenha((juiz.getSenha() != null) ? juiz.getSenha() : null);
                if (juiz.getEndereco() != null) {
                    juiz.getEndereco().setRua((juiz.getEndereco().getRua() != null) ? juiz.getEndereco().getRua().trim().toUpperCase() : null);
                    juiz.getEndereco().setBairro((juiz.getEndereco().getBairro() != null) ? juiz.getEndereco().getBairro().trim().toUpperCase() : null);
                    juiz.getEndereco().setCep((juiz.getEndereco().getCep() != null) ? juiz.getEndereco().getCep().replace(".", "").replace("-", "").trim() : null);
                    juiz.getEndereco().setComplemento((juiz.getEndereco().getComplemento() != null) ? juiz.getEndereco().getComplemento().trim().toUpperCase() : "");
                }
            }

            PessoaValidator.validaJuiz(juiz);
            EnderecoValidator.validaEndereco(juiz.getEndereco());
            juiz.setSenha(Seguranca.md5(juiz.getSenha()));

            if (PessoaFacade.buscarPessoaCpf(juiz.getCpf()) != null) {
                throw new JuizException("CPF já cadastrado");
            }
            if (PessoaFacade.buscarPessoaEmail(juiz.getEmail()) != null) {
                throw new JuizException("Email já cadastrado");
            }
            if ((juizDao.buscarJuizOab(juiz.getRegistroOab()) != null) || (AdvogadoFacade.buscarAdvogadoOab(juiz.getRegistroOab()) != null)) {
                throw new JuizException("Número de registro da OAB já cadastrado");
            }

            juizDao.cadastrarJuiz(juiz);
        } catch (DaoException | NoSuchAlgorithmException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao cadastrar os dados de juiz";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public static Juiz buscarJuizOab(int regOab) {
        try {
            return juizDao.buscarJuizOab(regOab);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar juiz por OAB";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static Juiz buscarJuizProcessos() throws DaoException {
        try {
            List<Juiz> juizes = juizDao.buscarJuizProcessos();
            Juiz rtn = new Juiz();

            if (juizes != null) {
                juizes.forEach((j) -> {
                    j.setProcessos(ProcessoFacade.listaTodosProcessosJuiz(j));
                });
            }

            if (juizes != null) {
                if (rtn.getId() == 0) {
                    rtn = juizes.get(0);
                }
                for (Juiz j : juizes) {
                    if (j.getProcessos().size() < rtn.getProcessos().size()) {
                        rtn = j;
                    }
                }
            }

            return rtn;
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar juiz por processo";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }
}
