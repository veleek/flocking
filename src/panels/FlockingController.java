package flocking.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import flocking.JFlock;

public class FlockingController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private BoidControl bc;
	//private FlockingBehaviors fb;
	private FlockWorld fw;
	private JFlock flock;
	
	private Timer timer;
	private int fps = 30;

	@SuppressWarnings("unchecked")
	public FlockingController(BoidControl bc, FlockingBehaviors fb, JFlock flock, FlockWorld fw){
		
		//this.bc = bc;
		//this.fb = fb;
		this.flock = flock;
		this.fw  = fw;
		
		bc.addFlockSizeListener(new FlockSizeListener());
		bc.addFPSListener(new FPSListener());
		
		timer = new Timer(1000/bc.getFPS(), new Animator());
		timer.start();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	private class FlockSizeListener implements ChangeListener {

		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			flock.setFlockSize(source.getValue());
		}
	}
	
	private class FPSListener implements ChangeListener {

		/* (non-Javadoc)
		 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
		 */
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			fps = source.getValue();
			if(fps <= 0 )
			{
				if(timer.isRunning())
				{
					timer.stop();
				}
			}
			else
			{
				if(!timer.isRunning())
				{
					timer.start();
				}
				timer.setDelay((int)1000/fps);		
			}
		}
		
	}
	
	private class Animator implements ActionListener
	{
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Action Performed in Flocking Controller");
			flock.update();
			fw.repaint();
		}
		
	}
}	
