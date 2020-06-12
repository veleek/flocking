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
public class Separation extends Behavior {

	private static final long serialVersionUID = 1L;
	private static final double MIN_DIST = 7;
	
	public Separation() {
		super("Separation");
		priority = 1;
	}
	/* (non-Javadoc)
	 * @see flocking.behaviors.Behavior#setPriority(double)
	 */
	@Override
	public void setPriority(double priority) {
		// TODO Auto-generated method stub
		this.priority = priority;
	}

	/* (non-Javadoc)
	 * @see flocking.behaviors.Behavior#getPriority()
	 */
	@Override
	public double getPriority() {
		//priority = 1/getAvgDist(boid);
		return priority;
	}

	/* (non-Javadoc)
	 * @see flocking.behaviors.Behavior#getAcceleration(flocking.JBoid)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JVector calcAcceleration(JBoid thisBoid) {
		JVector accel  = new JVector();
		
		ArrayList <JBoid>NN = thisBoid.getNearestNeighbours();
		if(NN.size()>0)
		{	
			for(JBoid neighbour: NN)
			{
				//JBoid neighbour = (JBoid)i.next();
				JVector dist = thisBoid.getPos().minus(neighbour.getPos());
				//System.out.println("Dist: "+ dist.mag2() + " MinDist: "+ MIN_DIST*MIN_DIST);
				
				if(dist.mag2() < (MIN_DIST * MIN_DIST))
				{
					//System.out.println(dist.normalize().mag());
					//System.out.println(dist.normalize().mag2());
					accel.plusEquals(dist.normalize().star(MIN_DIST));
				}
			}
		}
		return accel;
	}
}
