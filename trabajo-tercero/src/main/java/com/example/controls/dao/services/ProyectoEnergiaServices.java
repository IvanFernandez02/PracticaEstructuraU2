package com.example.controls.dao.services;

import com.example.controls.dao.ProyectoEnergiaDao;
import com.example.controls.tda.list.LinkedList;
import com.example.models.ProyectoEnergia;

public class ProyectoEnergiaServices {
    private ProyectoEnergiaDao obj;  


    public ProyectoEnergiaServices(){
        obj = new ProyectoEnergiaDao();
    }


    public Boolean save() throws Exception{
        return obj.save();
    }
    public Boolean update() throws Exception{
        return obj.update();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public ProyectoEnergia getProyectoEnergia(){
        return obj.getProyectoEnergia();
    }

    public void setProyectoEnergia( ProyectoEnergia proyectoEnergia){
        obj.setProyectoEnergia(proyectoEnergia);    
    }
    public ProyectoEnergia get(Integer id) throws Exception{
        return obj.get(id);
    }


}   
