// $Id: Turtle.java 4 2009-03-16 14:44:53Z georgebashi $

/*
    This file is part of PTurtle.

    PTurtle is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    PTurtle is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with PTurtle.  If not, see <http://www.gnu.org/licenses/>.
*/

package terrapin;

import java.util.List;
import processing.core.PApplet;

/**
 * Terrapin class, making ideas from the LOGO teaching language available in Processing.
 * 
 * @author George Bashi
 * @author Ollie Glass
 */
public class Terrapin {
	/** x location on screen. */
	public int x;
	/** y location on screen. */
	public int y;
	/** Colour of line drawn by Terrapin (as a Processing color). */
	public int drawColor;
	/** If false, the Terrapin moves but does not leave a trail. */
	public boolean drawing = true;
	/** The angle (in degrees) that the Terrapin is facing. */
	private float rotation;
	/** The PApplet to render to. */
	public final PApplet applet;
	
	/**
	 * Standard constructor, creates a Terrapin in the middle of the screen which
	 * draws in white.
	 * 
	 * @param applet
	 *            PApplet to render to.
	 */
	public Terrapin(PApplet applet) {
		this.applet = applet;
		x = applet.width / 2;
		y = applet.height / 2;
		drawColor = applet.color(255, 255, 255);
	}
	
	/**
	 * "Copy" constructor, creates an identical Terrapin to the one passed in.
	 * 
	 * @param t
	 *            Terrapin to copy.
	 */
	public Terrapin(Terrapin t) {
		applet = t.applet;
		x = t.x;
		y = t.y;
		drawColor = t.drawColor;
		drawing = t.drawing;
		rotation = t.rotation;
	}
	
	/**
	 * Move Terrapin backward.
	 * 
	 * @param amount
	 *            number of pixels to move by.
	 */
	public void backward(int amount) {
		forward(-amount);
	}
	
	/**
	 * Move Terrapin forward.
	 * 
	 * @param amount
	 *            number of pixels to move by.
	 */
	public void forward(int amount) {
		int newX, newY;
		float rotRad = PApplet.radians(rotation);
		newX = x + PApplet.round(amount * PApplet.cos(rotRad));
		newY = y + PApplet.round(amount * PApplet.sin(rotRad));
		moveTo(newX, newY);
	}
	
	/**
	 * Get the distance between this Terrapin and point (x,y).
	 * 
	 * @param otherX
	 *            location in x axis.
	 * @param otherY
	 *            location in y axis.
	 * @return distance in pixels.
	 */
	public int getDistance(int otherX, int otherY) {
		return (int) Math.sqrt(Math.pow((otherX - x), 2) + Math.pow((otherY - y), 2));
	}
	
	/**
	 * Get the distance between this Terrapin and another.
	 * 
	 * @param t
	 *            the other Terrapin.
	 * @return distance in pixels.
	 */
	public int getDistance(Terrapin t) {
		return getDistance(t.x, t.y);
	}
	
	/**
	 * Get the nearest (Euclidean distance) Terrapin from a List.
	 * 
	 * @param Terrapins
	 *            the list.
	 * @return the nearest Terrapin.
	 */
	public Terrapin getNearest(List<Terrapin> Terrapins) {
		Terrapin nearest = null;
		int nearestDist = Integer.MAX_VALUE;
		
		for (Terrapin t : Terrapins) {
			int newDist = getDistance(t.x, t.y);
			if (newDist < nearestDist) {
				nearest = t;
				nearestDist = newDist;
			}
		}
		
		return nearest;
	}
	
	/**
	 * Get the angle that the Terrapin is facing.
	 * 
	 * @return angle in degrees.
	 */
	public int getRotation() {
		return Math.round(rotation);
	}
	
	/**
	 * Turn the Terrapin left.
	 * 
	 * @param amount
	 *            angle in degrees.
	 */
	public void left(int amount) {
		rotation -= amount;
	}
	
