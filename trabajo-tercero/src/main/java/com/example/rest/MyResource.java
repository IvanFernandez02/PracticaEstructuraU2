package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.controls.dao.PersonaDao;
import com.example.controls.dao.services.PersonaServices;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap mapa = new HashMap<>();
        PersonaServices pd = new PersonaServices();
        //PersonaServices pd = new PersonaServices();
        String aux = "";
        try {
            pd.getPersona().setNombre("Pablo");
            pd.getPersona().setApellido("Morocho");
            pd.getPersona().setDni("1234567890");
            pd.getPersona().setNumCelular("0987654321");
            pd.getPersona().setFechaNacimiento("12/12/2020");
            pd.getPersona().setSexo("M");
            pd.getPersona().setTipo("Cedula");
            pd.save();
          
            aux = "La lista esta vasia"+pd.listAll().isEmpty();
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        mapa.put("msg","Ok");
        mapa.put("data", "test"+aux);
        return Response.ok(mapa).build();
    }
}
