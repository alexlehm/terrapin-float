// $Id: Line.java 4 2009-03-16 14:44:53Z georgebashi $

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

import processing.core.PApplet;

/**
 * Line class, stored by HistoryTurtle.
 * 
 * @author George Bashi
 * @author Ollie Glass
 */
public class Line {
	/** From location in x axis. */
	public int fromX;
	/** From location in y axis. */
	public int fromY;
	/** To location in x axis. */
	public int toX;
	/** To location in y axis. */
	public int toY;
	/**
	 * Colour to draw line in, as a colour created with
	 * {@link processing.core.PApplet#color(int, int, int)}.
	 */
	public int drawColor;
	/** PApplet to draw to. */
	private final PApplet applet;
	
	/**
	 * Create a new line.
	 * 
	 * @param applet
	 *            PApplet to draw to.
	 * @param fromX
	 *            from location in x axis.
	 * @param fromY
	 *            from location in y axis.
	 * @param toX
	 *            to location in x axis.
	 * @param toY
	 *            to location in y axis.
	 */
	public Line(PApplet applet, int fromX, int fromY, int toX, int toY, int drawColor) {
		this.applet = applet;
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.drawColor = drawColor;
	}
	
	/**
	 * Draw this line.
	 */
	void draw() {
		applet.stroke(drawColor);
		applet.line(fromX, fromY, toX, toY);
	}
}
