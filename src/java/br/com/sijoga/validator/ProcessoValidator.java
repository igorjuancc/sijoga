package br.com.sijoga.validator;

import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.dto.IntimacaoDto;
import br.com.sijoga.exception.FaseException;
import br.com.sijoga.exception.DocumentoException;
import br.com.sijoga.exception.IntimacaoException;
import br.com.sijoga.exception.ProcessoException;
import org.primefaces.model.UploadedFile;

public class ProcessoValidator {

    public static void validaProcesso(Processo processo) throws ProcessoException {
        if (processo == null) {
            throw new ProcessoException("Processo inválido");
        } else {
            String mensagem = "";

            if ((processo.getAdvogadoPromovente() == null) || (processo.getAdvogadoPromovente().getId() == 0)) {
                mensagem += "Advogado promovente inválido<br/>";
            }
            if ((processo.getAdvogadoPromovido() == null) || (processo.getAdvogadoPromovido().getId() == 0)) {
                mensagem += "Advogado promovido inválido<br/>";
            }
            if ((processo.getAdvogadoPromovente() != null) && (processo.getAdvogadoPromovido() != null)) {
                if (processo.getAdvogadoPromovente().getId() == processo.getAdvogadoPromovido().getId()) {
                    mensagem += "Um advogado não pode representar as duas partes<br/>";
                }
            }
            if ((processo.getPromovente() == null) || (processo.getPromovente().getId() == 0)) {
                mensagem += "Parte promovente inválida<br/>";
            }
            if ((processo.getPromovido() == null) || (processo.getPromovido().getId() == 0)) {
                mensagem += "Parte promovida inválida<br/>";
            }
            if ((processo.getPromovente() != null) && (processo.getPromovido() != null)) {
                if (processo.getPromovente().getId() == processo.getPromovido().getId()) {
                    mensagem += "As partes não podem ser iguais<br/>";
                }
            }
            if ((processo.getFases() == null) || (processo.getFases().isEmpty())) {
                mensagem += "Necessário uma fase para iniciar o processo<br/>";
            }
            if (!mensagem.equals("")) {
                throw new ProcessoException(mensagem);
            }
        }
    }

    public static void validaFase(FaseProcesso fase) throws FaseException {
        if (fase == null) {
            throw new FaseException("Fase do processo inválida");
        } else {
            String mensagem = "";
            if ((fase.getTitulo() == null) || (fase.getTitulo().isEmpty()) || (fase.getTitulo().equals(""))) {
                mensagem += "Titulo de fase do processo inválido<br/>";
            }
            if ((fase.getDescricao() == null) || (fase.getDescricao().isEmpty()) || (fase.getDescricao().equals(""))) {
                mensagem += "Descrição de fase do processo inválida<br/>";
            }
            if ((fase.getAdvogado() == null) || fase.getAdvogado().getId() == 0) {
                mensagem += "Necessário indicar um advogado para criação da primeira fase do processo<br/>";
            }
            if ((fase.getTipo() != 1) && (fase.getTipo() != 2)) {
                mensagem += "Fase do processo inválida<br/>";
            }
            if ((fase.getAdvogado() == null) || fase.getAdvogado().getId() == 0) {
                mensagem += "Necessário um advogado para criação de fase do processo<br/>";
            }
            if (!mensagem.equals("")) {
                throw new FaseException(mensagem);
            }
        }
    }

    public static void validaDocumento(UploadedFile arquivo) throws DocumentoException {
        if (arquivo == null) {
            throw new DocumentoException("Fase do processo inválida");
        } else {
            String mensagem = "";
            if (arquivo.getSize() > 2097152) {
                mensagem += "Tamanho do arquivo maior que 2MB<br/>";
            }
            if (!"application/pdf".equals(arquivo.getContentType())) {
                mensagem += "Formato de arquivo inválido<br/>";
            }
            if (!mensagem.equals("")) {
                throw new DocumentoException(mensagem);
            }
        }
    }

    public static void validaIntimacao(IntimacaoDto intimacao) throws IntimacaoException {
        if (intimacao == null) {
            throw new IntimacaoException("Intimação inválida");
        } else {
            String mensagem = "";
            if (intimacao.getProcesso() == 0) {
                mensagem += "Número do processo não é válido<br/>";
            }
            if ((intimacao.getCpf() == null) || (intimacao.getCpf().equals(""))) {
                mensagem += "CPF do intimado não é válido<br/>";
            }
            if ((intimacao.getNome() == null) || (intimacao.getNome().equals(""))) {
                mensagem += "Nome do intimado não é válido<br/>";
            }
            if (!mensagem.equals("")) {
                throw new IntimacaoException(mensagem);
            }
        }
    }
}
