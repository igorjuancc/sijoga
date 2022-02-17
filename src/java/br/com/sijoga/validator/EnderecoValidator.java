package br.com.sijoga.validator;

import br.com.sijoga.bean.Endereco;
import br.com.sijoga.exception.EnderecoException;

public class EnderecoValidator {

    public static void validaEndereco(Endereco endereco) throws EnderecoException {
        if (endereco == null) {
            throw new EnderecoException("Endereço inválido");
        } else {
            String mensagem = "";
            if (endereco.getNumero() <= 0) {
                mensagem += "Número do endereço inválido<br/>";
            }
            if ((endereco.getCep() == null)
                    || (endereco.getCep().isEmpty())
                    || (endereco.getCep().trim().equals(""))
                    || (endereco.getCep().length() != 8)) {
                mensagem += "CEP inválido<br/>";
            }
            if ((endereco.getRua() == null)
                    || (endereco.getRua().isEmpty())
                    || (endereco.getRua().trim().equals(""))) {
                mensagem += "Nome da rua inválido<br/>";
            }
            if ((endereco.getBairro() == null)
                    || (endereco.getBairro().isEmpty())
                    || (endereco.getBairro().trim().equals(""))) {
                mensagem += "Bairro inválido<br/>";
            }
            if ((endereco.getCidade() == null) || (endereco.getCidade().getId() <= 0)) {
                mensagem += "Cidade inválida<br/>";
            }
            if (!mensagem.equals("")) {
                throw new EnderecoException(mensagem);
            }
        }
    }

}
