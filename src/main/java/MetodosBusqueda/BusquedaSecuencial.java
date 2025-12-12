/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MetodosBusqueda;
import Clases.Producto;
import java.util.List;
/**
 *
 * @author diego123
 */
public class BusquedaSecuencial {
    public Producto buscar(List<Producto> listaD, String sku){
        for (int i = 0; i < listaD.size(); i++) {
            if(listaD.get(i).getSku().equals(sku)){
                return listaD.get(i);
            }
        }
        return null;
    }
}
