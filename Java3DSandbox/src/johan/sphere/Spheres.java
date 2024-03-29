package johan.sphere;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

/*
 * Second try
 * 
 * 
 */

public class Spheres {
	
	SimpleUniverse universe;
	BranchGroup group;
	Color3f light1Color;
	Color3f lightColor;
	JButton btn;
	AmbientLight ambientlight;
	PointLight pointlight;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Spheres();

	}
	
	
	public Spheres() {
		run();
	}

	private void run() {
		JFrame frame = new JFrame("Johan's Spheres");
		
		frame.getContentPane();

		frame.setBounds(0, 0, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		canvas.setBounds(0, 0, 500, 500);
		
		BorderLayout border = new BorderLayout();
		JPanel panel = new JPanel(border);
		panel.add(btn = new JButton("Randomize colors"), BorderLayout.NORTH);
		panel.add(canvas, BorderLayout.SOUTH);
		frame.add(panel);
		panel.add(canvas);
		
		universe = new SimpleUniverse(canvas);
		group = new BranchGroup();
		
		//Objects
		TransformGroup sphereGrid = addSphereGrid();
//		group.addChild(new Sphere(0.5f));
//		TransformGroup tg = addMovedSphere();
		
		//Rotation of spheregrid
		Transform3D rotation = new Transform3D();
		rotation.rotX(Math.toRadians(15));
		rotation.rotY(Math.toRadians(5));
		rotation.rotZ(Math.toRadians(15));
		
		Alpha spinAlpha = new Alpha(-1, 20000);	
//		RotationInterpolator rotInterpolator = new RotationInterpolator(spinAlpha, sphereGrid);
		RotationInterpolator rotInterpolator = new RotationInterpolator(spinAlpha, sphereGrid, rotation, 0.0f, (float)Math.toRadians(360));
	    BoundingSphere zone = new BoundingSphere();
	    rotInterpolator.setSchedulingBounds(zone);
		sphereGrid.addChild(rotInterpolator);
		
		
		//Lighting
		addPointLight();
		addAmbientLight();
		
		
		View view = canvas.getView();
		view.setBackClipDistance(1000);
		
		universe.addBranchGraph(group);
//		universe.getViewingPlatform().setNominalViewingTransform();
		moveCamera();
		
        //Add action listener to button
        btn.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	randomColors();   
            }
        });   
		
		frame.pack();
		frame.setVisible(true);
		
		while(true){
			randomColors();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public void randomColors(){
    	Random rnd = new Random();
    	float a = rnd.nextFloat();
    	float b = rnd.nextFloat();
    	float c = rnd.nextFloat();
        Color3f lightColor1 = new Color3f(a,b,c);
        Color3f lightColor2 = new Color3f(b,c,a);
        
        ambientlight.setColor(lightColor1);
        pointlight.setColor(lightColor2);
	}
	 
	public void moveCamera(){
		ViewingPlatform vp = universe.getViewingPlatform();
		TransformGroup View_TransformGroup = vp.getMultiTransformGroup().getTransformGroup(0);
		
		Transform3D View_Transform3D = new Transform3D();
        View_TransformGroup.getTransform(View_Transform3D);
        
        View_Transform3D.setTranslation(new Vector3f(0.0f, 0.0f, 40.0f));
        View_TransformGroup.setTransform(View_Transform3D);
	}
	
	public TransformGroup addSphereGrid(){
		
		TransformGroup largeTransformGrp = new TransformGroup();
		largeTransformGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		largeTransformGrp.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		
		for(int x = -10 ; x <= 10 ; x++){
			for (int y = -10; y <= 10; y++) {
				for (int z = -10; z <= 10 ; z++) {
			
			
					Sphere sphere = new Sphere(0.2f);
					TransformGroup transformGrp = new TransformGroup();
					Transform3D transform = new Transform3D();
					transform.setTranslation(new Vector3f(x, y, z));

					
					transformGrp.setTransform(transform);
					transformGrp.addChild(sphere);

					largeTransformGrp.addChild(transformGrp);
					
					
				}
			}
		}
		
		
		group.addChild(largeTransformGrp);
		return largeTransformGrp;
	}
	
	public TransformGroup addMovedSphere(){
		Sphere sphere = new Sphere(0.5f);
		TransformGroup transformGrp = new TransformGroup();
		Transform3D transform = new Transform3D();
		
		transform.setTranslation(new Vector3f(1.0f, 0.0f, 0.0f));
		transformGrp.setTransform(transform);
		transformGrp.addChild(sphere);
		group.addChild(transformGrp);
		
		return transformGrp;
	}
	
	
	public void addPointLight(){
		//Creates light that shines 100 units from its origin
		light1Color = new Color3f(1.8f, 0.1f, 0.1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);
		
		pointlight = new PointLight(light1Color, new Point3f(5.0f, -5f, 2.0f), new Point3f(0.1f, 0.0f, 0.0f));
		pointlight.setEnable(true);
		pointlight.setCapability(PointLight.ALLOW_COLOR_WRITE);
		pointlight.setInfluencingBounds(bounds);
		group.addChild(pointlight);
	}
	
	public void addAmbientLight(){
		lightColor = new Color3f(1.0f, 0.5f, 1.0f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);
		
		ambientlight = new AmbientLight(true, lightColor); 
		ambientlight.setInfluencingBounds(bounds);
		ambientlight.setCapability(AmbientLight.ALLOW_COLOR_WRITE);
		group.addChild(ambientlight);
	}

}
