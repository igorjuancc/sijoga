package br.com.sijoga.facade;

import br.com.sijoga.bean.Parte;
import br.com.sijoga.dao.ParteDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.exception.EnderecoException;
import br.com.sijoga.exception.ParteException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import br.com.sijoga.validator.EnderecoValidator;
import br.com.sijoga.validator.PessoaValidator;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ParteFacade {

    private static final ParteDao parteDao = new ParteDao();

    public static void cadastrarParte(Parte parte) throws ParteException, EnderecoException {
        try {
            if (parte != null) {
                parte.setCpf((parte.getCpf() != null) ? parte.getCpf().replace(".", "").replace("-", "") : "");
                parte.setNome((parte.getNome() != null) ? parte.getNome().trim().toUpperCase() : "");
                parte.setFone((parte.getFone() != null) ? parte.getFone().replace("(", "").replace(")", "").replace("-", "") : null);
                parte.setEmail((parte.getEmail() != null) ? parte.getEmail().trim() : "");
                parte.setSenha((parte.getSenha() != null) ? parte.getSenha() : null);
                if (parte.getEndereco() != null) {
                    parte.getEndereco().setRua((parte.getEndereco().getRua() != null) ? parte.getEndereco().getRua().trim().toUpperCase() : null);
                    parte.getEndereco().setBairro((parte.getEndereco().getBairro() != null) ? parte.getEndereco().getBairro().trim().toUpperCase() : null);
                    parte.getEndereco().setCep((parte.getEndereco().getCep() != null) ? parte.getEndereco().getCep().replace(".", "").replace("-", "").trim() : null);
                    parte.getEndereco().setComplemento((parte.getEndereco().getComplemento() != null) ? parte.getEndereco().getComplemento().trim().toUpperCase() : "");
                }
            }

            PessoaValidator.validaParte(parte);
            EnderecoValidator.validaEndereco(parte.getEndereco());
            parte.setSenha(Seguranca.md5(parte.getSenha()));

            if (PessoaFacade.buscarPessoaCpf(parte.getCpf()) != null) {
                throw new ParteException("CPF já cadastrado");
            }
            if (PessoaFacade.buscarPessoaEmail(parte.getEmail()) != null) {
                throw new ParteException("Email já cadastrado");
            }
            parteDao.cadastrarParte(parte);        
        } catch (DaoException | NoSuchAlgorithmException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao cadastrar os dados de cliente";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public static List<Parte> listaClientes() {
        try {
            return parteDao.listaClientes();
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar lista de clientes";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

}
