package br.com.sijoga.facade;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.dao.ProcessoDao;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.SijogaUtil;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.primefaces.model.UploadedFile;

public class ProcessoFacade {

    private static final ProcessoDao processoDao = new ProcessoDao();

    public static List<String> cadastrarProcesso(Processo processo, UploadedFile arquivo) throws NoSuchAlgorithmException, DaoException {
        try {
            List<String> mensagens = new ArrayList();
            Date dataHoje = new Date();
            FaseProcesso fase1 = processo.getFases().get(0);

            if ((processo.getAdvogadoPromovente() == null) || (processo.getAdvogadoPromovente().getId() == 0)) {
                mensagens.add("Advogado promovente inválido");
            }
            if ((processo.getAdvogadoPromovido() == null) || (processo.getAdvogadoPromovido().getId() == 0)) {
                mensagens.add("Advogado promovido inválido");
            }
            if ((processo.getAdvogadoPromovente() != null) && (processo.getAdvogadoPromovido() != null)) {
                if (processo.getAdvogadoPromovente().getId() == processo.getAdvogadoPromovido().getId()) {
                    mensagens.add("Um advogado não pode representar as duas partes");
                }
            }
            if ((processo.getPromovente() == null) || (processo.getPromovente().getId() == 0)) {
                mensagens.add("Parte promovente inválida");
            }
            if ((processo.getPromovido() == null) || (processo.getPromovido().getId() == 0)) {
                mensagens.add("Parte promovida inválida");
            }
            if ((processo.getPromovente() != null) && (processo.getPromovido() != null)) {
                if (processo.getPromovente().getId() == processo.getPromovido().getId()) {
                    mensagens.add("As partes não podem ser iguais");
                }
            }

            if ((processo.getFases() == null) || (processo.getFases().isEmpty())) {
                mensagens.add("Necessário uma fase para iniciar o processo");
            } else {
                fase1.setDataHora(dataHoje);
                fase1.setTipo(1);

                if ((fase1.getTitulo() == null)) {
                    mensagens.add("Necessário inserir um título para a primeira fase do processo");
                } else {
                    fase1.setTitulo(fase1.getTitulo().trim().toUpperCase());
                    if (("".equals(fase1.getTitulo())) || " ".equals(fase1.getTitulo())) {
                        mensagens.add("Titulo inválido");
                    }
                }

                if ((fase1.getDescricao() == null)) {
                    mensagens.add("Necessário inserir uma descrição para a primeira fase do processo");
                } else {
                    fase1.setDescricao(fase1.getDescricao().trim().toUpperCase());
                    if (("".equals(fase1.getDescricao())) || " ".equals(fase1.getDescricao())) {
                        mensagens.add("Descrição do processo inválida");
                    }
                }

                if ((fase1.getAdvogado() == null) || fase1.getAdvogado().getId() == 0) {
                    mensagens.add("Necessário um advogado para criação da primeira fase do processo");
                }

                if (arquivo != null) {
                    if (arquivo.getSize() > 2097152) {
                        mensagens.add("Tamanho do arquivo maior que 2MB");
                    }
                    if (!"application/pdf".equals(arquivo.getContentType())) {
                        mensagens.add("Formato de arquivo inválido");
                    }
                }
            }

            if (mensagens.isEmpty()) {
                processo.setDataInicio(dataHoje);
                processo.setJuiz(JuizFacade.buscarJuizProcessos()); //Buscar juiz com menos processos em aberto
                processo.getFases().remove(0);
                processoDao.cadastrarProcesso(processo);
                fase1.setProcesso(processo);
                mensagens = FaseProcessoFacade.cadastrarFaseProcesso(fase1, arquivo);
            }

            return mensagens;
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problemas ao cadastrar novo processo [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static String finalizarProcesso(Processo processo) throws DaoException {
        try {
            if (processo.getParecer() == null) {
                return "Necessário um parecer para encerrar o processo";
            } else if (processo.getParecer().trim().equals("")) {
                return "Parecer inválido";
            } else if ((processo.getVencedor() == null) || (processo.getVencedor().getId() == 0)) {
                return "Necessário indicar o vencedor do processo";
            } else if ((processo.getVencedor().getId() != processo.getPromovente().getId())
                    && (processo.getVencedor().getId() != processo.getPromovido().getId())) {
                return "Vencedor do processo inválido";
            } else {
                processo.setParecer(processo.getParecer().toUpperCase());
                processoDao.atualizarProcesso(processo);
                return null;
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao finalizar processo [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static Processo buscaProcessoId(int id) throws DaoException {
        try {
            return processoDao.buscaProcessoId(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao buscar processo por id [Facade]****" + e);
            e.printStackTrace();
            throw e;
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

    public static List<Processo> listaTodosProcessosJuiz(Juiz juiz) throws DaoException {
        try {
            return processoDao.listaTodosProcessosJuiz(juiz);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de juiz [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Processo> listaTodosProcessosParte(Parte parte) throws DaoException {
        try {
            return processoDao.listaTodosProcessosParte(parte);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de parte [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Processo> listaProcessosAdvogadoAbertos(Advogado advogado) throws DaoException {
        try {
            return processoDao.listaProcessosAdvogadoAbertos(advogado);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de advogado em aberto [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Processo> listaProcessosAdvogadoFechados(Advogado advogado) throws DaoException {
        try {
            return processoDao.listaProcessosAdvogadoFechados(advogado);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de advogado fechados [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromovido(Advogado advogado) throws DaoException {
        try {
            return processoDao.listaProcessosAdvogadoPromovido(advogado);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de advogado promovido [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromovente(Advogado advogado) throws DaoException {
        try {
            return processoDao.listaProcessosAdvogadoPromovente(advogado);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de advogado promovente [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromovidoGanho(Advogado advogado) throws DaoException {
        try {
            return processoDao.listaProcessosAdvogadoPromovidoGanho(advogado);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de advogado promovido ganho [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static List<Processo> listaProcessosAdvogadoPromoventeGanho(Advogado advogado) throws DaoException {
        try {
            return processoDao.listaProcessosAdvogadoPromoventeGanho(advogado);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("****Problema ao listar processos de advogado promovente ganho [Facade]****" + e);
            e.printStackTrace();
            throw e;
        }
    }

}
