package programacion.interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class PanelDatos extends JPanel
{
 // Constantes
    private static final String FACING = "data/Facing.gif";
	
 // Atributos de la clase 		
    private JTextField txtStreet, txtAvenue, txtFacing, txtBeepers, txtCuestionamientos;
    private JLabel lblStreet, lblAvenue, lblFacing, lblBeepers, lblCuestionamientos;
    private ImageIcon imgFacing;
    private JLabel lblImagen;
    private JScrollPane spnCodigo;
    private JTextArea txaCodigo;
    private KeyListener teclas;
    
    /**
     * Constructor
     */    
    public PanelDatos() 
    {   	
    // ..............................................( T, L, B, R ).............................................
       setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 0, 0 ), new TitledBorder( "" ) ) );
       setLayout( null );
    
    // Instancia atributos de la clase   
       imgFacing = new ImageIcon( FACING );
       lblImagen = new JLabel( "" );
       lblImagen.setIcon( imgFacing );
    // lblImagen.setIcon( loadImage( FACING ) );
    // lblImagen.setIcon( null );
       lblImagen.setBounds( 10, 10, 150, 150 );

       lblAvenue = new JLabel( "Avenue" ); lblAvenue.setBounds( 20, 170, 50, 10 );
       txtAvenue = new JTextField( "0" ); txtAvenue.setBounds( 20, 185, 50, 20 );
       txtAvenue.setEnabled( false ); txtAvenue.setHorizontalAlignment( JTextField.CENTER );

       lblStreet = new JLabel( "Street" ); lblStreet.setBounds( 90, 170, 50, 10 );
       txtStreet = new JTextField( "0" ); txtStreet.setBounds(90, 185, 50, 20 );
       txtStreet.setEnabled( false ); txtStreet.setHorizontalAlignment( JTextField.CENTER );

       lblFacing = new JLabel( "Facing" ); lblFacing.setBounds( 20, 210, 50, 15 );
       txtFacing = new JTextField( "North" ); txtFacing.setBounds( 20, 225, 50, 20 );
       txtFacing.setEnabled( false ); txtFacing.setHorizontalAlignment( JTextField.CENTER );

       lblBeepers = new JLabel( "Beepers" ); lblBeepers.setBounds( 90, 210, 50, 10 );
       txtBeepers = new JTextField( "0" ); txtBeepers.setBounds( 90, 225, 50, 20 );
       txtBeepers.setEnabled( false ); txtBeepers.setHorizontalAlignment( JTextField.CENTER );

       lblCuestionamientos = new JLabel( "Answers" ); lblCuestionamientos.setBounds( 20, 250, 120, 10 );
       txtCuestionamientos = new JTextField( "" ); txtCuestionamientos.setBounds( 20, 265, 120, 20 );
       txtCuestionamientos.setEnabled( false ); txtCuestionamientos.setHorizontalAlignment( JTextField.CENTER );
       
       txaCodigo = new JTextArea(); 
       txaCodigo.setEnabled( false );
       spnCodigo = new JScrollPane( txaCodigo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
    // spnCodigo.setBounds( 5, 290, 160, 280 );
       spnCodigo.setBounds( 5, 255, 160, 262 );
       
    // Capturando teclas presionadas 
       /*
       teclas = new AccionTeclas();
       addKeyListener(teclas);
       setFocusable(true);
       */
    // -----------------------------------------   
       
    // Agrega los atributos al panel   
       add( lblImagen );
       add( lblStreet ); add( txtStreet );
       add( lblAvenue ); add( txtAvenue );
       add( lblFacing ); add( txtFacing );
       add( lblBeepers ); add( txtBeepers );
    // add( lblCuestionamientos ); add( txtCuestionamientos );
       add( spnCodigo );
    }

    /**
     * Actualiza la avenida de ubicaci�n de karel en el visor del mundo
     * @param col
     */
    public void setAvenue ( int col )
    { txtAvenue.setText( String.valueOf( col ));    	   
    }

    /**
     * Actualiza la calle de ubicaci�n de karel en el visor del mundo
     * @param row
     */
    public void setStreet ( int row )
    { txtStreet.setText( String.valueOf( row ));   	   
    }
    
    /**
     * Actualiza la orientaci�n de karel en el visor del mundo
     * @param facing
     */
    public void setFacing ( String facing )
    { txtFacing.setText( facing );   	   
    }
    
    /**
     * Actualiza el n�mero de beepers de karel en el visor del mundo
     * @param beepers
     */
    public void setBeepers ( int beepers )
    { txtBeepers.setText( String.valueOf( beepers ));   	   
    }
    
    /**
     * Actualiza el area de c�digo, con la instrucci�n ejecutada
     * @param instruction
     */
    public void setCodigo ( String instruction )
    { txaCodigo.append( " " + instruction + "\n" );
    }
    
    public void setCuestionamientos( boolean answer )
    { txtCuestionamientos.setText( String.valueOf( answer ) );    	
    }
    
 // Esta rutina no se ha verificado - aun esta pendiente       
    private BufferedImage loadImage( String nombre )
    { URL url = null;
      try
      { url = getClass().getClassLoader().getResource( nombre );
        return ImageIO.read( url );
      }
      catch (Exception e)
      {  System.out.println("No se pudo cargar la imagen " + nombre +" de "+url);
    	 System.out.println("El error fue : " + e.getClass().getName() + " " + e.getMessage());
    	 System.exit(0);
    	 return null;
      }
    }
 // ----------------------------------------------------   
    
}