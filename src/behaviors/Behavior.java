/**
 * 
 */
package flocking.behaviors;

import java.awt.Component;
import flocking.*;
import flocking.panels.FlockingBehaviors;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.lang.String;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * @author Ben Randall
 *  
 * <p>
 * This is a base class (could possible be an interaface) that is
 * inherited by all of the flocking behaviors providing a default 
 * interface for all of them to interact with the flock and the
 * individual boids.
 * </p>
 */
abstract public class Behavior extends JPanel
							   implements ItemListener,
							   			  ChangeListener,
							   			  Comparable{

	protected JCheckBox	checkBox;
	protected JSlider slider;
	protected boolean enabled;
	protected double priority;
	protected String behaviorName;
	protected static JTextField tracer;
	
	public Behavior(String behaviorName) {
		this.behaviorName = behaviorName;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		priority = 1;
		
		checkBox = new JCheckBox(behaviorName,true);
		checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		enabled = true;
		
		slider = new JSlider(0,100,100);
		slider.setAlignmentX(Component.LEFT_ALIGNMENT);
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		//checkBox.addItemListener(this);
		//slider.addChangeListener(this);
		add(checkBox);
		add(slider);
	}

	abstract public void setPriority(double priority);
	
	/** 
	 * Returns the current priority of this behavior which must be
	 * greater than or equal to zero.  If multiple behaviors have
	 * the same priority, they will be included in the same order 
	 * in which they were added.   
	 * 
	 * @return double priority
	 */
	abstract public double getPriority();
		// TODO Insert calculations for priority 
		// return priority;
	
	
	abstract protected JVector calcAcceleration(JBoid boid);
	
	public JVector getAcceleration(JBoid boid) {
		return calcAcceleration(boid).star(getStrength());
	}
	
	protected double getStrength() {
		if(enabled)
			return slider.getValue()/100.0;
		else
			return 0;
	}
	
	public String getBehaviorName() {
		return behaviorName;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	static public void setTracer(JTextField newTracer) {
		tracer = newTracer;
	}
	
	public int compareTo (Object other)
	{
		int compare = 0;
		if(this.getPriority() < ((Behavior)other).getPriority())
		{
			compare = -1;
		}
		else if(this.getPriority() > ((Behavior)other).getPriority())
		{
			compare = 1;
		}
		return compare;
	}

	public void itemStateChanged(ItemEvent e) {
		JCheckBox source  = (JCheckBox)e.getItemSelectable();
		
		enabled = (e.getStateChange() == ItemEvent.SELECTED)?true:false;
		
		if(source == checkBox) {
			slider.setEnabled(enabled);
			tracer.setText("Enabling/Disabling "+ behaviorName);
			dispatchEvent((ItemEvent)e);
		}
	}
	
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		
		tracer.setText(String.valueOf(source.getValue()));
	}

	/**
	 * @param behaviors
	 */
	public void addEventListener(FlockingBehaviors behaviors) {
		checkBox.addItemListener(this);
		slider.addChangeListener(this);
	}
	
}
