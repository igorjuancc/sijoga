package br.com.sijoga.facade;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.dao.AdvogadoDao;
import br.com.sijoga.exception.AdvogadoException;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.exception.EnderecoException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import br.com.sijoga.validator.EnderecoValidator;
import br.com.sijoga.validator.PessoaValidator;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AdvogadoFacade {

    private static final AdvogadoDao advogadoDao = new AdvogadoDao();

    public static void cadastrarAdvogado(Advogado advogado) throws AdvogadoException, EnderecoException {
        try {
            if (advogado != null) {
                advogado.setCpf((advogado.getCpf() != null) ? advogado.getCpf().replace(".", "").replace("-", "") : "");
                advogado.setNome((advogado.getNome() != null) ? advogado.getNome().trim().toUpperCase() : "");
                advogado.setFone((advogado.getFone() != null) ? advogado.getFone().replace("(", "").replace(")", "").replace("-", "") : null);
                advogado.setEmail((advogado.getEmail() != null) ? advogado.getEmail().trim() : "");
                advogado.setSenha((advogado.getSenha() != null) ? advogado.getSenha() : null);
                if (advogado.getEndereco() != null) {
                    advogado.getEndereco().setRua((advogado.getEndereco().getRua() != null) ? advogado.getEndereco().getRua().trim().toUpperCase() : null);
                    advogado.getEndereco().setBairro((advogado.getEndereco().getBairro() != null) ? advogado.getEndereco().getBairro().trim().toUpperCase() : null);
                    advogado.getEndereco().setCep((advogado.getEndereco().getCep() != null) ? advogado.getEndereco().getCep().replace(".", "").replace("-", "").trim() : null);
                    advogado.getEndereco().setComplemento((advogado.getEndereco().getComplemento() != null) ? advogado.getEndereco().getComplemento().trim().toUpperCase() : "");
                }
            }

            PessoaValidator.validaAdvogado(advogado);
            EnderecoValidator.validaEndereco(advogado.getEndereco());
            advogado.setSenha(Seguranca.md5(advogado.getSenha()));

            if (PessoaFacade.buscarPessoaCpf(advogado.getCpf()) != null) {
                throw new AdvogadoException("CPF já cadastrado");
            }
            if (PessoaFacade.buscarPessoaEmail(advogado.getEmail()) != null) {
                throw new AdvogadoException("Email já cadastrado");
            }
            if ((advogadoDao.buscarAdvogadoOab(advogado.getRegistroOab()) != null) || (JuizFacade.buscarJuizOab(advogado.getRegistroOab()) != null)) {
                throw new AdvogadoException("Número de registro da OAB já cadastrado");
            }
            advogadoDao.cadastrarAdvogado(advogado);
        } catch (DaoException | NoSuchAlgorithmException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao cadastrar os dados de advogado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public static Advogado buscarAdvogadoOab(int regOab) {
        try {
            return advogadoDao.buscarAdvogadoOab(regOab);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar advogado por OAB";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Advogado> listaAdvogados() throws DaoException {
        try {
            return advogadoDao.listaAdvogados();
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar lista de advogados";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }
}
