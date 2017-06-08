package programacion.mundo;



import java.util.HashMap;
import java.util.Map;

public class Grafo {

    Map<String, Vertices> vertices;

public Grafo() {
	
	
	vertices = new HashMap<>();
}
    
    
    
    public void addArista(String p, String hijo, String movimiento) {
        
        Vertices v2 = getVertice(hijo);
        Vertices v1 = getVertice(p);
        if (v1 != null && v2 != null) {
            v1.agregarArista(v2,movimiento);
            v2.agregarArista(v1, movimiento);
        }
    }    
    
     public Vertices addVertice(String matriz){
        Vertices verticeB = new Vertices(matriz);
        vertices.put(matriz, verticeB);
        return verticeB;
    }
    
     
    public Vertices getVertice(String dato) { 
    
        return vertices.get(dato);
    }

	
}
