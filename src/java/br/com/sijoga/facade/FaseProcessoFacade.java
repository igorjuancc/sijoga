package br.com.sijoga.facade;

import br.com.sijoga.bean.Documento;
import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.dao.FaseProcessoDao;
import br.com.sijoga.exception.ArquivoException;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.exception.DocumentoException;
import br.com.sijoga.exception.FaseException;
import br.com.sijoga.util.SijogaUtil;
import br.com.sijoga.validator.ProcessoValidator;
import java.util.Date;
import org.primefaces.model.UploadedFile;

public class FaseProcessoFacade {

    private static final FaseProcessoDao faseProcessoDao = new FaseProcessoDao();

    public static void cadastrarFaseProcesso(FaseProcesso faseProcesso, UploadedFile arquivo) throws ArquivoException, DocumentoException, FaseException {
        try {
            Date dataHoje = new Date();
            faseProcesso.setDataHora(dataHoje);

            if ((faseProcesso.getProcesso().getFases() != null) && (!faseProcesso.getProcesso().getFases().isEmpty())) {
                if ((faseProcesso.getProcesso().getFases().get(faseProcesso.getProcesso().getFases().size() - 1).getTipo() == 2)
                        && (faseProcesso.getProcesso().getFases().get(faseProcesso.getProcesso().getFases().size() - 1).getJustificativa() == null)) {
                    throw new FaseException("Processo aguardando resposta do juiz");
                }
            }
            if (faseProcesso.getProcesso().getVencedor() != null) {
                throw new FaseException("Processo já foi finalizado, impossivel adicionar novas fases!");
            } else {
                faseProcesso.setTitulo((faseProcesso.getTitulo() != null) ? faseProcesso.getTitulo().trim().toUpperCase() : null);
                faseProcesso.setDescricao((faseProcesso.getDescricao() != null) ? faseProcesso.getDescricao().trim().toUpperCase() : null);
                ProcessoValidator.validaFase(faseProcesso);

                if ((faseProcesso.getProcesso() == null) || (faseProcesso.getProcesso().getId() == 0)) {
                    throw new FaseException("Necessário o número do processo para criação de uma nova fase");
                }
                if (arquivo != null) {
                    ProcessoValidator.validaDocumento(arquivo);
                    faseProcesso.setDocumento(new Documento());
                    faseProcesso.getDocumento().setExtensao(".pdf");
                }

                faseProcessoDao.cadastrarFaseProcesso(faseProcesso);
                if (arquivo != null) {
                    if (!salvarArquivoFase(faseProcesso, arquivo)) {
                        throw new ArquivoException("Problemas ao gravar documento de processo");
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao cadastrar nova fase do processo";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public static FaseProcesso buscaFaseProcessoId(int id) {
        try {
            return faseProcessoDao.buscaFaseProcessoId(id);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar fase de processo por id";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static void atualizarFaseProcesso(FaseProcesso fase) {
        try {
            faseProcessoDao.atualizarFaseProcesso(fase);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao atualizar fase de processo por id";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public static void cadastrarFaseProcessoWs(FaseProcesso faseProcesso) throws FaseException {
        try {
            Date dataHoje = new Date();
            faseProcesso.setDataHora(dataHoje);

            if ((faseProcesso.getProcesso().getFases() != null) && (!faseProcesso.getProcesso().getFases().isEmpty())) {
                if ((faseProcesso.getProcesso().getFases().get(faseProcesso.getProcesso().getFases().size() - 1).getTipo() == 2)
                        && (faseProcesso.getProcesso().getFases().get(faseProcesso.getProcesso().getFases().size() - 1).getJustificativa() == null)) {
                    throw new FaseException("Processo aguardando resposta do juiz");
                }
            }
            if (faseProcesso.getProcesso().getVencedor() != null) {
                throw new FaseException("Processo já foi finalizado, impossivel adicionar novas fases!");
            } else {
                faseProcesso.setTitulo((faseProcesso.getTitulo() != null) ? faseProcesso.getTitulo().trim().toUpperCase() : null);
                faseProcesso.setDescricao((faseProcesso.getDescricao() != null) ? faseProcesso.getDescricao().trim().toUpperCase() : null);
                ProcessoValidator.validaFase(faseProcesso);

                if ((faseProcesso.getProcesso() == null) || (faseProcesso.getProcesso().getId() == 0)) {
                    throw new FaseException("Necessário o número do processo para criação de uma nova fase");
                }
                faseProcessoDao.cadastrarFaseProcesso(faseProcesso);
            }
        } catch (DaoException e) {
            String msg = "Houve um problema ao cadastrar nova fase do processo" + e.getMessage();
            throw new FaseException(msg);            
        }
    }

    public static Boolean salvarArquivoFase(FaseProcesso faseProcesso, UploadedFile arquivo) {
        String caminho = SijogaUtil.caminhoProjeto() + "Documentos\\";
        String nomeArquivo = Integer.toString(faseProcesso.getDocumento().getId()) + faseProcesso.getDocumento().getExtensao();
        return SijogaUtil.salvarArquivo(arquivo, caminho, nomeArquivo);
    }
}