	/**
	 * Move the Terrapin to a specified point. Used internally to allow
	 * HistoryTerrapin to override this to record lines.
	 * 
	 * @param x
	 *            location in x axis
	 * @param y
	 *            location in y axis
	 */
	protected void moveTo(int x, int y) {
		if (drawing) {
			applet.stroke(drawColor);
			applet.line(this.x, this.y, x, y);
		}
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Move the Terrapin toward point (x,y).
	 * 
	 * @param toX
	 *            location in x axis.
	 * @param toY
	 *            location in y axis.
	 * @param amount
	 *            value between 0 and 1 as a ratio of how close to move toward
	 *            point (x,y); 0 will not move the Terrapin, 1 will cause it to
	 *            jump straight to (x,y), 0.5f will cause it to move half way
	 *            there, etc.
	 */
	public void moveToward(int toX, int toY, float amount) {
		moveToward(toX, toY, (int) (getDistance(toX, toY) * amount));
	}
	
	/**
	 * Move the Terrapin a specified number of pixels toward point (x,y).
	 * 
	 * @param toX
	 *            location in x.
	 * @param toY
	 *            location in y.
	 * @param amount
	 *            number of pixels to move toward (x,y).
	 */
	public void moveToward(int toX, int toY, int amount) {
		applet.pushMatrix();
		applet.translate(x, y);
		rotation = PApplet.degrees(PApplet.atan2(toY - y, toX - x));
		applet.popMatrix();
		moveTo(toX, toY);
	}
	
	/**
	 * Move Terrapin toward another.
	 * 
	 * @param t
	 *            Terrapin to move towards.
	 * @param amount
	 *            value between 0 and 1 as a ratio of how close to move toward
	 *            point (x,y); 0 will not move the Terrapin, 1 will cause it to
	 *            jump straight to (x,y), 0.5f will cause it to move half way
	 *            there, etc.
	 *            
	 * @see terrapin.Terrapin#moveToward(int, int, float)
	 */
	public void moveToward(Terrapin t, float amount) {
		moveToward(t.x, t.y, amount);
	}
	
	/**
	 * Move Terrapin toward another.
	 * 
	 * @param t
	 *            Terrapin to move towards.
	 * @param amount
	 *            number of pixels to move toward (x,y).
	 *            
	 * @see terrapin.Terrapin#moveToward(int, int, int)
	 */
	public void moveToward(Terrapin t, int amount) {
		moveToward(t.x, t.y, amount);
	}
	
	/**
	 * Randomise the colour that the Terrapin draws with from a set of 16
	 * hard-coded colours.
	 */
	public void randomPenColor() {
		switch ((int) applet.random(0, 15)) {
			case 0:
				setPenColor(255, 255, 255);
				break;
			case 1:
				setPenColor(0, 0, 255);
				break;
			case 2:
				setPenColor(0, 255, 0);
				break;
			case 3:
				setPenColor(0, 255, 255);
				break;
			case 4:
				setPenColor(255, 0, 0);
				break;
			case 5:
				setPenColor(255, 0, 255);
				break;
			case 6:
				setPenColor(255, 255, 0);
				break;
			case 7:
				setPenColor(180, 180, 180);
				break;
			case 8:
				setPenColor(155, 96, 59);
				break;
			case 9:
				setPenColor(197, 136, 18);
				break;
			case 10:
				setPenColor(100, 162, 64);
				break;
			case 11:
				setPenColor(120, 187, 187);
				break;
			case 12:
				setPenColor(255, 149, 119);
				break;
			case 13:
				setPenColor(144, 113, 208);
				break;
			case 14:
				setPenColor(255, 163, 0);
				break;
			case 15:
				setPenColor(183, 183, 183);
				break;
		}
	}
	
	/**
	 * Turn the Terrapin right.
	 * 
	 * @param amount
	 *            angle in degrees.
	 */
	public void right(int amount) {
		rotation += amount;
	}
	
	/**
	 * Move the Terrapin to an absolute location. <strong>This does not
	 * draw</strong>.
	 * 
	 * @param x
	 *            location in x axis.
	 * @param y
	 *            location in y axis.
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set the colour the Terrapin draws with.
	 * 
	 * @param color
	 *            a colour created with
	 *            {@link processing.core.PApplet#color(int, int, int)}.
	 */
	public void setPenColor(int color) {
		drawColor = color;
	}
	
	/**
	 * Set the colour the Terrapin draws with.
	 * 
	 * @param r
	 *            red value, 0-255.
	 * @param g
	 *            green value, 0-255.
	 * @param b
	 *            blue value, 0-255.
	 */
	public void setPenColor(int r, int g, int b) {
		drawColor = applet.color(r, g, b);
	}
	
	/**
	 * Set the direction the Terrapin is facing in to an absolute angle.
	 * 
	 * @param rotation
	 *            angle in degrees.
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Strafe the Terrapin left.
	 * 
	 * @param amount
	 *            number of pixels to strafe Terrapin.
	 */
	public void strafeLeft(int amount) {
		left(90);
		forward(amount);
		right(90);
	}
	
	/**
	 * Strafe the Terrapin right.
	 * 
	 * @param amount
	 *            number of pixels to strafe Terrapin.
	 */
	public void strafeRight(int amount) {
		right(90);
		forward(amount);
		left(90);
	}
	
	/**
	 * Convert the Terrapin to a String representation
	 * 
	 * @return "Terrapin at 100,100"
	 */
	@Override
	public String toString() {
		return "Terrapin at " + x + "," + y;
	}
	
}
