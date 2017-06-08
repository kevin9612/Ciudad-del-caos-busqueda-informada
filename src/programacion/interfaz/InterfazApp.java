package programacion.interfaz;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import programacion.controlador.Controlador;
import programacion.util.Util;


public class InterfazApp extends JFrame implements ActionListener
{   
 // Atributos de la clase
    private PanelMundo pnlMundo;
    private PanelSimulaciones pnlSimulaciones;
    private PanelCoordenadas pnlColumnas;
    private PanelCoordenadas pnlFilas;
    private PanelDatos pnlDatos;
    private JMenuBar mbrOpciones;

    private JMenu mnuAcerca;
    private JMenuItem mitAcerca;

    private JMenu mnuExtensiones;
    private JMenuItem mitMundo, mitInstrucciones, mitEjecutar, mitGuardar;
    private Controlador controlador;
    
 // Constructor
    public InterfazApp( Controlador controlador ) 
    { setTitle( "Chaos City" );  
   	  getContentPane( ).setLayout( null );
   
   // Enlaza el controlador	  
   	  this.controlador = controlador;
   	  
   // Instancia controles de menú
      mbrOpciones = new JMenuBar( );
      
      mnuExtensiones = new JMenu( );
      mnuExtensiones.setMnemonic( 'E' );
      mnuExtensiones.setText( "Extensions" );

      mitMundo = new JMenuItem();
      mitMundo.setMnemonic( 'W' );
      mitMundo.setText( "Load world." );
      mitMundo.addActionListener(this);
      
      mitGuardar = new JMenuItem();
      mitGuardar.setMnemonic( 'S' );
      mitGuardar.setText( "Save world." );
      mitGuardar.addActionListener(this);

      mitInstrucciones = new JMenuItem();
      mitInstrucciones.setMnemonic( 'L' );
      mitInstrucciones.setText( "Load program." );
      
      mitEjecutar = new JMenuItem();
      mitEjecutar.setMnemonic( 'E' );
      mitEjecutar.setText( "Execute." );
      
      mnuExtensiones.add( mitMundo );
      mnuExtensiones.add( mitGuardar );
   // mnuExtensiones.add( mitInstrucciones );
   // mnuExtensiones.add( mitEjecutar );
      mbrOpciones.add( mnuExtensiones );

   // Acerca de..
      mnuAcerca = new JMenu( );
      mnuAcerca.setMnemonic( 'A' );   	  
      mnuAcerca.setText( "About" );

      mitAcerca = new JMenuItem();
      mitAcerca.setMnemonic( 'G' );
      mitAcerca.setText( "Author: Giovanni Fajardo Utria." );
      
      mnuAcerca.add( mitAcerca );
      mbrOpciones.add( mnuAcerca );
                 
      setJMenuBar( mbrOpciones );          
      
   // Instancia los paneles    
      pnlMundo = new PanelMundo( controlador );
      pnlMundo.setBounds( 10, 40, 730, 490  );

      pnlColumnas = new PanelCoordenadas( 1, 30 );
      pnlColumnas.setBounds( 10, 5, 730, 30  );

      pnlFilas = new PanelCoordenadas( 30, 1 );
      pnlFilas.setBounds( 745, 40, 30, 490  );
      
      for( int i = 0; i<30; i++ )
           pnlColumnas.setSigla( i, String.valueOf( i ) );	  

      for( int i = 0; i<30; i++ )
           pnlFilas.setSigla( i, String.valueOf( i ) );	  
      
      pnlDatos = new PanelDatos();
   // pnlDatos.setBounds( 785, 10, 170, 580);
      pnlDatos.setBounds( 785, 10, 170, 522);
      
   // Instancia panel de Simulaciones.
      pnlSimulaciones = new PanelSimulaciones( controlador );      
      pnlSimulaciones.setBounds( 10, 530, 765, 60 );
   //                            L   T    R    B
        
      
   // Organizar el panel principal 
      getContentPane( ).add( pnlMundo );
      getContentPane( ).add( pnlColumnas );
      getContentPane( ).add( pnlFilas );
   // getContentPane( ).add( pnlSimulaciones );
      getContentPane( ).add( pnlDatos );
               
      setSize( 970, 590);     
      setResizable( false );
      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      
   // Conecta los objetos a controlar   
      controlador.conectar( pnlMundo, pnlSimulaciones, pnlDatos);
      
      Util.centrarVentana( this );
    }    

 // Manejador de eventos del menu 
    public void  actionPerformed(ActionEvent e)
    { if (e.getActionCommand().equals( "Load world." ) )
		try 
        { this.controlador.loadWorld();
		} 
        catch (FileNotFoundException e1)
		{ System.out.println("File Not Found Exception Error.");
		} 
        catch (ClassNotFoundException e1)
        { System.out.println("Class Not Found Exception Error.");
		} 
        catch (IOException e1)
        { System.out.println("IO Exception Error.");
		}
	else
      if (e.getActionCommand().equals( "Save world." ) )
		try 
        { this.controlador.saveWorld();
		} 
        catch (FileNotFoundException e1)
		{ System.out.println("File Not Found Exception Error.");
		} 
        catch (IOException e1)
        { System.out.println("IO Exception Error.");
		}
    }  
    
    
//  Ejecucion.		
	public static void main( String args[] )
	{ InterfazApp frmMain = new InterfazApp( new Controlador() );
	  frmMain.setVisible( true );	  
	}
		
}