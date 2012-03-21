package johan.sphere;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.exp.swing.JCanvas3D;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.ConfiguredUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * Draws a sphere on the screen.
 * BLEEEEEEHHHHHH
 * 
 * 
 * fungerar ej
 * fungerar ej
 * fungerar ej
 * BLEEEEEEHHHHHH
 * BLEEEEEEHHHHHH
 * BLEEEEEEHHHHHH
 * BLEEEEEEHHHHHH
 * BLEEEEEEHHHHHH
 * BLEEEEEEHHHHHH
 * fungerar ej
 * fungerar ej
 * fungerar ej
 * fungerar ej
 * fungerar ej
 * fungerar ej
 * 
 * @author sajohan
 *
 */


public class DrawSphere {

	SimpleUniverse universe;
	BranchGroup group;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DrawSphere();
	}

	public DrawSphere() {
		run();
	}

	private void run() {
		JFrame frame = new JFrame("Johan's Spheres");
		
		frame.setBounds(0, 0, 500, 500);
		
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		
		
		universe = new SimpleUniverse(canvas3D);
		group = new BranchGroup();
		drawSphere();
//		drawSpheres();
//		spheres.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//	    spheres.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		addLight();
	    universe.addBranchGraph(group);
	    // look towards the ball
	    universe.getViewingPlatform().setNominalViewingTransform();

		frame.add(canvas3D);
		frame.setVisible(true);
		
//		while(true){
//			Transform3D transform = new Transform3D();
//			transform.setTranslation(new Vector3f(0.1f, 0.0f, 0.0f));
//			spheres.setTransform(transform);
//			
//		}
		
	}

	public void addLight() {
		
		//Creates light that shines 100 units from its origin
		Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		
		PointLight light1 = new PointLight(light1Color, new Point3f(1.0f, 0f, 0.0f), new Point3f(1.0f, 0.0f, 1.0f));
		light1.setEnable(true);
		
		light1.setInfluencingBounds(bounds);
		group.addChild(light1);
		
		
//		Color3f light1Color = new Color3f(.1f, 1.4f, .1f); // green light
//
//		   BoundingSphere bounds =
//
//		      new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
//
//		   Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
//
//		   DirectionalLight light1
//
//		      = new DirectionalLight(light1Color, light1Direction);
//
//		   light1.setInfluencingBounds(bounds);
//
//		   group.addChild(light1);
		
	}

	public void drawSphere() {

		
		Sphere sphere = new Sphere(1.5f);
		group.addChild(sphere);
	    

	    
	}
	
	public TransformGroup drawSpheres(){
		
		Sphere sphere = new Sphere(0.5f);
		TransformGroup transformGrp = new TransformGroup();
		Transform3D transform = new Transform3D();
		
		transform.setTranslation(new Vector3f(0.1f, 0.0f, 0.0f));
		transformGrp.setTransform(transform);
		
		
		transformGrp.addChild(sphere);
		group.addChild(transformGrp);
		

		
		return transformGrp;
	}
	
}
