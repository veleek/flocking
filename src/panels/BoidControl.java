/**
 * 
 */
package flocking.panels;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author Visitor
 *
 */
public class BoidControl extends JPanel{

	static final long serialVersionUID = 1;
	JSlider flockSize;
	JSlider fps;
	
	public BoidControl() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Boids"));
		//setBackground(Color.LIGHT_GRAY);
		
		JLabel numBoidsLabel = new JLabel("Number of Boids:");
		flockSize = new JSlider(1,2000,20);
		flockSize.setMinorTickSpacing(200);
		flockSize.setPaintTicks(true);
		
		JLabel fpsLabel = new JLabel("Frames Per Second:");
		fps = new JSlider(0,100,30);
		fps.setMajorTickSpacing(50);
		fps.setMinorTickSpacing(10);
		// Create the label table
		/*Hashtable<Integer,JLabel> labelTable = new Hashtable<Integer,JLabel>();
		labelTable.put( new Integer( 500 ), new JLabel("2") );
		labelTable.put( new Integer( 10 ), new JLabel("100") );
		labelTable.put( new Integer( 250 ), new JLabel("4") );
		fps.setLabelTable( labelTable );*/

		fps.setPaintLabels(true);
		fps.setPaintTicks(true);
		
		
		
		numBoidsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		flockSize.setAlignmentX(Component.LEFT_ALIGNMENT);
		fpsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		fps.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		add(numBoidsLabel);
		add(flockSize);
		add(Box.createVerticalStrut(5));
		add(fpsLabel);
		add(fps); 
		
		
	}
	
	/**
	 * @param listener
	 */
	public void addFlockSizeListener(ChangeListener listener) {
		flockSize.addChangeListener(listener);
	}

	/**
	 * @param listener
	 */
	public void addFPSListener(ChangeListener listener) {
		fps.addChangeListener(listener);
	}

	/**
	 * @return
	 */
	public int getFPS() {
		return fps.getValue();
	}
}
