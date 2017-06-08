package programacion.controlador;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import programacion.interfaz.PanelDatos;
import programacion.interfaz.PanelMundo;
import programacion.interfaz.PanelSimulaciones;
import programacion.mundo.Execution;
import programacion.mundo.Karel;
import programacion.mundo.World;

/*
 * Controaldor.java
 * Patron Interfaz - Controlador  - Mundo
 * @author Giovanni Fajardo Utria
 * @version 1.0
 */
public class Controlador
{
// Atributos	
   private World world;	
   private Karel karel;
   private PanelMundo pnlMundo;
   private PanelSimulaciones pnlSimulaciones;
   private PanelDatos pnlDatos;
   private  Execution execution;
   private ArrayList<String> temporal;
   
/**
 * Constructor por defecto
 */	
  public Controlador()
  { world = new World();
  	temporal = new ArrayList<>();
  }
    
  public void conectar( PanelMundo pnlMundo, PanelSimulaciones pnlSimulaciones, PanelDatos pnlDatos )
  { this.pnlMundo = pnlMundo; 
	this.pnlSimulaciones = pnlSimulaciones;  
	this.pnlDatos = pnlDatos;
  }

  private void actualizar()
  { pnlDatos.setAvenue( karel.getAvenue() );
	pnlDatos.setStreet( karel.getStreet() );
	pnlDatos.setFacing( karel.getCompass() );
	pnlDatos.setBeepers( karel.getBeepers() );
  }
  
/* -----------------------------------------------------------------------------  
 * Requerimientos funcionales
 * ----------------------------------------------------------------------------- 
 */
   
/**
 * Req-1: Poner muros en el mundo.
 */  
  public void putBloks( int avenue, int street, String text )
  { world.bloks( avenue, street, text );
   
  }

/**
 * Req-2: Poner Beepers en el mundo.
 */  
  public void putBeeper( int avenue, int street, String text )
  { world.beepers( avenue, street, text );
  }
  
/**
 * Req-3: Ubicar a Karel en el mundo.
 * @param informacion 
 */  
  public void ubicarKarel( int avenue, int street, int beepers, String compass, String informacion)
  { pnlMundo.setText( avenue, street, "" );
	pnlMundo.setKarel( avenue, street, compass );
    karel = new Karel( avenue, street, beepers, compass, world);
    world.setKarel( karel );
 /* -----------------------------------------------------------------------------------
  * Se comentario para permitir agregar obst�culos mientras karel se mueve en el mundo
    pnlMundo.removeLabelClicMouse();
  */
    pnlMundo.setKarel( true );
    actualizar();    
    execution = new Execution(world, this);
    execution.setInfo(informacion);
    execution.findPath(informacion, karel.getAvenue(), karel.getStreet(), karel.getCompass());
	//pnlMundo.pintarBusqueda(execution.recorrerGrafoParaPintar(karel.getAvenue()+","+karel.getStreet()));
    execution.start();	
  }
  
  
/**
 * Req-3.1 Identifica si se ha llegado a la meta 
 */
  public boolean goal()
  { return karel.goal();
  }
  
/**
* Req-3.2 Ubica la meta en el mundo 
*/
  public void putHome( int avenue, int street, String text )
  { world.home(avenue, street, text);
  }

/**
 * Req-3.3 Ubica obst�culos en el mundo, durante la ejecuci�n 
 */
   public void putObstacle( int avenue, int street, String text )
   { world.obstacle(avenue, street, text); 
   }

/**
 * Req-3.4 Envia mensajes durante la ejecuci�n 
 */
   public void setMessage( String text )
   { pnlDatos.setCodigo( text ); 
   }
   
/**
 * Req-3.5: Mover a Karel en el mundo.
 * Utiliza un hilo de ejecuci�n
 */  
  public boolean move()
  { int avenue = karel.getAvenue(), street = karel.getStreet();
    boolean swmove;
	pnlMundo.setKarel( karel.getAvenue(), karel.getStreet(), "" );
	swmove = karel.move();
	pnlMundo.setText( karel.getAvenue(), karel.getStreet(), "" );
    pnlMundo.setKarel( karel.getAvenue(), karel.getStreet(), karel.getCompass() );
	pnlMundo.setText( avenue, street, world.refresh( avenue, street ) );
    actualizar();
 // pnlDatos.setCodigo( "move( );" );
	return swmove;
  }
  
/**
 * Req-4: Mover a Karel en el mundo.
 */  
  public void moverKarel()
  { int avenue = karel.getAvenue(), street = karel.getStreet();
	pnlMundo.setKarel( karel.getAvenue(), karel.getStreet(), "" );
	karel.move();
	pnlMundo.setText( karel.getAvenue(), karel.getStreet(), "" );
    pnlMundo.setKarel( karel.getAvenue(), karel.getStreet(), karel.getCompass() );
	pnlMundo.setText( avenue, street, world.refresh( avenue, street ) );
  	actualizar();
 	pnlDatos.setCodigo( "move( );" );
  }

/**
 * Req-5: Girar Karel a la izquierda.
 */  
  public void leftKarel()
  { karel.turnLeft();
    pnlMundo.setKarel( karel.getAvenue(), karel.getStreet(), karel.getCompass() );
    actualizar();
 // pnlDatos.setCodigo( "turnLeft( );" );
  }

/**
 * Req-6: Girar Karel a la derecha.
 */  
  public void rightKarel()
  { karel.turnRight();
    pnlMundo.setKarel( karel.getAvenue(), karel.getStreet(), karel.getCompass() );
    actualizar();
 // pnlDatos.setCodigo( "turnRight( );" );
  }
  
/**
 * Req-7: Karel recoge un beeper.
 */  
  public void pickBeeper()
  { karel.pickBeeper();
    actualizar();
    pnlDatos.setCodigo( "pickBeeper( );" );
  }

/**
 * Req-8: Karel pone un beeper.
 */  
  public void putBeeper()
  { karel.putBeeper();
    actualizar();
    pnlDatos.setCodigo( "putBeeper( );" );
  }
  
/**
 * Req-9: Karel oye o mira a su alrededor.
 */  
   public void questions( String txt, int index )
   { pnlDatos.setCodigo( txt );
     pnlDatos.setCuestionamientos( karel.question( index ) );
   }  

/**
 * Req-10: Carga el mundo en la interfaz.
 * @throws IOException 
 * @throws FileNotFoundException 
 * @throws ClassNotFoundException 
 */  
   public void loadWorld( ) throws FileNotFoundException, IOException, ClassNotFoundException

