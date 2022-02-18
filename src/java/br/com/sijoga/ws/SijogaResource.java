package br.com.sijoga.ws;

import br.com.sijoga.dto.IntimacaoDto;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.facade.IntimacaoFacade;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
        IntimacaoFacade.montaFaseProcesso(intimacaoDto);
        return Response.ok().entity(intimacaoDto).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
