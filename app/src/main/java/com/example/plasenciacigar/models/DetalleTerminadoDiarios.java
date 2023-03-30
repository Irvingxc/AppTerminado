package com.example.plasenciacigar.models;

public class DetalleTerminadoDiarios {
    private int id;
    private int id_det_progra_term;
    private int id_terminado_diario;
    private Double cantidad;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_det_progra_term() {
        return id_det_progra_term;
    }

    public void setId_det_progra_term(int id_det_progra_term) {
        this.id_det_progra_term = id_det_progra_term;
    }

    public int getId_terminado_diario() {
        return id_terminado_diario;
    }

    public void setId_terminado_diario(int id_terminado_diario) {
        this.id_terminado_diario = id_terminado_diario;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
