package br.com.sijoga.facade;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.dao.ProcessoDao;
import br.com.sijoga.exception.ArquivoException;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.exception.DocumentoException;
import br.com.sijoga.exception.FaseException;
import br.com.sijoga.exception.ProcessoException;
import br.com.sijoga.util.SijogaUtil;
import br.com.sijoga.validator.ProcessoValidator;
import java.util.Date;
import java.util.List;
import org.primefaces.model.UploadedFile;

public class ProcessoFacade {

    private static final ProcessoDao processoDao = new ProcessoDao();

    public static void cadastrarProcesso(Processo processo, UploadedFile arquivo) throws DocumentoException, FaseException, ProcessoException {
        try {
            Date dataHoje = new Date();
            FaseProcesso fase1 = processo.getFases().get(0);

            ProcessoValidator.validaProcesso(processo);

            if (fase1 != null) {
                fase1.setDataHora(dataHoje);
                fase1.setTipo(1);
                fase1.setTitulo((fase1.getTitulo() != null) ? fase1.getTitulo().trim().toUpperCase() : null);
                fase1.setDescricao((fase1.getDescricao() != null) ? fase1.getDescricao().trim().toUpperCase() : null);
            }

            ProcessoValidator.validaFase(fase1);
            if (arquivo != null) {
                ProcessoValidator.validaDocumento(arquivo);
            }

            processo.setDataInicio(dataHoje);
            processo.setJuiz(JuizFacade.buscarJuizProcessos()); //Buscar juiz com menos processos em aberto
            processo.getFases().remove(0);
            processoDao.cadastrarProcesso(processo);

            fase1.setProcesso(processo);
            FaseProcessoFacade.cadastrarFaseProcesso(fase1, arquivo);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao salvar novo processo";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        } catch (ArquivoException ex) {
            ex.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento(ex.getMessage());
        }
    }

    public static void finalizarProcesso(Processo processo) throws ProcessoException {
        try {
            if (processo.getParecer() == null) {
                throw new ProcessoException("Necessário um parecer para encerrar o processo");
            } else if (processo.getParecer().trim().equals("")) {
                throw new ProcessoException("Parecer inválido");
            } else if ((processo.getVencedor() == null) || (processo.getVencedor().getId() == 0)) {
                throw new ProcessoException("Necessário indicar o vencedor do processo");
            } else if ((processo.getVencedor().getId() != processo.getPromovente().getId())
                    && (processo.getVencedor().getId() != processo.getPromovido().getId())) {
                throw new ProcessoException("Vencedor do processo inválido");
            } else {
                processo.setParecer(processo.getParecer().toUpperCase());
                processoDao.atualizarProcesso(processo);
            }
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao finalizar processo";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public static Processo buscaProcessoId(int id) {
        try {
            return processoDao.buscaProcessoId(id);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao buscar processo por id";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaTodosProcessosAdvogado(Advogado advogado) {
        try {
            return processoDao.listaTodosProcessosAdvogado(advogado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos de advogado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaTodosProcessosJuiz(Juiz juiz) {
        try {
            return processoDao.listaTodosProcessosJuiz(juiz);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos de juiz";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaTodosProcessosParte(Parte parte) {
        try {
            return processoDao.listaTodosProcessosParte(parte);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos de parte";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaProcessosAdvogadoAbertos(Advogado advogado) {
        try {
            return processoDao.listaProcessosAdvogadoAbertos(advogado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos em aberto de advogado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaProcessosAdvogadoFechados(Advogado advogado) {
        try {
            return processoDao.listaProcessosAdvogadoFechados(advogado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos fechados de advogado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromovido(Advogado advogado) {
        try {
            return processoDao.listaProcessosAdvogadoPromovido(advogado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos promovidos de advogado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromovente(Advogado advogado) {
        try {
            return processoDao.listaProcessosAdvogadoPromovente(advogado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos de advogado promovente";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromovidoGanho(Advogado advogado) {
        try {
            return processoDao.listaProcessosAdvogadoPromovidoGanho(advogado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos promovidos ganho de advogado";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromoventeGanho(Advogado advogado) {
        try {
            return processoDao.listaProcessosAdvogadoPromoventeGanho(advogado);
        } catch (DaoException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao listar processos ganhos de advogado promovente";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

}
