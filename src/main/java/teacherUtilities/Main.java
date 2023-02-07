package teacherUtilities;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;


public class Main extends JFrame implements Constants
// TODO insufficient code quality in whole project, drastic refactoring necessary
{
	private static final long serialVersionUID = 2638500843910810227L;
	public static final String
		LOG_DIRECTORY_PATH = "logs/";
	
	
	Main(Simulation anim)
    {
		super("ANDIS Elementary Maths Problems Creator " + Simulation.VERSION);
	
		File logDirectory = new File(LOG_DIRECTORY_PATH);
		logDirectory.mkdir();
		
		setSize(DIM);
    	addWindowListener(new DemoAdapter());    	
    	this.add(anim);        
    }
    
    public static void main(String[] argv)
    {    	
    	Simulation anim = new Simulation();
    	JFrame f = new Main(anim);
    	f.setVisible(true);
    }
        
    static class DemoAdapter extends WindowAdapter
    {
        @Override
		public void windowClosing (WindowEvent e){System.exit(0);}
    }
}
