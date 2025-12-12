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

/*Hice esta clase porque no quise usar una libreria que tenia java que era la de
Collections.sort, quise usar mi propia creacion del algoritmo (mejor dicho reutilizarlo)
con el fin de no usar o depender tanto de las librerias, aunque para eso fueron hechas
pero es mas que nada para mejorar la logica y recordar los metodos de ordenacion*/
public class QuickSort {

    public static int d = 0;

    public void ordenar(List<Producto> lista) {
        d = 0;
        if (lista != null && lista.size() > 0) {
            quickSort(lista, 0, lista.size() - 1);
        }
    }
    //mi metodo metodo recursivo
    private void quickSort(List<Producto> v, int l, int h) {
        int pivote;
        if (l < h) {
            // se acomoda el pivote en su posicion correcta
            pivote = Separar(v, l, h);
            // se ordena la parte izquierda
            quickSort(v, l, pivote - 1);
            // lo mismo pero ahora con valores mayores y por la derecha
            quickSort(v, pivote + 1, h);
        }
    }

    // mi metodo para separar
    private int Separar(List<Producto> v, int l, int h) {
        int medio = l + (h - l) / 2;

        // Esto evita el "peor caso" de QuickSort en listas ordenadas
        Producto tempPivote = v.get(medio);
        v.set(medio, v.get(h));
        v.set(h, tempPivote);

        // el pivote ya no es el del final, sino el del medio.
        String pivote = v.get(h).getSku(); 
        
        Producto temporal; 
        int i = l - 1; 

        for (int j = l; j < h; j++) {
            d++; 
            if (v.get(j).getSku().compareTo(pivote) <= 0) {
                i = i + 1;
                // aplicamos permutaciones
                temporal = v.get(i);
                v.set(i, v.get(j));
                v.set(j, temporal);
            }
        }
        
        temporal = v.get(i + 1);
        v.set(i + 1, v.get(h));
        v.set(h, temporal);
        
        return i + 1; 
    }
}
