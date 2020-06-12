/**
 * 
 */
package flocking.behaviors;

import java.util.ArrayList;
import flocking.JBoid;
import flocking.JVector;

/**
 * @author Visitor
 *
 */
public class Cohesion extends Behavior {

	private static final long serialVersionUID = 1L;

	public Cohesion() {
		super("Cohesion");
		priority = 3;
	}
		
	/* (non-Javadoc)
	 * @see flocking.behaviors.Behavior#setPriority(double)
	 */
	@Override
	public void setPriority(double priority) {
		this.priority = priority;
	}

	/* (non-Javadoc)
	 * @see flocking.behaviors.Behavior#getPriority()
	 */
	@Override
	public double getPriority() {
		return priority;
	}

	/* (non-Javadoc)
	 * @see flocking.behaviors.Behavior#getAcceleration(flocking.JBoid)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JVector calcAcceleration(JBoid thisBoid) {
		JVector accel = new JVector();
		JVector avgPosition  = new JVector();
		
		ArrayList <JBoid>NN = thisBoid.getNearestNeighbours();
		if(NN.size()>0)
		{
			for(JBoid neighbour: NN)
			{
				JVector neighbourPos = neighbour.getPos();
				avgPosition.plusEquals(neighbourPos);
			}
			avgPosition.divideEquals(NN.size());
			accel = avgPosition.minus(thisBoid.getPos());
		}
		/*	The acceleration vector is equal to the central
		 * 	position of all the boids minus the current
		 * 	position of this boid.  That's the reason for the
		 * 	call to JBoid.star(-1)
		 */
		//System.out.println(avgPosition.minus(thisBoid.getPos()));
		//return thisBoid.getPos().minus(avgPosition);
		return accel;
		
	}
	
}
