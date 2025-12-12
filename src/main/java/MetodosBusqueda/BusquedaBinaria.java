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
public class BusquedaBinaria {
    public Producto buscar(List<Producto> listaD, String sku){
        int limiteInferior = 0;
        int limiteSuperior = listaD.size() -1;
        int Mitad, relacionar; String skuMedio;
        
        while(limiteInferior <= limiteSuperior){
            Mitad = limiteInferior + (limiteSuperior-limiteInferior)/2;
            skuMedio = listaD.get(Mitad).getSku();
            relacionar = skuMedio.compareTo(sku);
            
            //esto devuelve el producto
            if (relacionar == 0) return listaD.get(Mitad);
            else if (relacionar < 0) limiteInferior = Mitad + 1;
            else limiteSuperior = Mitad - 1;
        }
        return null;
    }
}
