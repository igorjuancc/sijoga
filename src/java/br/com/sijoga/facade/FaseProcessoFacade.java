package br.com.sijoga.facade;

import br.com.sijoga.bean.Documento;
import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.dao.FaseProcessoDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.SijogaUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.primefaces.model.UploadedFile;

public class FaseProcessoFacade {

    private static final FaseProcessoDao faseProcessoDao = new FaseProcessoDao();

    public static List<String> cadastrarFaseProcesso(FaseProcesso faseProcesso, UploadedFile arquivo) throws NoSuchAlgorithmException, DaoException {
        try {
            List<String> mensagens = new ArrayList();
            Date dataHoje = new Date();
            faseProcesso.setDataHora(dataHoje);
            
            if ((faseProcesso.getProcesso().getFases() != null) && (!faseProcesso.getProcesso().getFases().isEmpty())) {
                if ((faseProcesso.getProcesso().getFases().get(faseProcesso.getProcesso().getFases().size() - 1).getTipo() == 2)
                        && (faseProcesso.getProcesso().getFases().get(faseProcesso.getProcesso().getFases().size() - 1).getJustificativa() == null)) {
                    mensagens.add("Processo aguardando resposta do juiz");
                }
            } 
            if (faseProcesso.getProcesso().getVencedor() != null) {
                mensagens.add("Processo já foi finalizado, impossivel adicionar novas fases!");
            } else {
                if ((faseProcesso.getTipo() != 1) && (faseProcesso.getTipo() != 2)) {
                    mensagens.add("Fase de processo inválida");
                }

                if ((faseProcesso.getTitulo() == null)) {
                    mensagens.add("Necessário inserir um título fase do processo");
                } else {
                    faseProcesso.setTitulo(faseProcesso.getTitulo().trim().toUpperCase());
                    if (("".equals(faseProcesso.getTitulo())) || " ".equals(faseProcesso.getTitulo())) {
                        mensagens.add("Titulo inválido");
                    }
                }

                if ((faseProcesso.getDescricao() == null)) {
                    mensagens.add("Necessário inserir uma descrição para a primeira fase do processo");
                } else {
                    faseProcesso.setDescricao(faseProcesso.getDescricao().trim().toUpperCase());
                    if (("".equals(faseProcesso.getDescricao())) || " ".equals(faseProcesso.getDescricao())) {
                        mensagens.add("Descrição do processo inválida");
                    }
                }

                if ((faseProcesso.getAdvogado() == null) || faseProcesso.getAdvogado().getId() == 0) {
                    mensagens.add("Necessário um advogado para criação de fase do processo");
                }

                if ((faseProcesso.getProcesso() == null) || (faseProcesso.getProcesso().getId() == 0)) {
                    mensagens.add("Necessário o número do processo para criação de uma nova fase");
                }

                if (arquivo != null) {
                    faseProcesso.setDocumento(new Documento());
                    faseProcesso.getDocumento().setExtensao(".pdf");

                    if (arquivo.getSize() > 2097152) {
                        mensagens.add("Tamanho do arquivo maior que 2MB");
                    }
                    if (!"application/pdf".equals(arquivo.getContentType())) {
                        mensagens.add("Formato de arquivo inválido");
                    }
                }

                if (mensagens.isEmpty()) {
                    faseProcessoDao.cadastrarFaseProcesso(faseProcesso);
                    if (arquivo != null) {
                        if (!salvarArquivoFase(faseProcesso, arquivo)) {
                            mensagens.add("Problemas ao gravar arquivo");
                        }
                    }
                }
            }

            return mensagens;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar nova fase do processo [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static FaseProcesso buscaFaseProcessoId(int id) throws DaoException {
        try {
            return faseProcessoDao.buscaFaseProcessoId(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar fase de processo por id [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static void atualizarFaseProcesso(FaseProcesso fase) throws DaoException {
        try {
            faseProcessoDao.atualizarFaseProcesso(fase);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao atualizar fase de processo por id [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Boolean salvarArquivoFase(FaseProcesso faseProcesso, UploadedFile arquivo) {
        String caminho = "D:\\Documentos\\UFPR\\Aulas\\2020\\DAC\\SIJOGA\\web\\Documentos";
        String nomeArquivo = Integer.toString(faseProcesso.getDocumento().getId()) + faseProcesso.getDocumento().getExtensao();
        return SijogaUtil.salvarArquivo(arquivo, caminho, nomeArquivo);
    }
}
