package br.com.sijoga.facade;

import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.dto.EnderecoDto;
import br.com.sijoga.dto.IntimacaoDto;
import br.com.sijoga.dto.OficialDto;
import br.com.sijoga.exception.ArquivoException;
import br.com.sijoga.exception.DocumentoException;
import br.com.sijoga.exception.FaseException;
import br.com.sijoga.exception.IntimacaoException;
import br.com.sijoga.util.SijogaUtil;
import br.com.sijoga.validator.EnderecoValidator;
import br.com.sijoga.validator.ProcessoValidator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class IntimacaoFacade {

    public static void cadastrarIntimacao(IntimacaoDto intimacao, Parte intimado) throws IntimacaoException {
        String msg;
        try {
            if (intimado == null) {
                throw new IntimacaoException("Parte intimada inválida");
            } else if (intimado.getEndereco() == null) {
                throw new IntimacaoException("Endereço de Parte intimada inválido");
            } else if (intimacao == null) {
                throw new IntimacaoException("Intimação não é válida");
            } else if (intimacao.getOficial() == null) {
                throw new IntimacaoException("Oficial de justiça não é válido");
            } else {
                EnderecoDto end = new EnderecoDto();
                intimacao.setEndereco(end);
                intimacao.setCpf((intimado.getCpf() != null) ? intimado.getCpf().replace(".", "").replace("-", "") : null);
                intimacao.setNome((intimado.getNome() != null) ? intimado.getNome().trim().toUpperCase() : null);
                end.setBairro((intimado.getEndereco().getBairro() != null) ? intimado.getEndereco().getBairro().trim().toUpperCase() : null);
                end.setCep((intimado.getEndereco().getCep() != null) ? intimado.getEndereco().getCep().replace(".", "").replace("-", "").trim() : null);
                end.setCidade(intimado.getEndereco().getCidade().getId());
                end.setComplemento((intimado.getEndereco().getComplemento() != null) ? intimado.getEndereco().getComplemento().trim().toUpperCase() : null);
                end.setNumero(intimado.getEndereco().getNumero());
                end.setRua((intimado.getEndereco().getRua() != null) ? intimado.getEndereco().getRua().trim().toUpperCase() : null);

                EnderecoValidator.validaEnderecoDto(end);
                ProcessoValidator.validaIntimacao(intimacao);
                intimacao.setDataHora(new Date());

                Client client = ClientBuilder.newClient();
                WebTarget target = client.target("http://localhost:8080/SOSIFOD/webresources/sosifod/novaIntimacao");
                Response resp = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(intimacao, MediaType.APPLICATION_JSON));
                intimacao = resp.readEntity(IntimacaoDto.class);
                //intimacao = client.target("http://localhost:8080/SOSIFOD/webresources/sosifod/novaIntimacao").request(MediaType.APPLICATION_JSON).post(Entity.json(intimacao), IntimacaoDto.class);

                if (resp.getStatus() == 200) {
                    montaFaseProcesso(intimacao);
                } else {
                    msg = "Houve um problema ao cadastrar uma nova intimação: <br />";
                    msg += SijogaUtil.statusHttp(resp.getStatus());
                    SijogaUtil.mensagemErroRedirecionamento(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            msg = "Houve um problema ao cadastrar uma nova intimação";
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public static List<OficialDto> listaOficiais() {
        String msg;
        try {
            List<OficialDto> lista;
            Client client = ClientBuilder.newClient();
            Response resp = client.target("http://localhost:8080/SOSIFOD/webresources/sosifod/oficiais").request(MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() == 200) {
                lista = resp.readEntity(new GenericType<List<OficialDto>>() {
                });
            } else {
                msg = "Houve um problema ao buscar a lista de oficiais de justiça: <br />";
                msg += SijogaUtil.statusHttp(resp.getStatus());
                SijogaUtil.mensagemErroRedirecionamento(msg);
                return null;
            }

            return lista;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            msg = "Houve um problema ao buscar a lista de oficiais de justiça";
            SijogaUtil.mensagemErroRedirecionamento(msg);
            return null;
        }
    }

    public static void montaFaseProcesso(IntimacaoDto intimacao) {
        try {
            FaseProcesso fase = new FaseProcesso();
            fase.setProcesso(ProcessoFacade.buscaProcessoId(intimacao.getProcesso()));
            fase.setTitulo("Intimação Judicial");
            fase.setDataHora(new Date());
            fase.setTipo(1);
            fase.setAdvogado(fase.getProcesso().getFases().get(fase.getProcesso().getFases().size() - 1).getAdvogado()); //Advogado da ultima fase do processo

            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String str = fmt.format(intimacao.getDataHora());
            String str2 = "";
            String status;
            if (intimacao.getDataHoraExec() != null) {
                str2 = fmt.format(intimacao.getDataHoraExec());
            }
            if (intimacao.getStatus() != null) {
                if (intimacao.getStatus()) {
                    status = "EXECUTADO";
                } else {
                    status = "NÃO EXECUTADO";
                }
            } else {
                status = "PARA EXECUÇÃO";
            }

            String desc = "Intimação Judicial Nº" + Integer.toString(intimacao.getId())
                    + "<br />Data e hora da solicitação: " + str
                    + "<br />Data e hora execução: " + str2
                    + "<br />Estado da execução: " + status
                    + "<br />Juiz: " + fase.getProcesso().getJuiz().getNome() + " OAB: " + fase.getProcesso().getJuiz().getRegistroOab()
                    + "<br />Advogado Solicitante: " + fase.getAdvogado().getNome() + " OAB: " + Integer.toString(fase.getAdvogado().getRegistroOab())
                    + "<br />Intimado: " + (fase.getProcesso().getPromovente().getCpf().equals(intimacao.getCpf()) ? fase.getProcesso().getPromovente().getNome() : fase.getProcesso().getPromovido().getNome())
                    + "<br />Oficial de Justiça: " + intimacao.getOficial().getNome();

            fase.setDescricao(desc);
            FaseProcessoFacade.cadastrarFaseProcesso(fase, null);
        } catch (ArquivoException | DocumentoException | FaseException e) {
            e.printStackTrace(System.out);
            String msg = "Houve um problema ao montar fase do processo: <br />";
            msg += e.getMessage();
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }
}
