package de.teacherUtilities;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Main extends JFrame implements Constants
{
	private static final long serialVersionUID = 2638500843910810227L;

	
	Main(Simulation anim)
    {
    	super("ANDIS Elementary Maths Problems Creator " + Simulation.VERSION);
    	setSize(DIM);
    	addWindowListener(new DemoAdapter());    	
    	this.add(anim);        
    }
    
    public static void main( String argv[] ) 
    {    	
    	Simulation anim = new Simulation();
    	JFrame f = new Main(anim);
    	f.setVisible(true);   	
    	
    	while(true)
    	{
    	    System.out.println("");
    	}
    }
        
    static class DemoAdapter extends WindowAdapter
    {
        @Override
		public void windowClosing (WindowEvent e){System.exit(0);}
    }
}
