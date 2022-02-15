package br.com.sijoga.facade;

import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.dto.IntimacaoDto;
import br.com.sijoga.dto.OficialDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class IntimacaoFacade {

    public static List<String> cadastrarIntimacao(IntimacaoDto intimacao) throws Exception {
        try {
            List<String> mensagens = new ArrayList();

            if (intimacao == null) {
                mensagens.add("Intimação não é válida");
            } else if (intimacao.getEndereco() == null) {
                mensagens.add("Endereço não é válido");
            } else if (intimacao.getOficial() == null) {
                mensagens.add("Oficial de justiça não é válido");
            } else {
                intimacao.setCpf(intimacao.getCpf().replace(".", "").replace("-", ""));
                intimacao.setNome(intimacao.getNome().trim().toUpperCase());
                intimacao.getEndereco().setRua(intimacao.getEndereco().getRua().trim().toUpperCase());
                intimacao.getEndereco().setBairro(intimacao.getEndereco().getBairro().trim().toUpperCase());
                intimacao.getEndereco().setCep(intimacao.getEndereco().getCep().replace(".", "").replace("-", "").trim());

                if (intimacao.getEndereco().getComplemento() != null) {
                    intimacao.getEndereco().setComplemento(intimacao.getEndereco().getComplemento().trim().toUpperCase());
                }

                if (intimacao.getProcesso() == 0) {
                    mensagens.add("Número do processo não é válido");
                }
                if ((intimacao.getCpf() == null) || (intimacao.getCpf().equals(""))) {
                    mensagens.add("CPF do intimado não é válido");
                }
                if ((intimacao.getNome() == null) || (intimacao.getNome().equals(""))) {
                    mensagens.add("Nome do intimado não é válido");
                }

                if (intimacao.getEndereco().getNumero() == 0) {
                    mensagens.add("Número do endereço de intimação não é válido");
                }
                if ((intimacao.getEndereco().getCep() == null) || (intimacao.getEndereco().getCep().equals(""))) {
                    mensagens.add("CEP de intimação não é válido");
                }
                if ((intimacao.getEndereco().getRua() == null) || (intimacao.getEndereco().getRua().equals(""))) {
                    mensagens.add("Rua de endereço de intimação não é válido");
                }
                if ((intimacao.getEndereco().getBairro() == null) || (intimacao.getEndereco().getBairro().equals(""))) {
                    mensagens.add("Bairro de endereço de intimação não é válido");
                }
                if (intimacao.getEndereco().getCidade() == 0) {
                    mensagens.add("Cidade de endereço de intimação não é válido");
                }

                if (mensagens.isEmpty()) {
                    intimacao.setDataHora(new Date());
                    Client client = ClientBuilder.newClient();
                    intimacao = client.target("http://localhost:8080/SOSIFOD/webresources/sosifod/novaIntimacao").request(MediaType.APPLICATION_JSON).post(Entity.json(intimacao), IntimacaoDto.class);                    
                    mensagens = montaFaseProcesso(intimacao);
                }
            }
            return mensagens;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static List<OficialDto> listaOficiais() {
        try {
            List<OficialDto> lista = null;
            Client client = ClientBuilder.newClient();
            Response resp = client.target("http://localhost:8080/SOSIFOD/webresources/sosifod/oficiais").request(MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() == 200) {
                lista = resp.readEntity(new GenericType<List<OficialDto>>() {
                });
            }

            return lista;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    public static List<String> montaFaseProcesso(IntimacaoDto intimacao) throws Exception {
        try {
            FaseProcesso fase = new FaseProcesso();
            fase.setProcesso(ProcessoFacade.buscaProcessoId(intimacao.getProcesso()));                        
            fase.setTitulo("Intimação Judicial");
            fase.setDataHora(new Date());
            fase.setTipo(1);            
            fase.setAdvogado(fase.getProcesso().getFases().get(fase.getProcesso().getFases().size()-1).getAdvogado()); //Advogado da ultima fase do processo
            
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
            String str = fmt.format(intimacao.getDataHora());
            String str2 = "";
            String status = "";
            if (intimacao.getDataHoraExec() != null) {
                str2 = fmt.format(intimacao.getDataHoraExec());                
            }        
            if (intimacao.getStatus() != null) {
                if (intimacao.getStatus()) {
                    status = "EXECUTADO";                    
                }  else {
                    status = "NÃO EXECUTADO";                    
                }                                
            } else {
                status = "PARA EXECUÇÃO";
            }
            
            String desc = "Intimação Judicial Nº" + Integer.toString(intimacao.getId()) + 
                          "<br />Data e hora da solicitação: " + str +
                          "<br />Data e hora execução: " + str2 +
                          "<br />Estado da execução: " + status +
                          "<br />Juiz: " + fase.getProcesso().getJuiz().getNome() + " OAB: " + fase.getProcesso().getJuiz().getRegistroOab() +
                          "<br />Advogado Solicitante: " + fase.getAdvogado().getNome() + " OAB: " + Integer.toString(fase.getAdvogado().getRegistroOab()) +
                          "<br />Intimado: " + (fase.getProcesso().getPromovente().getCpf().equals(intimacao.getCpf()) ? fase.getProcesso().getPromovente().getNome() : fase.getProcesso().getPromovido().getNome()) +
                          "<br />Oficial de Justiça: " + intimacao.getOficial().getNome();
            
            fase.setDescricao(desc);
            return FaseProcessoFacade.cadastrarFaseProcesso(fase, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }        
    }
}
