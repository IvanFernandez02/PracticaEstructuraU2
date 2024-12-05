package com.example.rest;

import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.controls.dao.ProyectoEnergiaDao;
import com.example.controls.dao.services.PersonaServices;
import com.example.controls.dao.services.ProyectoEnergiaServices;
import com.example.controls.exception.ListEmptyException;
import com.example.controls.tda.list.LinkedList;
import com.google.gson.Gson;
import com.example.models.*;



@Path("energia")
public class ProyectoEnergiaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyectoEnergia() {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServices ps = new ProyectoEnergiaServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[]{});

        }
        return Response.ok(map).build();
    } 
    /* @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap mapa = new HashMap<>();
        ProyectoEnergiaServices pd = new ProyectoEnergiaServices();
        //PersonaServices pd = new PersonaServices();
        String aux = "";
        try {
            pd.getProyectoEnergia().setNombre("Proyecto 1");
            pd.getProyectoEnergia().setInversion(1000.0);       
            pd.getProyectoEnergia().setTiempoVida(10);
            pd.getProyectoEnergia().setFechaInicio("12/12/2020");
            pd.getProyectoEnergia().setFechaFin("12/12/2021");
            pd.getProyectoEnergia().setCapacidadDiaria(100.0);
            pd.save(); 
            
          
            aux = "La lista esta vasia"+pd.listAll().isEmpty();
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        mapa.put("msg","Ok");
        mapa.put("data", "test"+aux);
        return Response.ok(mapa).build();
    } */
 

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        
            /// TODO
            /// VALIDACION
       
        System.out.println("ASAS");
        ProyectoEnergiaDao ps = new ProyectoEnergiaDao();
        
       

        try {
            ps.getProyectoEnergia().setNombre(map.get("nombre").toString());
            ps.getProyectoEnergia().setInversion(Double.parseDouble(map.get("inversion").toString()));
            ps.getProyectoEnergia().setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
            ps.getProyectoEnergia().setFechaInicio(map.get("fechaInicio").toString());
            ps.getProyectoEnergia().setFechaFin(map.get("fechaFin").toString());
            ps.getProyectoEnergia().setCapacidadDiaria(Double.parseDouble(map.get("capacidadDiaria").toString()));
            
            System.out.println("ASs");

            ps.save();
            res.put("msg", "OK");
            res.put("data", "Proyecto Registarado");
            return Response.ok(res).build();

            
        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }

    }
    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyectoEnergia(@PathParam ("id")Integer  id) {
        HashMap map = new HashMap<>();
        ProyectoEnergiaServices ps = new ProyectoEnergiaServices();
       
        try {
            ps.setProyectoEnergia(ps.get(id));
        } catch (Exception e) {
            
        }
        map.put("msg", "Ok");
        map.put("data", ps.getProyectoEnergia());
        
        if (ps.getProyectoEnergia().getId() == null) {
            
            map.put("data", "Persona no encontrada");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        
        return Response.ok(map).build();

    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {
        HashMap res = new HashMap<>();
        
            /// TODO
            /// VALIDACION
       
        System.out.println("ASAS");
        
    
        try {
            ProyectoEnergiaDao ps = new ProyectoEnergiaDao();
            ps.setProyectoEnergia(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getProyectoEnergia().setNombre(map.get("nombre").toString());
            ps.getProyectoEnergia().setInversion(Double.parseDouble(map.get("inversion").toString()));
            ps.getProyectoEnergia().setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
            ps.getProyectoEnergia().setFechaInicio(map.get("fechaInicio").toString());
            ps.getProyectoEnergia().setFechaFin(map.get("fechaFin").toString());
            ps.getProyectoEnergia().setCapacidadDiaria(Double.parseDouble(map.get("capacidadDiaria").toString()));
            
            System.out.println("ASs");

            ps.update();
            res.put("msg", "OK");
            res.put("data", "Proyecto Registarado");
            return Response.ok(res).build();

            
        } catch (Exception e) {
            System.out.println("Error" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        }

    }

    //Cosas IVAN

    @Path("/sort")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortProyectos(HashMap<String, Object> sortParams) {
        HashMap<String, Object> res = new HashMap<>();
        ProyectoEnergiaServices ps = new ProyectoEnergiaServices();
        LinkedList<ProyectoEnergia> proyectos = ps.listAll();
        
        String sortBy = sortParams.get("sortBy").toString().toLowerCase();
        String sortOrder = sortParams.get("sortOrder").toString().toLowerCase();
        String sortMethod = sortParams.get("sortMethod").toString().toLowerCase();

        Comparator<ProyectoEnergia> comparator = null;
        switch(sortBy) {
            case "id":
                comparator = Comparator.comparing(ProyectoEnergia::getId);
                break;
            case "nombre":
                comparator = Comparator.comparing(ProyectoEnergia::getNombre);
                break;
            case "inversion":
                comparator = Comparator.comparing(ProyectoEnergia::getInversion);
                break;
            case "tiempoVida":
                comparator = Comparator.comparing(ProyectoEnergia::getTiempoVida);
                break;
            default:
                res.put("msg", "Error");
                res.put("data", "Invalid sort criteria");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
        }

        // Reverse comparator if descending order
        if (sortOrder.equals("desc")) {
            comparator = comparator.reversed();
        }

        // Apply sorting method
        switch(sortMethod) {
            case "quicksort":
                proyectos.quickSort(comparator);
                break;
            case "mergesort":
                proyectos.mergeSort(comparator);
                break;
            case "shellsort":
                proyectos.shellSort(comparator);
                break;
            default:
                res.put("msg", "Error");
                res.put("data", "Invalid sort method");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
        }

        res.put("msg", "OK");
        res.put("data", proyectos.toArray());
        return Response.ok(res).build();
    }

    //Buscar del IVAN
    
    @Path("/search")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProyectos(HashMap<String, Object> searchParams) throws IndexOutOfBoundsException, ListEmptyException {
        HashMap<String, Object> res = new HashMap<>();
        ProyectoEnergiaServices ps = new ProyectoEnergiaServices();
        LinkedList<ProyectoEnergia> proyectos = ps.listAll();

        String searchBy = searchParams.get("searchBy").toString().toLowerCase();
        String searchMethod = searchParams.get("searchMethod").toString().toLowerCase();
        String searchValue = searchParams.get("searchValue").toString().toLowerCase(); // Convertimos a minúsculas para
                                                                                       // búsqueda insensible

        LinkedList<ProyectoEnergia> results = new LinkedList<>();

        if (searchMethod.equals("linear")) {
            // Búsqueda lineal que compara prefijos
            for (ProyectoEnergia proyecto : proyectos.toArray()) {
                String valorComparar = "";

                switch (searchBy) {
                    case "nombre":
                        valorComparar = proyecto.getNombre().toLowerCase();
                        break;
                    case "id":
                        valorComparar = proyecto.getId().toString();
                        break;
                    
                    case "tiempoVida":
                        valorComparar = proyecto.getTiempoVida().toString();
                        break;
                    default:
                        continue;
                }

                if (valorComparar.startsWith(searchValue)) {
                    results.add(proyecto);
                }
            }
        } else if (searchMethod.equals("binary")) {
            // Para búsqueda binaria, primero ordenamos
            Comparator<ProyectoEnergia> comparator = null;
            switch (searchBy) {
                case "nombre":
                    comparator = Comparator.comparing(p -> p.getNombre().toLowerCase());
                    break;
                case "id":
                    comparator = Comparator.comparing(ProyectoEnergia::getId);
                    break;
                case "tiempoVida":
                    comparator = Comparator.comparing(ProyectoEnergia::getTiempoVida);
                    break;
                default:
                    res.put("msg", "Error");
                    res.put("data", "Invalid search criteria");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            proyectos.quickSort(comparator);

            // Búsqueda binaria para encontrar el primer match
            int index = findFirstMatch(proyectos.toArray(), searchValue, searchBy);
            if (index != -1) {
                // Añadir todos los elementos que coincidan desde el índice encontrado
                while (index < proyectos.getSize()) {
                    ProyectoEnergia proyecto = proyectos.get(index);
                    String valorComparar = "";
                    switch (searchBy) {
                        case "nombre":
                            valorComparar = proyecto.getNombre().toLowerCase();
                            break;
                        case "id":
                            valorComparar = proyecto.getId().toString();
                            break;
                        
                        case "tiempoVida":
                            valorComparar = proyecto.getTiempoVida().toString();
                            break;
                    }

                    if (valorComparar.startsWith(searchValue)) {
                        results.add(proyecto);
                    } else {
                        break;
                    }
                    index++;
                }
            }
        }

        res.put("msg", "OK");
        res.put("data", results.toArray());
        return Response.ok(res).build();
    }

    // Método auxiliar para encontrar la primera coincidencia en búsqueda binaria
    private int findFirstMatch(ProyectoEnergia[] arr, String prefix, String searchBy) {
        int left = 0;
        int right = arr.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midValue = "";

            switch (searchBy) {
                case "nombre":
                    midValue = arr[mid].getNombre().toLowerCase();
                    break;
                case "id":
                    midValue = arr[mid].getId().toString();
                    break;
                
                case "tiempoVida":
                    midValue = arr[mid].getTiempoVida().toString();
                    break;
            }

            if (midValue.startsWith(prefix)) {
                result = mid;
                right = mid - 1; 
            } else if (midValue.compareTo(prefix) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public int searchContains(ProyectoEnergia[] arr, String prefix, String searchBy) {
        int left = 0;
        int right = arr.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midValue = "";

            switch (searchBy) {
                case "nombre":
                    midValue = arr[mid].getNombre().toLowerCase();
                    break;
                case "id":
                    midValue = arr[mid].getId().toString();
                    break;
                
                case "tiempoVida":
                    midValue = arr[mid].getTiempoVida().toString();
                    break;
            }

            if (midValue.contains(prefix)) {
                result = mid;
                right = mid - 1; 
            } else if (midValue.compareTo(prefix) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }
}