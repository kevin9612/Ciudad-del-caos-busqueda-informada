package programacion.interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import programacion.controlador.Controlador;
import programacion.util.Util;

public class UbicarKarel extends JDialog implements ActionListener
{
 // Atributos  
	private JLabel lblAvenue, lblStreet, lblCompass, lblBeepers, lbbusqueda, lbcoordenadas;
	private JComboBox cboCompass;
	private JSpinner spnAvenue, spnStreet, spnBeepers;
	private SpinnerNumberModel mdlAvenue, mdlStreet, mdlBeepers;
	private JButton btnAceptar;
	private Controlador controlador;	
	private JComboBox informacion;
	
	public UbicarKarel( Controlador controlador, int street, int avenue ) 
    { this.setTitle( "Chaos City" );  
   // this.getContentPane( ).setLayout( new GridLayout( 5, 2) );
   	  this.getContentPane( ).setLayout( new GridLayout( 5, 2) );

   // Enlaza el Controlador
      this.controlador = controlador;

      lblAvenue = new JLabel(" Avenue");
   	  lblStreet = new JLabel(" Street");
   	  lblCompass = new JLabel(" Compass");   	  
   	  lblBeepers = new JLabel(" Beepers");
      lbbusqueda = new JLabel("Search");
   	  
   	  mdlAvenue = new  SpinnerNumberModel(avenue, 0, 29, 1 );
   	  mdlStreet = new  SpinnerNumberModel(street, 0, 29, 1 );
   	  mdlBeepers = new  SpinnerNumberModel(0, 0, 99, 1 );
   	  
   	  spnAvenue = new JSpinner( mdlAvenue ); spnAvenue.setOpaque(true);
   	  spnStreet = new JSpinner( mdlStreet ); spnStreet.setOpaque(true);
   	  spnBeepers = new JSpinner( mdlBeepers ); spnBeepers.setOpaque(true);
   	  cboCompass = new JComboBox();   	  

   	  cboCompass.addItem("North"); cboCompass.addItem("South");
   	  cboCompass.addItem("West");  cboCompass.addItem("East");
   	
   	  informacion= new JComboBox();
   	  
   	  informacion.addItem("NorthEast");
   	  informacion.addItem("NorthWest");
   	  informacion.addItem("SouthEast");
   	  informacion.addItem("SouthWest");
   	  informacion.addItem("none");
   	  
   	  btnAceptar = new JButton("Aceptar");   	
   	  btnAceptar.addActionListener( this );
   	
   	  add(lblAvenue); add(spnAvenue);
   	  add(lblStreet); add(spnStreet);
      add(lblCompass); add(cboCompass);
      add(lbbusqueda); add(informacion);
   // add(lblBeepers); add(spnBeepers);
   	  add( btnAceptar );
   	  
   // this.setSize(180, 150);
   	  this.setSize(180, 130);
   	  this.setAlwaysOnTop( true );
   	  this.setResizable( false );
   	  this.setModal( true );
   // setDefaultCloseOperation( JDialog.EXIT_ON_CLOSE );
   	  
   	  Util.centrarVentana( this );
    }
	
	
//	Metodos de la clase
	
	private int getAvenue() 
	{ return mdlAvenue.getNumber().intValue();
	}
	
	private int getStreet() 
	{ return mdlStreet.getNumber().intValue();		 		
	}

	private int getBeepers() 
	{ return mdlBeepers.getNumber().intValue();		 		
	}
	
	private String getCompass() 
	{ return (String)cboCompass.getSelectedItem();
	}
	

	public String getInformacion() {
		return (String)informacion.getSelectedItem();
	}


	
    public void  actionPerformed( ActionEvent e )
    {  	if ( e.getActionCommand().equals( "Aceptar" ) )
    	{    controlador.ubicarKarel( getAvenue(), getStreet(), getBeepers(), getCompass(), getInformacion() ); 
    	     dispose( );
    	}
    }


}