   { ObjectInputStream in = new ObjectInputStream ( new FileInputStream ( "files/world.dat" ) );
     while (true)
     { try
       { world = (World) in.readObject();	         
	   }
	   catch(EOFException e)
	   { break;}
	 } 
	 in.close();
	 
      // System.out.println("Ctrl.loadWorld.");
      // Dibuja el mundo en la interfaz, no icluye karel ni meta	 
	 String city[][] = world.getCity();
	 
	 for(int i=0; i<30; i++)
	  for(int j=0; j<30; j++)
		  if (city[i][j].equals("B"))
		  {   putBloks( i, j, "B" );
	          pnlMundo.paint(i, j);
		  }    
		  else
		  { if (city[i][j] != "")
				putBeeper( i, j, city[i][j] );	        
		  }  
   }

/**
 * Req-11: Guarda el mundo en un archivo de objetos.
 * @throws IOException 
 * @throws FileNotFoundException 
 */  
   public void saveWorld( ) throws FileNotFoundException, IOException
   { 
     ObjectOutputStream out = new ObjectOutputStream ( new FileOutputStream ( "files/world.dat" ) );
     out.writeObject ( world );
     out.close();
   } 
   
   /**
    * Req-12: Pintar area de busqueda.
    * @param ArrayList donde se pinte en el mundo
    */
   public void pintarBusqueda() {
	   if (temporal != null) {
		   pnlMundo.eliminarAreaDeBusqueda(temporal);
		   pnlMundo.pintarBusqueda(execution.recorrerGrafoParaPintar(karel.getAvenue()+","+karel.getStreet()));
		   temporal = execution.recorrerGrafoParaPintar(karel.getAvenue()+","+karel.getStreet());
	   }else {
		   pnlMundo.pintarBusqueda(execution.recorrerGrafoParaPintar(karel.getAvenue()+","+karel.getStreet()));
		   temporal = execution.recorrerGrafoParaPintar(karel.getAvenue()+","+karel.getStreet());  
	   }
	   
   }
}
