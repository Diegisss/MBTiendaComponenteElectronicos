/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Prueba;

import MetodosBusqueda.HashProducto;
import MetodosBusqueda.BusquedaBinaria;
import MetodosBusqueda.BusquedaSecuencial;
import Clases.Producto;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Angel y diego
 */
public class PruebaTiendaComponentes {

    static HashProducto ListaComponentes = new HashProducto();
    static String[] disponibles = {"Resistencia", "Capacitor", "Transistor",
        "Diodo-led", "Circuito Integrado"};

    public static void main(String[] args) {
        String[] op = {"1. AGREGAR COMPONENTE", "2. ELIMINAR COMPONENTE", "3. ACTUALIZAR COMPONENTE", "4. VER POR TIPO",
            "5. VER TODOS LOS COMPONENTES", "6. BUSCAR POR SKU", "7. PROBAR METODOS DE BUSQUEDA",
            "7. SALIR"};
        
        String opcion;
        do{
            opcion = (String)JOptionPane.showInputDialog(null, "SISTEMA DE INVENTARIO DE COMPONENTES ELECTRONICOS\n\nELIGE UNA OPCION", 
                    "MENU",JOptionPane.QUESTION_MESSAGE, null, op, op[0]);
            
            if(opcion == null) return;
            
            switch(opcion){
                case "1. AGREGAR COMPONENTE":
                    agregar(); break;
                case "2. ELIMINAR COMPONENTE":
                    eliminar(); break;
                case "3. ACTUALIZAR COMPONENTE":
                    actualizar(); break;
                case "4. VER POR TIPO":
                    verPorTipo(); break;
                case "5. VER TODOS LOS COMPONENTES":
                    verTodos(); break;
                case "6. BUSCAR POR SKU":
                    buscarPorSku(); break;
                case "7. PROBAR METODOS DE BUSQUEDA":
                    probarMetodosBusqueda(); break;
                case "8. SALIR":
                    JOptionPane.showMessageDialog(null, "Saliendo..."); break;
            } 
        } while (!"8. SALIR".equals(opcion));
    }
    
