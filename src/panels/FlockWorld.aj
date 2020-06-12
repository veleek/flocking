package flocking.panels;

import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import flocking.JBoid;
import flocking.JFlock;
import flocking.JVector;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;



/**
 * @author Ben Randall
 * 
 * This class defines the panel that is used to output all of 
 * the visuals for the FlockingPanel class
 */
public class FlockWorld extends JPanel
						implements ActionListener{

	static final long serialVersionUID = 1L;
	private JFlock flock;
	private Color flockColor = Color.RED;
	private Timer timer;
	
	private boolean invalidated = false;
	
	private ArrayList[][] grid;
	private final int TILE_SIZE = 5;
	private final int X_TILES = 80;
	private final int Y_TILES = 80;
	
	// This is used to determine how far away
	// from the boid to search for neighbors
	// This number is the distance in tiles
	private final int searchSize = 3;
	
	public FlockWorld()
	{
		init();
	}
	
	public FlockWorld(JFlock flock) {
		this.flock = flock;
		init();
	}
	
	public void init()
	{
		Updater.init(this);
		setBackground(new Color(0xEE,0xEE,0xEE));
		setBorder(BorderFactory.createLoweredBevelBorder());
		
		int width  = X_TILES * TILE_SIZE;
		int height = Y_TILES * TILE_SIZE;
		setPreferredSize(new Dimension(width, height));
		JLabel gridHolder = new JLabel("Grid Holder");
		add(gridHolder);
				
		grid = new ArrayList[X_TILES][Y_TILES];
		for(int x=0; x<X_TILES; x++)
		{
			for(int y=0; y<Y_TILES; y++)
			{
				grid[x][y] = new ArrayList();
			}
		}
		timer = new Timer(1000/30,this);
		//timer.start();
	}
	
	@SuppressWarnings("unchecked")
	public void register(JBoid boid)
	{
		unregister(boid);
		int w = TILE_SIZE * X_TILES;
		int h = TILE_SIZE * Y_TILES;
		
		if(boid.getPos().x > w || boid.getPos().y > h)
		{
			System.out.println("Boid is out of range");
		}
		
		ArrayList newTile = grid[getXTile(boid)][getYTile(boid)]; 
		
		newTile.add(boid);
		boid.setCurrTile(newTile);
	}
	
	public void unregister(JBoid boid)
	{
		ArrayList currTile = boid.getCurrTile();
		if(currTile!=null)
		{
			currTile.remove(boid);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList getBoidsNear(JVector pos)
	{
		ArrayList boids = new ArrayList();

		int col = (int)Math.floor(pos.x/TILE_SIZE);
		int row = (int)Math.floor(pos.y/TILE_SIZE);
		
		for(int c = col-searchSize; c <= col+1; c++)
		{
			int p = Math.min(searchSize,(searchSize + 1) - (col - c));
			for(int r = row-p; r <= row+p; r++)
			{
				int cVal;
				int rVal;
				
				if(c<0) { cVal = c + X_TILES; }
				else if(c>=X_TILES) { cVal = c-X_TILES; }
				else { cVal = c; }
				
				if(r<0) { rVal = r + Y_TILES; }
				else if(r>=Y_TILES) { rVal = r-Y_TILES; }
				else { rVal = r; }
				
				//System.out.println("C: "+ c + " cVal: " + cVal);
				//System.out.println("R: "+ r + " rVal: " + rVal);
				
				ArrayList currTile = grid[cVal][rVal];
				if(currTile.size() > 0 )
				{
					Iterator i = currTile.iterator();
					while(i.hasNext())
					{
						boids.add(i.next());
					}
				}
			}
		}
		
		return boids;
	}
	
	public void paintComponent(Graphics page)
	{
		if(invalidated)
		{
			invalidated = false;
			super.paintComponent(page);
					
			JBoid boid;
			JVector pos;
			JVector vel;
	
			page.setColor(flockColor);
			
			for(int i=0;i<flock.getFlockSize();i++)
			{
				//System.out.println("I: " + i);
				boid = flock.getBoidAt(i);
				//System.out.println(boid);
				pos = boid.getPos();
				vel = boid.getVel();
				
				double scaler = 3;
				int x = (int)Math.round(pos.x);
				int y = (int)Math.round(pos.y);
				int w = (int)Math.round(vel.x*scaler);
				int h = (int)Math.round(vel.y*scaler);
				//System.out.println(x+" "+y+" "+w+" "+h);
				page.drawLine(x,y,x+w,y+h);
				//page.drawLine(x+w,y+h,x,y);
				//page.drawLine(x+w,y+h,x,y);
			}
		}
	}
	
	public int getXTile(JBoid boid)
	{
		return (int)Math.round(Math.floor(boid.getPos().x/TILE_SIZE));
	}
	
	public int getYTile(JBoid boid)
	{
		return (int)Math.round(Math.floor(boid.getPos().y/TILE_SIZE));
	}

	/**
	 * @return Returns the flock.
	 */
	public JFlock getFlock() {
		return flock;
	}

	/**
	 * @param flock The flock to set.
	 */
	public void setFlock(JFlock flock) {
		this.flock = flock;
	}
	
	private static aspect Updater {

		private static FlockWorld few;
		
		public static void init(FlockWorld flockWorld)
		{
			few = flockWorld; 
		}
		
		pointcut move(JBoid jBoid):
			target(jBoid) &&
			call(void JBoid.update());
		
		after(JBoid jBoid) returning: move(jBoid) {
			JVector pos = jBoid.getPos();
			int w = few.getWidth();
			int h = few.getHeight();
			
			if(pos.x > w) { pos.x -= w; }
			else if(pos.x < 0) { pos.x += w; }
			if(pos.y > h) { pos.y -= h; }
			else if(pos.y < 0) { pos.y += h; }
			jBoid.setPos(pos);
			few.register(jBoid);
		}
	} 
	
	public void actionPerformed(ActionEvent e) {
		//paintComponent(getGraphics());
	}
}
