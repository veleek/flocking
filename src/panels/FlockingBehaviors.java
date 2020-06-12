package flocking.panels;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Collections;
import flocking.behaviors.*;
import flocking.JBoid;
import flocking.JVector;


/**
 * @author Ben Randall
 * 
 * This panel is used to control which behaviors the flock
 * currently implements and also supplies Controls which 
 * can be used to control the strength of the behaviors.
 */
public class FlockingBehaviors extends JPanel {

	private static final long serialVersionUID = 1;
	private static ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
		
	private JTextField tracer = new JTextField("Tracer",10);

	public FlockingBehaviors() {
		super();
		
		// Define layout size and alignment properties
		setBorder(BorderFactory.createTitledBorder("Behaviors"));
		setPreferredSize(new Dimension(150,400));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		tracer.setMaximumSize(new Dimension(170, 22));
		tracer.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Set a common field for tracing anything that needs it
		Behavior.setTracer(tracer);

		// Add the behaviors
		addBehavior(new Separation());
		addBehavior(new Alignment());
		addBehavior(new Cohesion());
		
		// Add the tracing field
		add(tracer);

	}

	public JTextField getTracer() {
		return tracer;
	}
	
	/**
	 *
	 * @param behavior - A reference to a subclass of Behavior
	 *		which is used to calculate the flocking vector 
	 */
	public void addBehavior(Behavior behavior)
	{
		add(behavior);
		behavior.addEventListener(this);
		behaviors.add(behavior);
	}
	
	/**
	 * 
	 * @param index
	 * @return Behavior the behavior in ArrayList behaviors at
	 * 		the index specified by 'index'
	 */
	public Behavior getBehaviorAt(int index)
	{
		return behaviors.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public JVector getAcceleration(JBoid boid)
	{
		double maxAccel = boid.getMaxAccel();
		JVector accum = new JVector();
		JVector accel = new JVector();
		JVector temp = new JVector();
		Collections.sort(behaviors);
		//System.out.println("***************");
		for(Behavior b:behaviors)
		{
			if(!b.isEnabled()) { continue; }
			temp = b.getAcceleration(boid);
			//System.out.println("Calculating " + b.getBehaviorName());
			//System.out.println(b.getBehaviorName() + ": " + temp);
			accel.plusEquals(temp);
			
			//System.out.println("Accel: "+ accel);
			//System.out.println("Mag: " + accel.mag());
			//System.out.println("Max: " + maxAccel);
			
			if(accel.mag() > maxAccel )
			{
				accel = accum.trim(maxAccel, temp);
				accum.copy(accel);
				break;
			}
			accum.copy(accel);
		}
		return accum;
	}
}
