package com.example.controls.dao;

import com.example.controls.dao.implement.AdapterDao;
import com.example.controls.tda.list.LinkedList;
import com.example.models.ProyectoEnergia;

public class ProyectoEnergiaDao extends AdapterDao<ProyectoEnergia> {
    private ProyectoEnergia proyectoEnergia;
    private LinkedList listAll;

    public ProyectoEnergiaDao() {
        super(ProyectoEnergia.class);
    }
    public ProyectoEnergia getProyectoEnergia() {
        if (proyectoEnergia == null) {
            proyectoEnergia = new ProyectoEnergia();
        }
        return this.proyectoEnergia;
    }
    public void setProyectoEnergia(ProyectoEnergia proyectoEnergia) {
        this.proyectoEnergia = proyectoEnergia;
    }

    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return listAll;
    }
    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        proyectoEnergia.setId(id);
        this.persist(this.proyectoEnergia);
        this.listAll = listAll();
        return true;
    }
    public Boolean update() throws Exception {
        this.merge(getProyectoEnergia(), getProyectoEnergia().getId() -1);
        this.listAll = listAll();
        return true;
    }
    
}
