package programacion.mundo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JOptionPane;

import programacion.controlador.Controlador;

public class Execution extends Thread implements Serializable
{
	private Controlador ctrl;
	private ArrayList lst;
	private World world;
	private Karel karel;
	private Grafo grafos;
	private Vertices vertices;
	private Boolean estado, movalternativos, devolver, numero; 
	private Queue<Vertices> cola; 
	private int temp1, temp2, contador, contador2, confin1, confin2;
	private HashMap<String, String> guardarcomppass;
	private HashMap<String, String> auxarray;
	private String info, comp, coordenadaMeta, aux, auxanterior, tiponumero;
	
	public Execution(World world, Controlador ctrl)
	{ this.ctrl = ctrl;
	this.lst = new ArrayList();
	this.world = world;


	}

	public void run()
	{ int make; boolean swmove = true, swgoal = false;
	while (!swgoal)
	{ while (swmove)
	{ try 
	{ if (lst.size() > 0)
	{   make = Integer.parseInt((String)lst.get(0));	  
	switch (make)
	{ case 0: swmove = ctrl.move(); break;
	case 1: ctrl.leftKarel(); break;
	case 2: ctrl.rightKarel(); break;
	}
	sleep(500);	          	          
	if (swmove)
		if (!ctrl.goal()) lst.remove(0);
		else
		{	 swmove = false;
		swgoal = true;
		}
	else lst.clear();	          
	}
	else
	{ swmove = false;
	}
	} 
	catch (InterruptedException e) 
	{}
	}  
	if (!swgoal)
		
	{ 
		if (!estado) {
			
			ctrl.setMessage( "Recalculating..." );
			this.karel = world.getKarel();
			findPath(getInfo(),karel.getAvenue(),karel.getStreet(),karel.getCompass()); swmove = true;
			
		}
		
	
	}
	}
	ctrl.setMessage( "Home..." ); 	
	}
	
	
	/*
	 * Encontrar el camino a la meta, utilizando busqueda en anchura. 
	 */
	public void findPath(String informacion, int i, int j, String compass)
	{ String city[][] = new String[30][30]; 
	city = world.getCity();

	guardarcomppass = new HashMap<>();
	auxarray = new HashMap<>();
	
	grafos = new Grafo();
	cola = new LinkedList<>();

	temp1=Serialization.copy(i);
	temp2=Serialization.copy(j);
	
	tiponumero="";

	String auxcompass= Serialization.copy(compass);

	String coor = ""+temp1+","+temp2;
	grafos.addVertice(coor);

	numero=false;

	switch (informacion) {
	case "NorthEast":

		if (city[temp1][temp2]!="H" && city[temp1][temp2]!=null) {

			coor=""+temp1+","+temp2;
			grafos.addVertice(coor);
			cola.add(grafos.getVertice(coor));
			guardarcomppass.put(coor, auxcompass);
			estado=false;

		}
		

		while (!estado) {			
			if (cola.peek().getDato().equals("11,16")) {
				System.out.println("");
			}
			contador=0;
			contador2=0;
			confin1=0;
			confin2=0;
			devolver=false;			
			movalternativos=false;			
			auxcompass=consularCompass(cola.peek().getDato());
			
			int a = temp1;
			int b = temp1;
			int c = temp2;
			int d = temp2;
			
			if ( a>0 ) {
			
				a--;
			}else{
				
				confin1++;
			}
			if ( b<29) {
				b++;
			}else{
				
				confin2++;
				
			}	
			if ( c>0 ) {
				c--;
			}else{
				
				confin2++;
			}
			if ( d<29 ) {
				d++;
			}else{
				
				confin1++;
			}

			
			if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("2"))) {
			
				numero=false;
				auxanterior = cola.peek().getDato();				
				//cola.clear();			
				
				if (auxcompass.equals("North")) {
					
					if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						auxcompass="West";
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "1,0");
						
						if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
							if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
							
						}
					}else if (city[temp1][c].equalsIgnoreCase("H")) {
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("East")){
					
					if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						auxcompass="North";
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
							if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
							
						}
					}else if (city[a][temp2].equalsIgnoreCase("H")) {
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("South")){
					
					if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
						auxcompass="East";
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));					
						grafos.addArista(auxanterior, coor, "1,0");						
						
						if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][d].equalsIgnoreCase("H")) {
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("West")) {
					
					if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
						auxcompass="South";
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						
						
						if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[b][temp2].equalsIgnoreCase("H")) {
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				}
				
				guardarcomppass.put(coor, auxcompass);
			}else{
				
				if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("1"))) {
				

					numero=false;
					auxanterior = cola.peek().getDato();				
					//cola.clear();			
					
					if (auxcompass.equals("North")) {
						
						if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							auxcompass="East";
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
								grafos.addArista(auxanterior, coor, "2,0");
							
							if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
								if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][d].equalsIgnoreCase("H")) {
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("East")){
					
						if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							auxcompass="South";
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
								if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[b][temp2].equalsIgnoreCase("H")) {
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("South")){
						
						if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
							auxcompass="West";
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));					
							grafos.addArista(auxanterior, coor, "2,0");						
							
							if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
								
								if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][c].equalsIgnoreCase("H")) {
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("West")) {
						
						if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
							auxcompass="North";
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							
							
							if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
								
								if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[a][temp2].equalsIgnoreCase("H")) {
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					}
					
					guardarcomppass.put(coor, auxcompass);	
					
				}else{
			
			if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {				

				coor=""+a+","+temp2;
				
				if (grafos.getVertice(coor)==null) {

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
						if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
					
				}else{
					contador2++;
					
				}		

			}else {
				if (city[a][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = a+","+temp2;					
					grafos.addVertice(a+","+temp2);
					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "1,1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					break;
				}else{					
				
					contador++;
					devolver=true;
					
					
				}
				
			}
			separar(cola.peek().getDato());
			auxcompass=consularCompass(cola.peek().getDato());
			
			if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {

				coor=""+temp1+","+d;

				if (grafos.getVertice(coor)==null) {			

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
					}
					auxcompass = "East";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
						
						if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
				}else{		
				
						contador2++;					
						
						if (devolver) {
							
							contador++;
						}
				
				}
			}else {

				if (city[temp1][d].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = temp1+","+d;
					
					grafos.addVertice(temp1+","+d);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "2,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "2,2,0");
					}
					auxcompass = "East";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}else{
					
				contador++;
				
				if (contador2==1) {
					
					contador++;
				}
				
				}
			}

			if (contador >= 2 || (cola.size()==1 && contador2==2 && confin1<2 && confin2<2)) {
								
				separar(cola.peek().getDato());
				auxcompass=consularCompass(cola.peek().getDato());
				
				if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {

					coor=""+b+","+temp2;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						}
						auxcompass = "South";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
					separar(cola.peek().getDato());
					auxcompass=consularCompass(cola.peek().getDato());
					
				}else if (city[b][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = b+","+temp2;
					
					grafos.addVertice(coordenadaMeta);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
					}
					auxcompass = "South";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}
				if(city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")){

					coor=""+temp1+","+c;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "0");
						}
						auxcompass = "West";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
							
							if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
				
			}else if(city[temp1][c].equalsIgnoreCase("H")){
				System.out.println("hemos llegado");
				coordenadaMeta = temp1+","+c;
				
				grafos.addVertice(coordenadaMeta);

				if (auxcompass.equals("North")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
				} else if(auxcompass.equals("East")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,1,0");
				} else if(auxcompass.equals("South")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
				} else if(auxcompass.equals("West")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
				}
				auxcompass = "West";
				guardarcomppass.put(coor, auxcompass);
				
				break;
			}
			}
			
			
				}
			}
			
			cola.poll();
			
			if (cola.isEmpty()) {
				
				System.out.println("no hay camino");
				estado=true;
			}
			
			
			if (cola.peek()!=null) {
				
				separar(cola.peek().getDato());	
				
			}		
		}

		

		break;

	case "NorthWest":
		
		if (city[temp1][temp2]!="H" && city[temp1][temp2]!=null) {

			coor=""+temp1+","+temp2;
			grafos.addVertice(coor);
			cola.add(grafos.getVertice(coor));
			guardarcomppass.put(coor, auxcompass);
			estado=false;

		}
		

		while (!estado) {			
			
			contador=0;
			contador2=0;
			confin1=0;
			confin2=0;
			devolver=false;			
			movalternativos=false;			
			auxcompass=consularCompass(cola.peek().getDato());
			
			int a = temp1;
			int b = temp1;
			int c = temp2;
			int d = temp2;
			
			if ( a>0 ) {
			
				a--;
			}else{
				
				confin1++;
			}
			if ( b<29) {
				b++;
			}else{
				
				confin2++;
				
			}	
			if ( c>0 ) {
				c--;
			}else{
				
				confin2++;
			}
			if ( d<29 ) {
				d++;
			}else{
				
				confin1++;
			}

			
			if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("2"))) {
			
				numero=false;
				auxanterior = cola.peek().getDato();				
				//cola.clear();			
				
				if (auxcompass.equals("North")) {
					
					if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						auxcompass="West";
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "1,0");
						
						if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
							if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][c].equalsIgnoreCase("H")) {
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("East")){
					
					if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						auxcompass="North";
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
							if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[a][temp2].equalsIgnoreCase("H")) {
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("South")){
					
					if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
						auxcompass="East";
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));					
						grafos.addArista(auxanterior, coor, "1,0");						
						
						if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][d].equalsIgnoreCase("H")) {
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("West")) {
					
					if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
						auxcompass="South";
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						
						
						if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[b][temp2].equalsIgnoreCase("H")) {
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				}
				
				guardarcomppass.put(coor, auxcompass);
			}else{
				
				if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("1"))) {
				

					numero=false;
					auxanterior = cola.peek().getDato();				
					//cola.clear();			
					
					if (auxcompass.equals("North")) {
						auxcompass="East";
						if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
								grafos.addArista(auxanterior, coor, "2,0");
							
							if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
								if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][d].equalsIgnoreCase("H")) {
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("East")){
						
						if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							auxcompass="South";
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
								if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[b][temp2].equalsIgnoreCase("H")) {
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("South")){
						
						if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
							auxcompass="West";
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));					
							grafos.addArista(auxanterior, coor, "2,0");						
							
							if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
								
								if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][c].equalsIgnoreCase("H")) {
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("West")) {
						
						if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
							auxcompass="North";
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							
							
							if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
								
								if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[a][temp2].equalsIgnoreCase("H")) {
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					}
					
					guardarcomppass.put(coor, auxcompass);	
					
				}else{
			
			if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {				

				coor=""+a+","+temp2;
				
				if (grafos.getVertice(coor)==null) {

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
						if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
					
				}else{
					contador2++;
					
				}		

			}else {
				if (city[a][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = a+","+temp2;					
					grafos.addVertice(a+","+temp2);
					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "1,1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					break;
				}else{					
				
					contador++;
					devolver=true;
					
					
				}
				
			}
			separar(cola.peek().getDato());
			auxcompass=consularCompass(cola.peek().getDato());
			
			if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {

				coor=""+temp1+","+c;

				if (grafos.getVertice(coor)==null) {			

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "1,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "0");
					}
					auxcompass = "West";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
						if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
				}else{		
				
						contador2++;					
						
						if (devolver) {
							
							contador++;
						}
				
				}
			}else {

				if (city[temp1][c].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = temp1+","+c;
					
					grafos.addVertice(temp1+","+c);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "1,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "1,1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "0");
					}
					auxcompass = "West";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}else{
					
				contador++;
				
				if (contador2==1) {
					
					contador++;
				}
				
				}
			}

			if (contador >= 2 || (cola.size()==1 && contador2==2 && confin1<2 && confin2<2)) {
								
				separar(cola.peek().getDato());
				auxcompass=consularCompass(cola.peek().getDato());
				
				if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {

					coor=""+b+","+temp2;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						}
						auxcompass = "South";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
					separar(cola.peek().getDato());
					auxcompass=consularCompass(cola.peek().getDato());
					
				}else if (city[b][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = b+","+temp2;
					
					grafos.addVertice(coordenadaMeta);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
					}
					auxcompass = "South";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}
				if(city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")){

					coor=""+temp1+","+d;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						}
						auxcompass = "East";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
				
			}else if(city[temp1][d].equalsIgnoreCase("H")){
				System.out.println("hemos llegado");
				coordenadaMeta = temp1+","+d;
				
				grafos.addVertice(coordenadaMeta);

				if (auxcompass.equals("North")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
				} else if(auxcompass.equals("East")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
				} else if(auxcompass.equals("South")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
				} else if(auxcompass.equals("West")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
				}
				auxcompass = "East";
				guardarcomppass.put(coor, auxcompass);
				
				break;
			}
			}
			
			
				}
			}
			
			cola.poll();
			
			if (cola.isEmpty()) {
				
				System.out.println("no hay camino");
				estado=true;
			}
			
			
			if (cola.peek()!=null) {
				
				separar(cola.peek().getDato());	
				
			}		
		}

		
		
		break;

	case "SouthEast":
		

		if (city[temp1][temp2]!="H" && city[temp1][temp2]!=null) {

			coor=""+temp1+","+temp2;
			grafos.addVertice(coor);
			cola.add(grafos.getVertice(coor));
			guardarcomppass.put(coor, auxcompass);
			estado=false;

		}
		

		while (!estado) {			
			
			contador=0;
			contador2=0;
			confin1=0;
			confin2=0;
			devolver=false;			
			movalternativos=false;			
			auxcompass=consularCompass(cola.peek().getDato());
			
			int a = temp1;
			int b = temp1;
			int c = temp2;
			int d = temp2;
			
			if ( a>0 ) {
			
				a--;
			}else{
				
				confin1++;
			}
			if ( b<29) {
				b++;
			}else{
				
				confin2++;
				
			}	
			if ( c>0 ) {
				c--;
			}else{
				
				confin2++;
			}
			if ( d<29 ) {
				d++;
			}else{
				
				confin1++;
			}

			
			if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("2"))) {
			
				numero=false;
				auxanterior = cola.peek().getDato();				
				//cola.clear();			
				
				if (auxcompass.equals("North")) {
					
					if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						auxcompass="West";
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "1,0");
						
						if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
							if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][c].equalsIgnoreCase("H")) {
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("East")){
					
					if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						auxcompass="North";
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
							if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[a][temp2].equalsIgnoreCase("H")) {
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("South")){
					
					if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
						auxcompass="East";
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));					
						grafos.addArista(auxanterior, coor, "1,0");						
						
						if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][d].equalsIgnoreCase("H")) {
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("West")) {
					
					if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
						auxcompass="South";
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						
						
						if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[b][temp2].equalsIgnoreCase("H")) {
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				}
				
				guardarcomppass.put(coor, auxcompass);
			}else{
				
				if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("1"))) {
				

					numero=false;
					auxanterior = cola.peek().getDato();				
					//cola.clear();			
					
					if (auxcompass.equals("North")) {
						
						if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							auxcompass="East";
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
								grafos.addArista(auxanterior, coor, "2,0");
							
							if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
								if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][d].equalsIgnoreCase("H")) {
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("East")){
						
						if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							auxcompass="South";
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
								if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[b][temp2].equalsIgnoreCase("H")) {
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("South")){
						
						if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
							auxcompass="West";
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));					
							grafos.addArista(auxanterior, coor, "2,0");						
							
							if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
								
								if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][c].equalsIgnoreCase("H")) {
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("West")) {
						
						if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
							auxcompass="North";
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							
							
							if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
								
								if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[a][temp2].equalsIgnoreCase("H")) {
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					}
					
					guardarcomppass.put(coor, auxcompass);	
					
				}else{
			
			if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {				

				coor=""+b+","+temp2;
				
				if (grafos.getVertice(coor)==null) {

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "1,0");
					}
					auxcompass = "South";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
						
						if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
					
				}else{
					contador2++;
					
				}		

			}else {
				if (city[b][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = b+","+temp2;					
					grafos.addVertice(b+","+temp2);
					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "1,1,0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "2,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "1,0");
					}
					auxcompass = "South";
					guardarcomppass.put(coor, auxcompass);
					break;
				}else{					
				
					contador++;
					devolver=true;
					
					
				}
				
			}
			separar(cola.peek().getDato());
			auxcompass=consularCompass(cola.peek().getDato());
			
			if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {

				coor=""+temp1+","+d;

				if (grafos.getVertice(coor)==null) {			

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
					}
					auxcompass = "East";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
						
						if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
				}else{		
				
						contador2++;					
						
						if (devolver) {
							
							contador++;
						}
				
				}
			}else {

				if (city[temp1][d].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = temp1+","+d;
					
					grafos.addVertice(temp1+","+d);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "2,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+d, "2,2,0");
					}
					auxcompass = "East";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}else{
					
				contador++;
				
				if (contador2==1) {
					
					contador++;
				}
				
				}
			}

			if (contador >= 2 || (cola.size()==1 && contador2==2 && confin1<2 && confin2<2)) {
								
				separar(cola.peek().getDato());
				auxcompass=consularCompass(cola.peek().getDato());
				
				if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {

					coor=""+a+","+temp2;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						}
						auxcompass = "North";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
							
							if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
					separar(cola.peek().getDato());
					auxcompass=consularCompass(cola.peek().getDato());
					
				}else if (city[a][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = a+","+temp2;
					
					grafos.addVertice(coordenadaMeta);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}
				if(city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")){

					coor=""+temp1+","+c;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "0");
						}
						auxcompass = "West";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
							
							if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
				
			}else if(city[temp1][c].equalsIgnoreCase("H")){
				System.out.println("hemos llegado");
				coordenadaMeta = temp1+","+c;
				
				grafos.addVertice(coordenadaMeta);

				if (auxcompass.equals("North")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
				} else if(auxcompass.equals("East")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,1,0");
				} else if(auxcompass.equals("South")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
				} else if(auxcompass.equals("West")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
				}
				auxcompass = "West";
				guardarcomppass.put(coor, auxcompass);
				
				break;
			}
			}
			
			
				}
			}
			
			cola.poll();
			
			if (cola.isEmpty()) {
				
				System.out.println("no hay camino");
				estado=true;
			}
			
			
			if (cola.peek()!=null) {
				
				separar(cola.peek().getDato());	
				
			}		
		}
		break;

	case "SouthWest":


		if (city[temp1][temp2]!="H" && city[temp1][temp2]!=null) {

			coor=""+temp1+","+temp2;
			grafos.addVertice(coor);
			cola.add(grafos.getVertice(coor));
			guardarcomppass.put(coor, auxcompass);
			estado=false;

		}
		

		while (!estado) {			
			
			if (cola.peek().getDato().equals("8,15")) {
				System.out.println();
			}
			
			contador=0;
			contador2=0;
			confin1=0;
			confin2=0;
			devolver=false;			
			movalternativos=false;			
			auxcompass=consularCompass(cola.peek().getDato());
			
			int a = temp1;
			int b = temp1;
			int c = temp2;
			int d = temp2;
			
			if ( a>0 ) {
			
				a--;
			}else{
				
				confin1++;
			}
			if ( b<29) {
				b++;
			}else{
				
				confin2++;
				
			}	
			if ( c>0 ) {
				c--;
			}else{
				
				confin2++;
			}
			if ( d<29 ) {
				d++;
			}else{
				
				confin1++;
			}

			
			if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("2"))) {
			
				numero=false;
				auxanterior = cola.peek().getDato();				
				//cola.clear();			
				
				if (auxcompass.equals("North")) {
					
					if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						auxcompass="West";
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "1,0");
						
						if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
							if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][c].equalsIgnoreCase("H")) {
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("East")){
					
					if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						auxcompass="North";
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
							if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[a][temp2].equalsIgnoreCase("H")) {
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("South")){
					
					if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
						auxcompass="East";
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));					
						grafos.addArista(auxanterior, coor, "1,0");						
						
						if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][d].equalsIgnoreCase("H")) {
						coor=""+d+","+temp1;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("West")) {
					
					if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
						auxcompass="South";
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						
						
						if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[b][temp2].equalsIgnoreCase("H")) {
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				}
				
				guardarcomppass.put(coor, auxcompass);
			}else{
				
				if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("1"))) {
				

					numero=false;
					auxanterior = cola.peek().getDato();				
					//cola.clear();			
					
					if (auxcompass.equals("North")) {
						
						if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							auxcompass="East";
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
								grafos.addArista(auxanterior, coor, "2,0");
							
							if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
								if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][d].equalsIgnoreCase("H")) {
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("East")){
						
						if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							auxcompass="South";
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
								if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[b][temp2].equalsIgnoreCase("H")) {
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("South")){
						
						if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
							auxcompass="West";
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));					
							grafos.addArista(auxanterior, coor, "2,0");						
							
							if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
								
								if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][c].equalsIgnoreCase("H")) {
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("West")) {
						
						if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
							auxcompass="North";
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							
							
							if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
								
								if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[a][temp2].equalsIgnoreCase("H")) {
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					}
					
					guardarcomppass.put(coor, auxcompass);	
					
				}else{
			
			if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {				

				coor=""+b+","+temp2;
				
				if (grafos.getVertice(coor)==null) {

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "1,0");
					}
					auxcompass = "South";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
						
						if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
					
				}else{
					contador2++;
					
				}		

			}else {
				if (city[b][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = b+","+temp2;					
					grafos.addVertice(b+","+temp2);
					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "2,2,0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "2,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), b+","+temp2, "1,0");
					}
					auxcompass = "South";
					guardarcomppass.put(coor, auxcompass);
					break;
				}else{					
				
					contador++;
					devolver=true;
					
					
				}
				
			}
			separar(cola.peek().getDato());
			auxcompass=consularCompass(cola.peek().getDato());
			
			if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {

				coor=""+temp1+","+c;

				if (grafos.getVertice(coor)==null) {			

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "1,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "0");
					}
					auxcompass = "West";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
						if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
				}else{		
				
						contador2++;					
						
						if (devolver) {
							
							contador++;
						}
				
				}
			}else {

				if (city[temp1][c].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = temp1+","+c;
					
					grafos.addVertice(temp1+","+c);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "1,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "1,1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "0");
					}
					auxcompass = "West";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}else{
					
				contador++;
				
				if (contador2==1) {
					
					contador++;
				}
				
				}
			}

			if (contador >= 2 || (cola.size()==1 && contador2==2 && confin1<2 && confin2<2)) {
								
				separar(cola.peek().getDato());
				auxcompass=consularCompass(cola.peek().getDato());
				
				if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {

					coor=""+a+","+temp2;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						}
						auxcompass = "North";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
							
							if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
					separar(cola.peek().getDato());
					auxcompass=consularCompass(cola.peek().getDato());
					
				}else if (city[a][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = a+","+temp2;
					
					grafos.addVertice(coordenadaMeta);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}
				if(city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")){

					coor=""+temp1+","+d;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						}
						auxcompass = "East";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
				
			}else if(city[temp1][d].equalsIgnoreCase("H")){
				System.out.println("hemos llegado");
				coordenadaMeta = temp1+","+d;
				
				grafos.addVertice(coordenadaMeta);

				if (auxcompass.equals("North")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
				} else if(auxcompass.equals("East")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
				} else if(auxcompass.equals("South")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
				} else if(auxcompass.equals("West")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
				}
				auxcompass = "East";
				guardarcomppass.put(coor, auxcompass);
				
				break;
			}
			}
			
			
				}
			}
			
			cola.poll();
			
			if (cola.isEmpty()) {
				
				System.out.println("no hay camino");
				estado=true;
			}
			
			
			if (cola.peek()!=null) {
				
				separar(cola.peek().getDato());	
				
			}		
		}

		

		
		break;

	default:
		

		if (city[temp1][temp2]!="H" && city[temp1][temp2]!=null) {

			coor=""+temp1+","+temp2;
			grafos.addVertice(coor);
			cola.add(grafos.getVertice(coor));
			guardarcomppass.put(coor, auxcompass);
			estado=false;

		}
		

		while (!estado) {			
			
			contador=0;
			contador2=0;
			confin1=0;
			confin2=0;
			devolver=false;			
			movalternativos=false;			
			auxcompass=consularCompass(cola.peek().getDato());
			
			int a = temp1;
			int b = temp1;
			int c = temp2;
			int d = temp2;
			
			if ( a>0 ) {
			
				a--;
			}else{
				
				confin1++;
			}
			if ( b<29) {
				b++;
			}else{
				
				confin2++;
				
			}	
			if ( c>0 ) {
				c--;
			}else{
				
				confin2++;
			}
			if ( d<29 ) {
				d++;
			}else{
				
				confin1++;
			}

			
			if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("2"))) {
			
				numero=false;
				auxanterior = cola.peek().getDato();				
				//cola.clear();			
				
				if (auxcompass.equals("North")) {
					
					if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						auxcompass="West";
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "1,0");
						
						if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
							if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][c].equalsIgnoreCase("H")) {
						coor=""+temp1+","+c;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("East")){
					
					if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						auxcompass="North";
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
							if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[a][temp2].equalsIgnoreCase("H")) {
						coor=""+a+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("South")){
					
					if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
						auxcompass="East";
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));					
						grafos.addArista(auxanterior, coor, "1,0");						
						
						if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[temp1][d].equalsIgnoreCase("H")) {
						coor=""+temp1+","+d;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				} else if(auxcompass.equals("West")) {
					
					if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
						auxcompass="South";
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						
						
						if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}else if (city[b][temp2].equalsIgnoreCase("H")) {
						coor=""+b+","+temp2;
						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));
						grafos.addArista(auxanterior, coor, "1,0");
						coordenadaMeta = coor;
						break;
					}
				}
				
				guardarcomppass.put(coor, auxcompass);
			}else{
				
				if (auxarray.containsKey(cola.peek().getDato()) && (auxarray.get(cola.peek().getDato()).equalsIgnoreCase("1"))) {
				

					numero=false;
					auxanterior = cola.peek().getDato();				
					//cola.clear();			
					
					if (auxcompass.equals("North")) {
						
						if (city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							auxcompass="East";
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
								grafos.addArista(auxanterior, coor, "2,0");
							
							if (city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
								if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][d].equalsIgnoreCase("H")) {
							coor=""+temp1+","+d;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("East")){
						
						if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							auxcompass="South";
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							if (city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
								if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[b][temp2].equalsIgnoreCase("H")) {
							coor=""+b+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("South")){
						
						if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
							auxcompass="West";
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));					
							grafos.addArista(auxanterior, coor, "2,0");						
							
							if (city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
								
								if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[temp1][c].equalsIgnoreCase("H")) {
							coor=""+temp1+","+c;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					} else if(auxcompass.equals("West")) {
						
						if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
							auxcompass="North";
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							
							
							if (city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
								
								if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
								if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
								
								numero=true;
								aux=coor;
								auxarray.put(aux, tiponumero);
							}
						}else if (city[a][temp2].equalsIgnoreCase("H")) {
							coor=""+a+","+temp2;
							grafos.addVertice(coor);
							cola.add(grafos.getVertice(coor));
							grafos.addArista(auxanterior, coor, "2,0");
							coordenadaMeta = coor;
							break;
						}
					}
					
					guardarcomppass.put(coor, auxcompass);	
					
				}else{
			
			if (city[a][temp2].equalsIgnoreCase("") || city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {				

				coor=""+a+","+temp2;
				
				if (grafos.getVertice(coor)==null) {

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[a][temp2].equalsIgnoreCase("1") || city[a][temp2].equalsIgnoreCase("2")) {
						
						if (city[a][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[a][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
					
				}		

			}else {
				if (city[a][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = a+","+temp2;					
					grafos.addVertice(a+","+temp2);
					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "1,1,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), a+","+temp2, "2,0");
					}
					auxcompass = "North";
					guardarcomppass.put(coor, auxcompass);
					break;
				}
				
			}
			separar(cola.peek().getDato());
			auxcompass=consularCompass(cola.peek().getDato());
			
			if (city[temp1][c].equalsIgnoreCase("") || city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {

				coor=""+temp1+","+c;

				if (grafos.getVertice(coor)==null) {			

					grafos.addVertice(coor);
					cola.add(grafos.getVertice(coor));

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coor, "1,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coor, "1,1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coor, "2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coor, "0");
					}
					auxcompass = "West";
					guardarcomppass.put(coor, auxcompass);
					
					if ( city[temp1][c].equalsIgnoreCase("1") || city[temp1][c].equalsIgnoreCase("2")) {
						
						if (city[temp1][c].equalsIgnoreCase("1") ) {tiponumero="1";}
						if (city[temp1][c].equalsIgnoreCase("2") ) {tiponumero="2";}
						
						numero=true;
						aux=coor;
						auxarray.put(aux, tiponumero);
					}
				}
			}else {

				if (city[temp1][c].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = temp1+","+c;
					
					grafos.addVertice(temp1+","+c);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "1,0");					
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "1,1,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "2,0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), temp1+","+c, "0");
					}
					auxcompass = "West";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}
			}		
								
				separar(cola.peek().getDato());
				auxcompass=consularCompass(cola.peek().getDato());
				
				if (city[b][temp2].equalsIgnoreCase("") || city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {

					coor=""+b+","+temp2;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						}
						auxcompass = "South";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[b][temp2].equalsIgnoreCase("1") || city[b][temp2].equalsIgnoreCase("2")) {
							
							if (city[b][temp2].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[b][temp2].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
					separar(cola.peek().getDato());
					auxcompass=consularCompass(cola.peek().getDato());
					
				}else if (city[b][temp2].equalsIgnoreCase("H")) {
					System.out.println("hemos llegado");
					coordenadaMeta = b+","+temp2;
					
					grafos.addVertice(coordenadaMeta);

					if (auxcompass.equals("North")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
					} else if(auxcompass.equals("East")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
					} else if(auxcompass.equals("South")){
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
					} else if(auxcompass.equals("West")) {
						grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
					}
					auxcompass = "South";
					guardarcomppass.put(coor, auxcompass);
					
					break;
				}
				if(city[temp1][d].equalsIgnoreCase("") || city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")){

					coor=""+temp1+","+d;

					if (grafos.getVertice(coor)==null) {

						grafos.addVertice(coor);
						cola.add(grafos.getVertice(coor));

						if (auxcompass.equals("North")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,0");
						} else if(auxcompass.equals("East")){
							grafos.addArista(cola.peek().getDato(), coor, "0");
						} else if(auxcompass.equals("South")){
							grafos.addArista(cola.peek().getDato(), coor, "1,0");
						} else if(auxcompass.equals("West")) {
							grafos.addArista(cola.peek().getDato(), coor, "2,2,0");
						}
						auxcompass = "East";
						guardarcomppass.put(coor, auxcompass);
						
						if ( city[temp1][d].equalsIgnoreCase("1") || city[temp1][d].equalsIgnoreCase("2")) {
							
							if (city[temp1][d].equalsIgnoreCase("1") ) {tiponumero="1";}
							if (city[temp1][d].equalsIgnoreCase("2") ) {tiponumero="2";}
							
							numero=true;
							aux=coor;
							auxarray.put(aux, tiponumero);
						}
					}
				
			}else if(city[temp1][d].equalsIgnoreCase("H")){
				System.out.println("hemos llegado");
				coordenadaMeta = temp1+","+d;
				
				grafos.addVertice(coordenadaMeta);

				if (auxcompass.equals("North")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,0");
				} else if(auxcompass.equals("East")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "0");
				} else if(auxcompass.equals("South")){
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "1,0");
				} else if(auxcompass.equals("West")) {
					grafos.addArista(cola.peek().getDato(), coordenadaMeta, "2,2,0");
				}
				auxcompass = "East";
				guardarcomppass.put(coor, auxcompass);
				
				break;
			}
		
			
			
				}
			}
			
			cola.poll();
			
			if (cola.isEmpty()) {			
			
				estado=true;
			}
			
			
			if (cola.peek()!=null) {
				
				separar(cola.peek().getDato());	
				
			}		
		}

		

		
		break;
	}

	//lst.add("0"); // right
	//lst.add("1"); lst.add("0"); 
	/*lst.add("0"); lst.add("1"); 
	lst.add("0"); lst.add("0"); lst.add("0"); lst.add("0"); 
	lst.add("2"); // right
	lst.add("0"); lst.add("0"); lst.add("0"); lst.add("0"); 
	lst.add("1"); // left
	lst.add("0"); lst.add("0"); lst.add("0"); lst.add("0"); 
	lst.add("2"); // right
	lst.add("2"); // right */

	ctrl.pintarBusqueda();
	
	if (!estado) {		
		movimientos(encontrarCamino(i+","+j));
	}else{
		
		JOptionPane.showMessageDialog(null, "No hay camino");
	}
	
	}
	
	public ArrayList<String> encontrarCamino(String coordenadaInicial) {
		

        Queue<Vertices> colaAux = new LinkedBlockingDeque();
        Map<Vertices, Integer> visitados = new HashMap();
        ArrayList<String> camino = new ArrayList<>();
        Vertices rutaKarel = null;
        boolean encontrado = false;
        Vertices inicial = grafos.getVertice(coordenadaInicial);
        colaAux.add(inicial);
        
        while (colaAux.size() > 0 && !encontrado) {
            
            if (coordenadaInicial.equals(coordenadaMeta)) {
                return null;
            }else {
                 
            Vertices visitando = colaAux.remove();
            visitados.put(visitando, visitados.size()+1);
            
            
                 
            for (Vertices vb : visitando.getAdyacentes()) {
                if (visitados.get(vb) == null) {
                	
                	
                	
                    if (!colaAux.contains(vb)) {
                        
                        if (vb.getDato().equalsIgnoreCase(coordenadaMeta)) {
                            vb.setAnterior(visitando);
                            rutaKarel = vb;
                            encontrado = true;
                            break;
                        } else {
                            vb.setAnterior(visitando);
                            colaAux.add(vb);
                        }
                    }
                }
            }
        }
        }
        while (rutaKarel.getAnterior() != null) {
        	Vertices anterior = rutaKarel.getAnterior();
        	for (Vertices v : anterior.getAdyacentes()) {
        		if (v.equals(rutaKarel)) {
        			camino.add(v.mapaAdyacentes.get(anterior));
					break;
				}
			}
			rutaKarel = rutaKarel.getAnterior();
		}
        
		return camino;
	}
	
	public void movimientos(ArrayList<String> mov) {
		String[] sp;
		for (int i = mov.size()-1; i >= 0; i--) {
			sp = mov.get(i).split(",");
			for (int j = 0; j < sp.length; j++) {
				lst.add(sp[j]);
			}
		}
	}
	
	
	public ArrayList<String> recorrerGrafoParaPintar(String coordenadaInicial) {
		

        Queue<Vertices> colaAux = new LinkedBlockingDeque();
        Vertices inicial = grafos.getVertice(coordenadaInicial);
        colaAux.add(inicial);
        ArrayList<String> pintar = new ArrayList<>();
        
        while (colaAux.size() > 0) {
            
                 
            Vertices visitando = colaAux.remove();
            
            if (visitando.getDato().equals("11,14")) {
				System.out.println("FIN");
			}
            
            for (Vertices vb : visitando.getAdyacentes()) {
          
            	if (!pintar.contains(vb.getDato()) && 
            			!vb.getDato().equalsIgnoreCase(coordenadaInicial)
            			&& !vb.getDato().equalsIgnoreCase(coordenadaMeta)) {
					pintar.add(vb.getDato());
					colaAux.add(vb);
				}
                   
                }
            
        }
        
      
        

	
	return pintar;
}
	
	public String consularCompass(String coordenadas){
		
		if (guardarcomppass.get(coordenadas)!=null) {
			
			return guardarcomppass.get(coordenadas);
		}
		
		return "";
	}

	public void separar(String separa) {
		String[] s = separa.split(",");
		String resultado = "";
		temp1 = Integer.parseInt(s[0]);
		temp2 = Integer.parseInt(s[1]);

	}

	public void setComp(String comp) {
		this.comp = comp;
	}
	
	public String getComp() {
		return this.comp;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getInfo() {
		return this.info;
	}
	
	public void paintWorld()
	{ String city[][] = new String[30][30]; 
	city = world.getCity();

	for (int i=0; i<30; i++)
	{ for (int j=0; j<30; j++)
		if (city[i][j].equals(""))	
			System.out.print(".");
		else
			System.out.print(city[i][j]);
	System.out.println();
	}	  
	}
}