package programacion.mundo;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.JTextField;

/**
 * @(#)World.java
 * @author Giovanni Fajardo Utria
 * @version 1.00 2007/9/6
 */

public class World implements Serializable
{	
// Atributos de la clase
   private String city[][];
   private Karel karel;

 /**
  * Constructor
  */
    public World(  )
    { city = new String[30][30];
      for (int i=0; i<30; i++)
    	  for (int j=0; j<30; j++)
    		   city[i][j] = "";
    }

 /**
  * Asocia karel al mundo
  */
    public void setKarel( Karel karel )
    { this.karel = karel;    	
    }

    public Karel getKarel () {
    	return this.karel;
    }
    
 /**
  * Retorna el estado del mundo
  */
    public String[][] getCity()
    { return city;    	
    }
                    
 /**
  * Crea Bloks
  * @param avenue avenida
  * @param street calle
  * @param text   obstaculo
  */ 
    public void bloks( int avenue, int street, String text )
    { city[avenue][street] = text;
    }

 /**
  * Define la meta
  * @param avenue avenida
  * @param street calle
  * @param text   Meta
  */ 
    public void home( int avenue, int street, String text )
    { city[avenue][street] = text;
    }

 /**
  * Define obstáculos en ejecución
  * @param avenue avenida
  * @param street calle
  * @param text   Meta
  */ 
    public void obstacle( int avenue, int street, String text )
    { city[avenue][street] = text;
    }
    
 /**
  * Crea Beepers
  * @param avenue avenida
  * @param street calle
  * @param text   beepers 1,2,...n 
  */ 
    public void beepers( int avenue, int street, String text )
    { city[avenue][street] = text;    
    }

 /**
  * Retorna el contenido de la esquina donde esta karel
  * @param avenue avenida
  * @param street calle
  * @return citi[karel.getAvenue()][karel.getStreet()]
  */ 
    public String refresh( int avenue, int street)
    { return city[avenue][street];
    }
    
 /**
  * Retorna el número de Beepers en la esquina actual
  * @return citi[avenue][street] 
     */ 
    public int nextToABeepers(  )
    {  if ( city[karel.getAvenue()][karel.getStreet()].equals( "" ) )
    	    return 0;
       return Integer.parseInt( city[karel.getAvenue()][karel.getStreet()] );
    }	
 
 /**
  * Retorna el estado de una esquina
  * @return true / false 
  */ 
    public boolean nextBlocked(  )
    { int avenue = karel.getAvenue(), street = karel.getStreet();
      if (( karel.getCompass().equals("North") ) && ( karel.getAvenue() > 0 )) avenue = karel.getAvenue() - 1;
      else if (( karel.getCompass().equals("West") ) && ( karel.getStreet() > 0 )) street = karel.getStreet() - 1;
      else if (( karel.getCompass().equals("South") ) && ( karel.getAvenue() < 29 )) avenue = karel.getAvenue() + 1;
      else if (( karel.getCompass().equals("East") ) && ( karel.getStreet() < 29 )) street = karel.getStreet() + 1;
      else return true;
      
      if ( city[avenue][street].equals( "B" ) || city[avenue][street].equals( "O" ))
           return true; 
    		  
      return false;
    }

/**
 * ------------------------------------------------------------------------
 * Se agrego este método para identificar cuando se ha llegado a la meta
 * ------------------------------------------------------------------------    
 * Retorna si se ha llehado a la meta
 * @return true / false 
 */ 
   public boolean goal()
   { int avenue = karel.getAvenue(), street = karel.getStreet();
     if ( city[avenue][street].equals( "H" ) )
          return true;      
     return false;   
   }
}