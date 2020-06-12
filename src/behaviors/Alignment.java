/**
 * 
 */
package flocking.behaviors;

/*
import java.util.ArrayList;
import java.util.Iterator;
*/

import java.util.ArrayList;
import flocking.JBoid;
import flocking.JVector;

/**
 * @author Visitor
 *
 */
public class Alignment extends Behavior {

	private static final long serialVersionUID = 1L;

	public Alignment() {
		super("Alignment");
		priority  = 2;
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
		JVector avgVelocity  = new JVector();
		ArrayList <JBoid>NN = thisBoid.getNearestNeighbours();
		
		if(NN.size()>0)
		{
			//System.out.println("NN Before: " + NN.size());
			for(JBoid neighbour: NN)
			{
				JVector neighbourVel = neighbour.getVel();
				avgVelocity.plusEquals(neighbourVel);
			}
			//System.out.println("Avg Vel Before: " + avgVelocity);
			avgVelocity.divideEquals(NN.size());
			accel = avgVelocity.minus(thisBoid.getVel());
			
			//System.out.println("NN After: " + NN.size());
			//System.out.println("Avg Vel Mag: " + avgVelocity.mag());
		}
		return accel;
	}

}
