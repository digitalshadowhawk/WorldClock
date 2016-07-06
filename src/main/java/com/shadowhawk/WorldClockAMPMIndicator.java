package com.shadowhawk;

import static com.mumfrey.liteloader.gl.GL.GL_TRIANGLE_FAN;

import java.lang.Math;

@SuppressWarnings("unused")
public class WorldClockAMPMIndicator {

	
	/*
	 * Function that handles the drawing of a circle using the triangle fan
	 * method. This will create a filled circle.
	 *
	 * Params:
	 *	x (GLFloat) - the x position of the center point of the circle
	 *	y (GLFloat) - the y position of the center point of the circle
	 *	radius (GLFloat) - the radius that the painted circle will have
	 */
	void drawFilledCircle(float x, float y, float radius){
		int i;
		int triangleAmount = 20; //# of triangles used to draw circle
		
		//GLfloat radius = 0.8f; //radius
		float twicePi = (float) (2.0f * Math.PI);
		
		//glBegin(GL_TRIANGLE_FAN);//(x, y); // center of circle
			for(i = 0; i <= triangleAmount;i++) { 
				//glVertex2f(
			        //x + (radius * cos(i *  twicePi / triangleAmount)), 
				    //y + (radius * sin(i * twicePi / triangleAmount))
				//);
			}
		//glEnd();
	}
}
