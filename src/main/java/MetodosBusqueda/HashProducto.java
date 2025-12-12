package MetodosBusqueda;
import Clases.Producto;
import java.util.NoSuchElementException;
/**
 *
 * @author diego123
 */
public class HashProducto {
    private Producto [][] tablita;
    private int maximo[], cantidad[];
    
    public HashProducto(){
        tablita = new Producto[5][];
        maximo = new int[5];
        cantidad = new int [5];
        
        for (int i = 0; i < 5; i++) {
            maximo[i] = 1000000; //usare un tamaño fijo
            tablita[i] = new Producto[maximo[i]];
            cantidad[i] = 0;
        }
    }
    
    //metodo para agregar productos
    public void agregarProductos(Producto componente){
        if (componente == null) return;
        
        int indice = Indice(componente.getTipo());
        int pos = hash(componente.getSku()) % maximo[indice];
        int inicio = pos;
        
        //dado a que se haria mas lento con mi while, decidi hacer un acceso local a mi array
        Producto[] tablitaActual = tablita[indice];
        int capacidad = maximo[indice];

        //aqui es para manejar las colisiones
        while (tablitaActual[pos] != null){
            if (tablitaActual[pos].getSku() == componente.getSku() || 
                    tablitaActual[pos].getSku().equals(componente.getSku())){
                return;
            }
            pos = (pos + 1) % capacidad;
            //aqui lo que hago es que si la tabla esta lleno, ya no paso nada
            if(pos == inicio) return;
        }
        tablitaActual[pos] = componente;
        cantidad[indice]++;
    }
    
    //aqui buscamos un producto con su clave(sku) y su tipo esto es para la comparacion!!!
    public Producto buscarProducto(String sku, String tipo){
        int indice = Indice(tipo);
        if (indice == -1) return null;
        
        int pos = hash(sku) % maximo[indice];
        int inicio = pos;
        
        Producto[] tablitaActual = tablita[indice];
        int capacidad = maximo[indice];
        String skuBuscado = sku;
        
        //buscamos en la posicion que se calculo y en la que sigue
        while(tablitaActual[pos] != null){
            Producto Actual = tablitaActual[pos];
            if(Actual.getSku().equals(skuBuscado)) return Actual;
            
            pos = (pos + 1) % capacidad;
            
            if (pos == inicio) break; 
        }
        return null;
    }
    
    private final String[] TIPOS_COMPONENTES = {"Resistencia", "Capacitor", 
        "Transistor", "Diodo-led", "Circuito Integrado"};
    
    //buscamos para todos los tipos
    public Producto buscarProducto(String sku){
        //podemos buscar en cada categoria
        for (String tipo : TIPOS_COMPONENTES) {
            int indice = Indice(tipo);
            if (indice == -1) continue;
            int pos = hash(sku) % maximo[indice];
            int inicio = pos;
            
            Producto[] tablitaActual = tablita[indice];
            int capacidad = maximo[indice];
            
            while(tablitaActual[pos] != null){
                if(tablitaActual[pos].getSku().equals(sku)) return tablitaActual[pos];
                
                pos = (pos + 1) % capacidad;
                if (pos == inicio) break;
            }
        }
        return null;
    }
    
    //metodo de eliminar
    public void eliminarProducto(String sku, String tipo){
        int indice = Indice(tipo);
        if (indice == -1) throw new NoSuchElementException("TIPO DE COMPONENTE NO VALIDO: " + tipo);
        
        int pos = hash(sku) % maximo[indice];
        int inicio = pos;
        
        Producto[] tablitaActual = tablita[indice];
        int capacidad = maximo[indice];
        
        while(tablitaActual[pos] != null){
            if (tablitaActual[pos].getSku().equals(sku)){
                tablitaActual[pos] = null;
                cantidad[indice]--;
                return;
            }
            //seguimos
            pos = (pos + 1) % capacidad; 
            if (pos == inicio) break;
        }
        throw new NoSuchElementException("NO ENCONTRAMOS NINGUN COMPONENTE CON EL SKU: " + sku);
    }
    
    public Producto[] getPorTipo(String tipo){
        int indice = Indice(tipo);
        if (indice == -1) return new Producto[0];
        
        int tamaño = cantidad[indice];
        Producto[] resultado = new Producto[tamaño];
        
        Producto[] filaTablita = tablita[indice];
        int cantidad = 0; 
        
        for (int i = 0; i < filaTablita.length; i++) {
            if (filaTablita[i] != null){
                resultado[cantidad] = filaTablita[i];
                cantidad++;
                
                if (cantidad == tamaño) break;
            }
        }
        return resultado;
    }
    
    public void actualizarProducto(String sku, String tipo, String nuevoNombre, double nuevoPrecio, int nuevasUnidades) {
        int indice = Indice(tipo);
        if (indice == -1) throw new NoSuchElementException("TIPO NO VALIDO");

        int pos = hash(sku) % maximo[indice];
        int inicio = pos;
        Producto[] tablitaActual = tablita[indice];
        int capacidad = maximo[indice];
        
        while (tablitaActual[pos] != null) {
            //si esta condicion se llega a cumplir, entonces actualizamos
            if (tablitaActual[pos].getSku().equals(sku)) {
                
                tablitaActual[pos].setNombre(nuevoNombre);
                tablitaActual[pos].setPrecio(nuevoPrecio);
                tablitaActual[pos].setUnidades(nuevasUnidades);
                return; 
            }
            
            // Seguimos buscando por si hubo colisión :)
            pos = (pos + 1) % capacidad;
            if (pos == inicio) break;
        }
        
        // Si llegamos aquí, es que no existe :(
        throw new NoSuchElementException("NO SE ENCONTRO EL PRODUCTO CON SKU: " + sku);
    }
    
    //funcion implementada de hash
    private int hash(String sku){
        int hash = sku.hashCode();
        //investigado me di cuenta que era mas efectivo que ocupar Math.abs
        return (hash<0) ? -hash : hash;
        /*try{
            String num = sku.replaceAll("[^0-9]", "");
            //si no se encuentra vacio, entonces tomamos 6 digitos como maximo para evitar overflow
            if (!num.isEmpty()) return Integer.parseInt(num.substring(0, Math.min(6, num.length())));
        }catch (NumberFormatException error){
            //aqui chance y ocupamos el hashcode pero no se, luego vere
        }return Math.abs(sku.hashCode());*/
    }
    
    private int Indice(String tipo){
        if (tipo == null) return -1;
        
        switch(tipo.toUpperCase()){
            case "RESISTENCIA": return 0;
            case "CAPACITOR": return 1;
            case "TRANSISTOR": return 2;
            case "DIODO-LED": return 3;
            case "CIRCUITO INTEGRADO": return 4;
            default: return -1;
        }
    }
}
