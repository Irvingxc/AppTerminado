package com.example.plasenciacigar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetalleProgramacionTerminado {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("detid")
    @Expose
    private int detid;
    @SerializedName("numero_orden")
    @Expose
    private String numero_orden;
    @SerializedName("orden")
    @Expose
    private String orden;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("vitola")
    @Expose
    private String vitola;
    @SerializedName("capa")
    @Expose
    private String capa;
    @SerializedName("tipoempaque")
    @Expose
    private String tipoempaque;

    @SerializedName("cantidad")
    @Expose
    private String cantidad;

    @SerializedName("bultos")
    @Expose
    private String cantidadbultos;

    @SerializedName("unidades")
    @Expose
    private String unidades;

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getCantidadbultos() {
        return cantidadbultos;
    }

    public void setCantidadbultos(String cantidadbultos) {
        this.cantidadbultos = cantidadbultos;
    }

    public String getNumero_orden() {
        return numero_orden;
    }

    public void setNumero_orden(String numero_orden) {
        this.numero_orden = numero_orden;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getVitola() {
        return vitola;
    }

    public void setVitola(String vitola) {
        this.vitola = vitola;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getTipoempaque() {
        return tipoempaque;
    }

    public void setTipoempaque(String tipoempaque) {
        this.tipoempaque = tipoempaque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getDetid() {
        return detid;
    }

    public void setDetid(int detid) {
        this.detid = detid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
