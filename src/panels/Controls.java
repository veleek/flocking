/**
 * 
 */
package flocking.panels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ben Randall
 *
 */
public class Controls extends JPanel {
	static final long serialVersionUID = 1;
	public BoidControl bc;
	public FlockingBehaviors fb;
	
	public Controls() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setBorder(BorderFactory.createEtchedBorder());
		setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
		setPreferredSize(new Dimension(120,400));
		
		bc = new BoidControl();
		fb = new FlockingBehaviors();
		
		add(bc);
		add(fb);
				
	}
}