    //mi metodo para agregar
    public static void agregar(){
        try{
            String sku = JOptionPane.showInputDialog("INGRESE SKU DEL COMPONENTE: ");
            
            if(sku == null || sku.trim().isEmpty()) return;
            String tipo = (String)JOptionPane.showInputDialog(null, "SELECCIONA UN TIPO", "TIPOS DE COMPONENTES",
                    JOptionPane.QUESTION_MESSAGE, null, disponibles, disponibles[0]);
            
            if (tipo == null) return;
            
            String nombre = JOptionPane.showInputDialog("INGRESE EL NOMBRE DEL COMPONENTE ");
            if (nombre == null) return;
            
            //aqui declare un precio porque si el usuario cierra la ventana no pasa nada
            //si esta de tipo double no me dejaria hacer o verificar lo que conte
            //anteriormente
            String precioF = JOptionPane.showInputDialog("Precio ($)");
            if (precioF == null) return;
            double precio = Double.parseDouble(precioF);
            
            String unidadesF = JOptionPane.showInputDialog("CANTIDAD DE UNIDADES");
            if (unidadesF == null) return;
            int unidades = Integer.parseInt(unidadesF);
            Producto nuevo = new Producto(sku, nombre, tipo, precio, unidades);
            
            try{
                ListaComponentes.agregarProductos(nuevo);
                JOptionPane.showMessageDialog(null, "SE AGREGO EXITOSAMENTE");
            }catch(IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, "¡ERROR! " + e.getMessage());
            }  
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "DATO NUMERICO INVALIDO");
        }
    }
    
    //este es el metodo para eliminar
    public static void eliminar(){
        String sku = JOptionPane.showInputDialog("SKU DEL COMPONENTE PARA ELIMINAR");
        
        if (sku == null || sku.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "SKU INVALIDO"); return;
        }
        
        String tipo = (String)JOptionPane.showInputDialog(null, "SELECCIONA UN TIPO", "TIPOS DE COMPONENTES",
                    JOptionPane.QUESTION_MESSAGE, null, disponibles, disponibles[0]);
        
        if (tipo == null) return;
        
        try{
            ListaComponentes.eliminarProducto(sku, tipo);
            JOptionPane.showMessageDialog(null, "ELIMINADO CORRECTAMENTE");
        }catch(NoSuchElementException e){
            JOptionPane.showMessageDialog(null, "¡ERROR!" + e.getMessage());
        }
    }
    
    //aqui se encunetra el metodo de actualizar componentes
    public static void actualizar(){
        String sku = JOptionPane.showInputDialog("INGRESE EL SKU DEL COMPONENTE A MODIFICAR:");
        if (sku == null || sku.trim().isEmpty()) return;
        
        /*Primero checamos que en realidad exista
        OJO que aunque mi clase de HashProducto verifica
        que mejor que avisarles a mis usuarios de igual manera*/
        Producto existe = ListaComponentes.buscarProducto(sku);
        
        if(existe == null){
            JOptionPane.showMessageDialog(null, "NO EXISTE EN EL INVENTARIO");
            return;
        }
        //aqui se me hara mas sencillo mostrar lo que tenemos en ese instante
        JOptionPane.showMessageDialog(null, "DATOS ACTUALES:\nNombre: " + existe.getNombre() + 
                "\nPrecio: $" + existe.getPrecio() + "\nUnidades: " + existe.getUnidades());
        
        //si todo lo demas sale bien entonces pedimos los nuevos datos
        try{
            String nuevoNombre = JOptionPane.showInputDialog("NUEVO NOMBRE: ", existe.getNombre());
            if(nuevoNombre == null) return;
            
            String precioF = JOptionPane.showInputDialog("NUEVO PRECIO ($):", existe.getPrecio());
            if(precioF == null) return;
            double nuevoPrecio = Double.parseDouble(precioF);

            String unidadesF = JOptionPane.showInputDialog("NUEVAS UNIDADES:", existe.getUnidades());
            if(unidadesF == null) return;
            int nuevasUnidades = Integer.parseInt(unidadesF);
            
            ListaComponentes.actualizarProducto(sku, existe.getTipo(), nuevoNombre, nuevoPrecio, nuevasUnidades);
            JOptionPane.showMessageDialog(null, "¡ SE ACTUALIZADO CON ÉXITO :) !");
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "ERROR: INGRESASTE UN VALOR NUMERICO INVALIDO");
        }catch(NoSuchElementException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
    
    
    public static void verPorTipo(){
        String tipo = (String)JOptionPane.showInputDialog(null, "SELECCIONE UN TIPO PARA VISUALIZAR", "VER INVENTARIO",
                JOptionPane.QUESTION_MESSAGE, null, disponibles, disponibles[0]);
        
        if (tipo == null) return;
        
        Producto[] componentes = ListaComponentes.getPorTipo(tipo);
        
        if (componentes.length == 0){
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRARON COMPONENTES DEL TIPO " + tipo);
            return;
        }
        
        StringBuilder cadena = new StringBuilder();
        cadena.append("INFORMACION DEL ").append(tipo.toUpperCase()).append("\n\n");
        
        int totalUnidades = 0;
        double valorTotal = 0;
        
        for (Producto p : componentes) {
            double valor = p.getPrecio() * p.getUnidades();
            totalUnidades += p.getUnidades();
            valorTotal += valor;
            
            cadena.append("SKU: ").append(p.getSku()).append("\nNOMBRE: ").append(p.getNombre())
                    .append("\nUNIDADES: ").append(p.getUnidades()).append("\n$: ").append(p.getPrecio())
                    .append("\n-------------------------------------------------------------------\n");
        }
        cadena.append("\nTOTAL DE UNIDADES: ").append(totalUnidades);
        cadena.append("\nVALOR TOTAL DEL INVENTARIO: $").append(valorTotal);
        
        JTextArea insertar = new JTextArea(cadena.toString());
        insertar.setRows(40);
        insertar.setColumns(70);
        insertar.setEditable(false);
        JScrollPane scroll = new JScrollPane(insertar);
        
        scroll.setPreferredSize(new java.awt.Dimension(290, 300));
        
        JOptionPane.showMessageDialog(null, scroll, "INFORMACION DEL TIPO", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void verTodos(){
        StringBuilder cadena = new StringBuilder();
        cadena.append("INVENTARIO COMPLETO DE TODOS LOS TIPOS\n\n");
        double valorTotal = 0;
        
        for (String tipo : disponibles) {
            Producto[] componentes = ListaComponentes.getPorTipo(tipo);
            
            if (componentes.length > 0){
                cadena.append("CATEGORIA: ").append(tipo).append("\n");
                
                for (Producto p : componentes) {
                    cadena.append("   - ").append(p.getSku()).append(" : ").append(p.getNombre())
                      .append(" (").append(p.getUnidades()).append("U)\n");
                    
                    valorTotal += (p.getPrecio() * p.getUnidades());
                }
            }
        }
        cadena.append("\nVALOR TOTAL DE LA TIENDA :)").append(valorTotal);
        JTextArea insertar = new JTextArea(cadena.toString());
        insertar.setRows(40);
        insertar.setColumns(70);
        insertar.setEditable(false);
        JScrollPane scroll = new JScrollPane(insertar);
        
        scroll.setPreferredSize(new java.awt.Dimension(290, 300));
        
        JOptionPane.showMessageDialog(null, scroll, "INFORMACION GENERAL", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void buscarPorSku(){
        String sku = JOptionPane.showInputDialog("INGRESE EL SKU PARA BUSCAR");
        
        if(sku == null) return;
        
        Producto p = ListaComponentes.buscarProducto(sku);
        
        if (p != null) JOptionPane.showMessageDialog(null, "PRODUCTO ENCONTRADO: " + p.getNombre() + "\nTIPO: " + p.getTipo() +
                "\nPRECIO: $" + p.getPrecio() + "\nUnidades: " + p.getUnidades() + "u");
        else JOptionPane.showMessageDialog(null, "NO SE ENCONTRO EL PRODUCTO");
    }
    
    //este es el mas importante, incompleto por ahora
    public static void probarMetodosBusqueda(){
        JOptionPane.showMessageDialog(null, "FALTA IMPLEMENTARLO");
    }
}
