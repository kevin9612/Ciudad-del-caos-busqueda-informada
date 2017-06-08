package programacion.mundo;



import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Vertices {

    String dato;
    Map<Vertices, String> mapaAdyacentes = new HashMap<>();
    int grado;
    Vertices anterior;
    
    public Vertices() {
		// TODO Auto-generated constructor stub
	}
    public Vertices(String dato) {
        this.dato = dato;
        this.grado = 0;
        this.anterior = null;
    }

    public String getDato() {
        return dato;
    }

    //---

 
    public void agregarArista(Vertices vertice, String movimiento) {
        mapaAdyacentes.put(vertice, movimiento);
    }
        
    public Collection<Vertices> getAdyacentes() {
        return mapaAdyacentes.keySet();
    }
    
    public String caminoPara(Vertices actor) {
        return mapaAdyacentes.get(actor);
    }
    
    @Override
    public String toString() {
        return "{" + dato + "}";
    }
    
    public int getGrado() {
        return this.grado;
    }
    
    public void setGrado( int grado ) {  
        this.grado = grado;
    }
    
    public Vertices getAnterior() {
        return this.anterior;
    }
    
    public void setAnterior ( Vertices anterior ) {
        this.anterior = anterior;
    }

	
}
