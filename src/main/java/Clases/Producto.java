/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author diego123
 */
public class Producto {
    private String sku, nombre, tipo;
    private double precio;
    private int unidades;

    public Producto(String sku, String nombre, String tipo, double precio, int unidades) {
        this.sku = sku;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.unidades = unidades;
    }
    
    public String getSku(){
        return sku;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }
    
    public int getUnidades(){
        return unidades;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void setPrecio(double precio){
        this.precio = precio;
    }
    
    public void setUnidades(int unidades){
        this.unidades = unidades;
    }
    
}
