package programacion.interfaz;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import programacion.controlador.Controlador;
 
/**
 * Panel para invocar futuros requerimientos.
 * @author  Giovanni Fajardo Utria
 * @version 1.0.0.
 */
public class PanelSimulaciones extends JPanel implements ActionListener
{
 // Atributos de la clase 		
    private JButton btnExtension1, btnExtension2, btnExtension3;
    private JButton btnExtension4, btnExtension5, btnExtension6;
    private JComboBox cboCuestiona;
    private Controlador controlador;
    
 // Constructor
    public PanelSimulaciones( Controlador controlador ) 
    {   	
    // ..............................................( T, L, B, R ).............................................
       setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 0, 0 ), new TitledBorder( " Abilitys " ) ) );
       setLayout( new FlowLayout(  ) );
	
    // Enlaza el Controlador
       this.controlador = controlador;
       
    // Instancia atributos de la clase   
       btnExtension1 = new JButton( "Karel" );
       btnExtension2 = new JButton( "Move" );
       btnExtension3 = new JButton( "Left" );
       btnExtension4 = new JButton( "Right" );
       btnExtension5 = new JButton( "Pick" );
       btnExtension6 = new JButton( "Put" );
       cboCuestiona = new JComboBox();
       
       cboCuestiona.addItem("          Questions           ");
       cboCuestiona.addItem("anyBeepersInBeeperBag( )");
       cboCuestiona.addItem("nextToABeepers( )");
       cboCuestiona.addItem("frontIsBlocked( )");
       cboCuestiona.addItem("rightIsBlocked( )");
       cboCuestiona.addItem("leftIsBlocked( )");
       cboCuestiona.addItem("frontIsClear( )");
       cboCuestiona.addItem("rightIsClear( )");
       cboCuestiona.addItem("leftIsClear( )");
       cboCuestiona.addItem("facingNorth( )");
       cboCuestiona.addItem("facingSouth( )");
       cboCuestiona.addItem("facingWest( )");
       cboCuestiona.addItem("facingEast( )");       
       cboCuestiona.setEnabled( false );
       
       btnExtension1.addActionListener( this ); btnExtension1.setEnabled( false );
       btnExtension2.addActionListener( this ); btnExtension2.setEnabled( false );
       btnExtension3.addActionListener( this ); btnExtension3.setEnabled( false );
       btnExtension4.addActionListener( this ); btnExtension4.setEnabled( false );
       btnExtension5.addActionListener( this ); btnExtension5.setEnabled( false );
       btnExtension6.addActionListener( this ); btnExtension6.setEnabled( false );

       cboCuestiona.addActionListener( this );
       
    // Agrega los atributos al panel   
       add( btnExtension1 ); add( btnExtension2 ); add( btnExtension3 );
       add( btnExtension4 ); add( btnExtension5 ); add( btnExtension6 );
       add( cboCuestiona );             
    } 
    
 // Metodos de la clase
      
    public void  actionPerformed(ActionEvent e)
    { if (e.getActionCommand().equals( "comboBoxChanged" ) )
       {  if ( cboCuestiona.getSelectedIndex() > 0 )
    	       controlador.questions( ( String )cboCuestiona.getSelectedItem(), cboCuestiona.getSelectedIndex() );
          cboCuestiona.setSelectedIndex( 0 );     
       }
      else	
       	if ( e.getActionCommand().equals( "Karel" ) )
    	 {   UbicarKarel frmKarel = new UbicarKarel( this.controlador, 0 ,0 );
       		 frmKarel.setVisible( true );
       		 
    	     btnExtension1.setEnabled( false );
    	     btnExtension2.setEnabled( true );
    	     btnExtension3.setEnabled( true );
    	     btnExtension4.setEnabled( true );
    	     btnExtension5.setEnabled( true );
    	     btnExtension6.setEnabled( true );
    	     cboCuestiona.setEnabled( true );
    	 }
       	else
          if ( e.getActionCommand().equals( "Move" ) )
           {   controlador.moverKarel();
           }
          else
            if ( e.getActionCommand().equals( "Left" ) )
             {   controlador.leftKarel();
             }
            else
              if ( e.getActionCommand().equals( "Right" ) )
               {   controlador.rightKarel();
               }
              else
                if ( e.getActionCommand().equals( "Pick" ) )
                 {   controlador.pickBeeper();
                 }
                else
                  if ( e.getActionCommand().equals( "Put" ) )
                   {   controlador.putBeeper();
                   }	            	  
    }
}