package programacion.interfaz;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import programacion.controlador.Controlador;
 
public class PanelMundo extends JPanel
{
 // Constantes
	private static final String KARELNORTH = "data/KarelNorth.gif";
	private static final String KARELSOUTH = "data/KarelSouth.gif";
	private static final String KARELWEST = "data/KarelWest.gif";
	private static final String KARELEAST = "data/KarelEast.gif";
	private static final String BLOCK = "data/Block.gif";
	private static final String BANDERA = "data/Bandera.gif";
	
 // Atributos de la clase 		
    private JLabel lblMundo[][];

    private Controlador controlador;
    
    private JTextField txtKarel;
    private ImageIcon imgBusqueda, imgKarelNorth, imgKarelSouth, imgKarelWest, imgKarelEast, imgKarelHome, imgBlock;
    private JLabel lblKarel;
    private boolean swKarel; 
    
    /**
     * Constructor
     */    
    public PanelMundo( Controlador controlador ) 
    {   	
    // ..............................................( T, L, B, R ).............................................
       setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 0, 0 ), new TitledBorder( "" ) ) );
       setLayout( new GridLayout(30, 30) );
    
    // Enlaza el Controlador
       this.controlador = controlador;
       
       
    // Flag de ubicación de karel
       this.swKarel = false;
       
    // Instancia atributos de la clase   
       lblMundo = new JLabel[30][30];
       
    // Objetos para representara a Karel y muros
       imgKarelNorth = new ImageIcon( KARELNORTH );
       imgKarelSouth = new ImageIcon( KARELSOUTH );
       imgKarelWest = new ImageIcon( KARELWEST );
       imgKarelEast = new ImageIcon( KARELEAST );
       imgBlock = new ImageIcon( BLOCK );
       imgBusqueda = new ImageIcon( BANDERA );
       
    // Agrega los atributos al panel   
       for(int i=0;i<30;i++)
    	for(int j=0;j<30;j++)
    	{ lblMundo[i][j] = new JLabel( "" );
    	  lblMundo[i][j].setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 0, 0 ), new TitledBorder( "" ) ) );
    	  lblMundo[i][j].setHorizontalAlignment( JLabel.CENTER );
    	  lblMundo[i][j].setVerticalAlignment( JLabel.CENTER );
    	  lblMundo[i][j].setEnabled( true );
    	  lblMundo[i][j].setFont( new Font("myFont", Font.BOLD, 13) );
    	  lblMundo[i][j].addMouseListener( new LabelClicMouse( i, j, lblMundo[i][j], controlador, this ) );
    	  add(lblMundo[i][j]);    	  
        }       
    }

    /**
     *  Cambia el estado de la ubicación de Karel en el mundo
     */
    public void setKarel(boolean swKarel)
    { this.swKarel = swKarel;    	
    }

    /**
     *  Retorna el estado de la ubicación de Karel en el mundo
     */
    public boolean getKarel()
    { return this.swKarel;    	
    }
    
    /**
     * Remueve los eventos del mouse sobre el mundo
     */
    public void removeLabelClicMouse()
    { for(int i=0;i<30;i++)
       for(int j=0;j<30;j++)
       {   lblMundo[i][j].removeMouseListener( lblMundo[i][j].getMouseListeners()[0] );
       }	
    }
    
    /**
     * Asigna un elemento en una esquina del Mundo
     * @param avenue Fila
     * @param street Columna
     * @param txt Texto
     */
    public void setText( int avenue, int street, String txt )
    { if ( txt.equals("0") ) txt = "";
      lblMundo[avenue][street].setText( txt );	
    }

    /**
     * Pinta el mundo leido de un archivo
     * @param avenue Fila
     * @param street Columna
     * @param txt Texto
     */
    public void paint( int avenue, int street )
    { lblMundo[avenue][street].setIcon( imgBlock );
    }
    
    /**
     * Pintar los puntos donde se realizó la busqueda
     * @param lista de coordenadas
     */
    public void pintarBusqueda(ArrayList<String> busqueda){
    	String actual = "";
    	String[] sp;
    	for (int i = 0; i < busqueda.size(); i++) {
			actual = busqueda.get(i);
			sp = actual.split(",");
			
			if (!lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("1") && !lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("2")
					&& !lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("H") && !lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("B")) {
				lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].setIcon(null);
				lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].setText(".");
			}
				
			
		}
    }
    
    /**
     * Eliminar area de busqueda
     */
    public void eliminarAreaDeBusqueda(ArrayList<String> eliminar) {
    	String actual = "";
    	String[] sp;
    	for (int i = 0; i < eliminar.size(); i++) {
			actual = eliminar.get(i);
			sp = actual.split(",");
			
				if (lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getIcon()==null && !lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("1")
						&& !lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("2") && !lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("B")
						&& !lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].getText().equals("H")) {					

					lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].setIcon(null);
					lblMundo[Integer.parseInt(sp[0])][Integer.parseInt(sp[1])].setText("");
					
				}
			
			
		}
    }
    
    
    /**
     * Ubica a karel en el mundo
     * @param avenue Fila
     * @param street Columna
     * @param compass Orientación
     */
    public void setKarel( int avenue, int street, String compass)
    {  if ( compass.equals("North") ) 	
            lblMundo[avenue][street].setIcon( imgKarelNorth );
       else
    	 if ( compass.equals("South") )  
              lblMundo[avenue][street].setIcon( imgKarelSouth );
    	 else
    	   if ( compass.equals("West") )	 
                lblMundo[avenue][street].setIcon( imgKarelWest );
    	   else
      		 if ( compass.equals("East") )  
                  lblMundo[avenue][street].setIcon( imgKarelEast );
    		 else
    		      lblMundo[avenue][street].setIcon( null );	
       swKarel = true;  
    } 

    
 // Esta rutina no se ha verificado - aun esta pendiente    
    private BufferedImage loadImage( String nombre )
    { URL url = null;
      try
      { url = getClass().getClassLoader().getResource(nombre);
        return ImageIO.read( url );
      }
      catch (Exception e)
      {  System.out.println("No se pudo cargar la imagen " + nombre +" de "+url);
    	 System.out.println("El error fue : " + e.getClass().getName() + " " + e.getMessage());
    	 System.exit(0);
    	 return null;
      }
    }
 // ------------------------------------------------------    
}

