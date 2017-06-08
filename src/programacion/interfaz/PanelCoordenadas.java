package programacion.interfaz;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

 
public class PanelCoordenadas extends JPanel
{
 // Atributos de la clase 		
	private JLabel lblCoordenadas[];
	
	/**
	 * Constructor por defecto
	 */ 
    public PanelCoordenadas( int row, int col ) 
    {   	
    // ..............................................( T, L, B, R ).............................................
       //setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 0, 0 ), new TitledBorder( "" ) ) );
       setLayout(new GridLayout(row, col));
	    
    // Instancia atributos de la clase   
       lblCoordenadas = new JLabel [30];
            
    // Agrega los atributos al panel   
       for (int i=0;i<30;i++)
    	{ lblCoordenadas[i] = new JLabel();
    	  lblCoordenadas[i].setHorizontalAlignment( JLabel.CENTER );
    	  lblCoordenadas[i].setEnabled( false );    	  
    	  add(lblCoordenadas[i]);    	
        }              
    } 

    /**
     * Asigna la sigla correspondiente a la coordenada
     * @param i  Coordenada
     * @param sgl Sigla
     */
    public void setSigla(int i, String sgl )
    { lblCoordenadas[i].setText( sgl );	
    }
}