package nyx3DBox;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.universe.ViewingPlatform;

/*
 * code modified from http://www.java.net/node/647017
 * Does not work yet
 */


public class OrbitAboutVWOrigin implements MouseMotionListener {
	
	
	ViewingPlatform vp;
	
	private Transform3D view_tf3d;
	private TransformGroup view_tg;
	private int mouseY = 0;
	private int mouseX = 0;
	
	
	public OrbitAboutVWOrigin(ViewingPlatform vp){
		this.vp=vp;
	    view_tg = vp.getMultiTransformGroup().getTransformGroup(0);
	    view_tf3d = new Transform3D();
	    view_tg.getTransform(view_tf3d);
	} 
	
	private static final double ROT_X_RADIANS_PER_PIXEL = 0.01;
	private static final double ROT_Y_RADIANS_PER_PIXEL = ROT_X_RADIANS_PER_PIXEL;

	/**
	* Effects orbiting about the virtual world origin, based on the
	* cursor's previous position and the cursor's current position.
	* The camera remains pointed towards the origin.
	*
	* @param msmsView current MSMS View (which is different than the
	* Java3D View)
	* @param deltaCursorX change in cursor position, x-coordinate,
	* in pixels
	* @param deltaCursorY change in cursor position, y-coordinate,
	* in pixels
	*/
	public void orbit( ViewingPlatform msmsView,
	int deltaCursorX,
	int deltaCursorY ) {

	// Get the view platform transform and its rotation matrix from our
	// current view.
	TransformGroup viewPlatformTG = msmsView.getMultiTransformGroup().getTransformGroup(0);
	Transform3D viewPlatformTransform = new Transform3D();
	viewPlatformTG.getTransform( viewPlatformTransform );
	Matrix3d lastRotation = new Matrix3d();
	viewPlatformTransform.getRotationScale( lastRotation );

	// Handle rotation about the x-axis first. This is based on changes in
	// the cursor's y-coordinate, which makes sense if you think about it.
	// We're using the number of pixels moved to determine the number of
	// radians to move. Also, the direction is important for the user's
	// sanity.
	/** @todo make this a function of distance from the origin */
	double xAngle = -deltaCursorY * ROT_X_RADIANS_PER_PIXEL;

	// The rotation about the x-axis is a rotation about the view
	// platform's x-axis, not the virtual world's.
	// The x-axis must be rotated to reflect the current view.
	Vector3d xAxis = new Vector3d( 1, 0, 0 );
	lastRotation.transform( xAxis );
	Transform3D newXRotation = new Transform3D();
	newXRotation.set( new AxisAngle4d( xAxis, xAngle ) );

	// Now handle rotation about the y-axis. This is based on changes to
	// the cursor's x-coordinate.
	/** @todo make this a function of distance from the origin */
	double yAngle = -deltaCursorX * ROT_Y_RADIANS_PER_PIXEL;

	Vector3d yAxis = new Vector3d( 0, 1, 0 );
	lastRotation.transform( yAxis );
	Transform3D newYRotation = new Transform3D();
	newYRotation.set( new AxisAngle4d( yAxis, yAngle ) );

	// Combine the rotations, then apply it to the current view platform's
	// transform, and we're done!
	newXRotation.mul( newYRotation );
	newXRotation.mul( viewPlatformTransform );
	viewPlatformTG.setTransform( newXRotation );

	} // orbitAboutVWOrigin()

	@Override
	public void mouseDragged(MouseEvent e) {
		orbit(vp,e.getX() - mouseX,  e.getY() - mouseY);
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