/**
 * Controlador de eventos del Mouse
 * @author Giovanni Fajardo Utria
 */
class LabelClicMouse extends MouseAdapter 
{  
   private static final String BLOCK = "data/Block.gif";
   private static final String KARELHOME = "data/KarelHome.gif";
   private static final String OBSTACLE = "data/Obstacle.gif";
   
   private JLabel label;
   private Controlador controlador;
   private int avenue, street;
   private ImageIcon imgBlock, imgKarelHome, imgObstacle;
   private PanelMundo pnlMundo;

   public LabelClicMouse( int avenue, int street, JLabel label, Controlador controlador, PanelMundo pnlMundo )
   { this.label = label;
     this.controlador = controlador;
     this.avenue = avenue; this.street = street;
     this.imgBlock = new ImageIcon( BLOCK );
     this.imgKarelHome = new ImageIcon( KARELHOME );
     this.imgObstacle = new ImageIcon( 	OBSTACLE );
     this.pnlMundo = pnlMundo;
   }	
   
   public void mouseClicked(MouseEvent evento)
   { if(!pnlMundo.getKarel())
     {if (evento.isShiftDown())
	  {   if (evento.isMetaDown())	// Shif+Botón derecho    	  
	      {   if ( (label.getText()).equals( "" ) && label.getIcon() == null  )
	    	  { label.setIcon( imgKarelHome );
                controlador.putHome( avenue, street, "H" );
	    	  }
	          else
	          { if ( label.getIcon() != null )
       	        {   label.setIcon( null );
	                controlador.putHome( avenue, street, "" );
	            }    	        	  
	          }
	      }
	      else // Shift+Botón Izquierdo
          {	 UbicarKarel frmKarel = new UbicarKarel( this.controlador, street, avenue );
             frmKarel.setVisible( true );
          }
	  }
      else 
      { if ( evento.isMetaDown() )  // botón derecho del ratón - Coloca muros
        {   if ( (label.getText()).equals( "" ) && label.getIcon() == null  )
             {    label.setIcon( imgBlock );
                  controlador.putBloks( avenue, street, "B" );
             }
            else
        	  if ( label.getIcon() != null )
        	   {   label.setIcon( null );
        	       controlador.putBloks( avenue, street, "" );
        	   }      
        	  else
        	    if (Integer.parseInt( label.getText()) > 1 )
        	     {  label.setText(String.valueOf(Integer.parseInt(label.getText())-1));
        	        controlador.putBeeper( avenue, street, label.getText() );	   
        	     }
        	    else 
        	       { label.setText( "" );
        	         controlador.putBeeper( avenue, street, label.getText() );
        	       }                         
        }
        else
        { if ( evento.isAltDown() )  // botón medio del ratón
          {   System.out.println("Botón medio del ratón...");
          }
          else  // botón izquierdo del ratón - Coloca Beepers
            if ( (label.getText()).equals( "" ) && label.getIcon() == null )
            {    label.setText("1");
                 controlador.putBeeper( avenue, street, label.getText() );
            }
            else 
      	      if ( label.getIcon() != null )
              {// Se ignora la accion.         		
              }
              else
        	    if (Integer.parseInt( label.getText()) < 99 )  
                {  label.setText(String.valueOf(Integer.parseInt(label.getText())+1));
        	       controlador.putBeeper( avenue, street, label.getText() );
                }
        }
      }
    }
    else
    { if (evento.isShiftDown())
      {   if (evento.isMetaDown())	// Shif+Botón derecho 
          {   // System.out.println("Shif+Botón derecho");	
          }
          else // Shift+Botón Izquierdo
          { if (label.getIcon() != null)
            {   if(label.getIcon().equals(imgObstacle) )
        	    {  label.setIcon( null );
 	               controlador.putObstacle( avenue, street, "" );
        	    }   
            }
            else
            {  label.setIcon( imgObstacle );
               controlador.putObstacle( avenue, street, "O" );
            }  
          }
      }
    }
   }
   
}