package programacion.mundo;

/**
 * @(#)Karel.java
 * @author Giovanni Fajardo Utria
 * @version 1.00 2007/9/6
 */

public class Karel
{	
// Atributos de la clase
   private int beepers, avenue, street;
   private String compass;
   private World world;

/**
 * Constructor
 * @street 	street, columns
 * @avenue 	avenue, rows
 * @beepers	beepers
 * @compass	compass, N, S, W, E (North, Soudth, West(Occidente), Est(Oriente))
 */
    public Karel( int avenue, int street, int beepers, String compass, World world )
    { this.avenue = avenue; this.street = street; this.beepers = beepers; this.compass = compass;
      this.world = world; 
      
      System.out.println(avenue);
      System.out.println(street);
    }

/**
 * Karel gira en su eje hacia la izquierda 
 */     
    public void turnLeft()
    { if ( compass == "North" ) compass = "West";
      else if ( compass == "West" ) compass = "South";
      else if ( compass == "South" ) compass = "East";
      else compass = "North";                     
    }
   
/**
 * Gira tres veces a la izquierda
 * Karel gira en su eje hacia la derecha 
 */     
    public void turnRight()
    { turnLeft(); turnLeft(); turnLeft();
    }
    
/**
 * Karel se despalaza una cuadara alfrente
 * @return true / false
 */     
    public boolean move()
    { if ( frontIsClear() )
       {   if (( compass.equals("North") ) && ( avenue > 0 )) avenue--;
           else if (( compass.equals("West") ) && ( street > 0 )) street--;
           else if (( compass.equals("South") ) && ( avenue < 29 )) avenue++;
           else if (( compass.equals("East") ) && ( street < 29 )) street++;
           return true;           
       }
       return false;
    }

/**
 * Recoge un beeper de la esquina actual
 */     
    public void pickBeeper()
    { if ( nextToABeepers() )
      {    beepers++; 
           world.beepers( avenue, street, String.valueOf(world.nextToABeepers()-1) );
      }
    }

/**
 * Pone un beeper de la esquina actual
 */     
    public void putBeeper( )
    { if ( anyBeepersInBeepersBag() )
      {    beepers--; 
           world.beepers( avenue, street, String.valueOf(world.nextToABeepers()+1) );
      }
    }

// ----------------------------------------------------------------------
    
/**
 * Karel se cuestiona 
 * @return true / false
 */     
    public boolean question( int index )
    { 
      System.out.println("index = " + index);	
      switch ( index )
      { case 1: return anyBeepersInBeepersBag();
        case 2: return nextToABeepers();
        case 6: return frontIsClear();   
      }
      return false;	
    }

 /**
  * Cuestiona si existen beepersen la bolsa de Karel
  * @return true / false
  */     
    public boolean anyBeepersInBeepersBag()
    { return beepers > 0; 
    }

 /**
  * Cuestiona si existen beepersen la esquina actual
  * @return true / false
  */     
    public boolean nextToABeepers()
    { return world.nextToABeepers() > 0;
    }
    
 /**
  * Cuestiona si el frente esta libre
  * @return true / false
  */     
    public boolean frontIsClear()    
    { return !world.nextBlocked(); 
    }

// ----------------------------------------------------------------------    
 /**
  * Cuestiona si está en la meta
  * @return true / false
  */     
    public boolean goal()    
    { return world.goal(); 
    }        
// ----------------------------------------------------------------------
    
/**
 * Retorna la avenida actual
 * @return avenue
 */     
    public int getAvenue()
    { return avenue;
    }

/**
 * Retorna la calle actual
 * @return street
 */     
    public int getStreet()
    { return street;
    }

/**
 * Retorna la orientación actual
 * @return compass
 */     
    public String getCompass()
    { return compass;
    }
    
/**
 * Retorna el número de beepers en la bolsa de karel
 * @return beepers
 */    
    public int getBeepers()
    { return beepers;    	
    }
}
