package br.com.sijoga.ws;

import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.dto.IntimacaoDto;
import br.com.sijoga.exception.FaseException;
import br.com.sijoga.facade.FaseProcessoFacade;
import br.com.sijoga.facade.IntimacaoFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sijoga")
public class SijogaResource {

    @Context
    private UriInfo context;

    public SijogaResource() {
    }

    @POST
    @Path("/execIntimacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response novaIntimacao(IntimacaoDto intimacaoDto) {
        try {
            FaseProcesso fase = IntimacaoFacade.montaFaseProcesso(intimacaoDto);
            FaseProcessoFacade.cadastrarFaseProcessoWs(fase);
            intimacaoDto.setId(fase.getId());
            return Response.status(Response.Status.CREATED).entity(intimacaoDto).build();
        } catch (FaseException ex) {
            ex.printStackTrace(System.out);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
