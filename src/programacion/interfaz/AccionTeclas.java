/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programacion.interfaz;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author giovanni-fajardo
 */
public class AccionTeclas implements KeyListener
{
    public AccionTeclas() {}
    
    @Override
    public void keyTyped(KeyEvent e) 
    {
    }

    @Override
    public void keyPressed(KeyEvent e) 
    { if (e.getKeyCode() >= 37 && e.getKeyCode() <= 40)
      { 
          switch (e.getKeyCode())
          { case 37: System.out.println("left..."); break;
            case 38: System.out.println("up....."); break;
            case 39: System.out.println("right.."); break;
            case 40: System.out.println("down..."); break;  
            default:             
          }            
      }
      else
        System.out.println("keyPressed .... " + e.getKeyCode());        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {        
    }
    
}
