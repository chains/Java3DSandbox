/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nyx3DBox;

import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * Move the camera with wasd. Zoom in and out with q and e. Z and x rotates, but breaks coordinates etc...
 * @author Nyx
 */
public class CameraTesting{

	public CameraTesting(){
		new Hello3d();
	}

    private static class Hello3d implements KeyListener, ActionListener {

    	private Transform3D view_tf3d;
    	private TransformGroup view_tg;
    	Vector3d controlVec = new Vector3d(0.0f, -1.0f, 5.0f);
    	
        public Hello3d() {
        	JFrame frame = new JFrame();
        	
        	
        	GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            Canvas3D canvas = new Canvas3D(config); 

            canvas.setSize(400, 400); 
        	
        	
            SimpleUniverse univ = new SimpleUniverse(canvas);
            BranchGroup group = new BranchGroup();
            Sphere sphere = new Sphere(0.5f);
            group.addChild(sphere);
            Color3f light = new Color3f(1.8f, 0.1f, 0.1f);
            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
            
            Vector3f lightDir = new Vector3f(4.0f, -7.0f, -12.0f);
            DirectionalLight actualLight = new DirectionalLight(light, lightDir);
            actualLight.setInfluencingBounds(bounds);
            group.addChild(actualLight);
            univ.getViewingPlatform().setNominalViewingTransform();
            ViewingPlatform vp = univ.getViewingPlatform();
            view_tg = vp.getMultiTransformGroup().getTransformGroup(0);
            view_tf3d = new Transform3D();
            view_tg.getTransform(view_tf3d);
            
            view_tf3d.setTranslation(controlVec);
            view_tg.setTransform(view_tf3d); 
            univ.addBranchGraph(group);
            canvas.addKeyListener(this);
            frame.add(canvas);
           // frame.addKeyListener(this);
            frame.pack();
            frame.setVisible(true);
            
        }

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
        	System.out.println("Hai");
        	Vector3f tempVec;
        	if(e.getKeyChar() == 'd'){
        		System.out.println("Bai");
        		Transform3D dirTransf = new Transform3D();
        		dirTransf.setTranslation(new Vector3d(0.05d, 0.0d, 0.0d));
        		//controlVec.setX((float)(controlVec.getX() + 0.05));
                view_tg.getTransform(view_tf3d);
                view_tf3d.mul(dirTransf);
                view_tg.setTransform(view_tf3d); 
        	}
        	if(e.getKeyChar() == 'a'){
        		System.out.println("Bai");
        		Transform3D dirTransf = new Transform3D();
        		dirTransf.setTranslation(new Vector3d(-0.05d, 0.0d, 0.0d));
        		//controlVec.setX((float)(controlVec.getX() - 0.05));
                view_tg.getTransform(view_tf3d);
                view_tf3d.mul(dirTransf);
                view_tg.setTransform(view_tf3d); 
        	}
        	if(e.getKeyChar() == 'w'){
        		System.out.println("Bai");
        		Transform3D dirTransf = new Transform3D();
        		dirTransf.setTranslation(new Vector3d(0.0d, 0.05d, 0.0d));
        		//controlVec.setX((float)(controlVec.getX() - 0.05));
                view_tg.getTransform(view_tf3d);
                view_tf3d.mul(dirTransf);
                view_tg.setTransform(view_tf3d); 
        	}
        	if(e.getKeyChar() == 's'){
        		System.out.println("Bai");
        		Transform3D dirTransf = new Transform3D();
        		dirTransf.setTranslation(new Vector3d(0.0d, -0.05d, 0.0d));
        		//controlVec.setX((float)(controlVec.getX() - 0.05));
                view_tg.getTransform(view_tf3d);
                view_tf3d.mul(dirTransf);
                view_tg.setTransform(view_tf3d); 
        	}
        	if(e.getKeyChar() == 'q'){
        		System.out.println("Bai");
        		Transform3D dirTransf = new Transform3D();
        		dirTransf.setTranslation(new Vector3d(0.0d, 0.0d, 0.05d));
        		//controlVec.setX((float)(controlVec.getX() - 0.05));
                view_tg.getTransform(view_tf3d);
                view_tf3d.mul(dirTransf);
                view_tg.setTransform(view_tf3d); 
        	}
        	if(e.getKeyChar() == 'e'){
        		System.out.println("Bai");
        		Transform3D dirTransf = new Transform3D();
        		dirTransf.setTranslation(new Vector3d(0.0d, 0.0d, -0.05d));
        		//controlVec.setX((float)(controlVec.getX() - 0.05));
                view_tg.getTransform(view_tf3d);
                view_tf3d.mul(dirTransf);
                view_tg.setTransform(view_tf3d); 
        	}
        	if(e.getKeyChar() == 'z'){
        		System.out.println("Bai");
                view_tg.getTransform(view_tf3d);
                //view_tf3d.setTranslation(new Vector3f(0,0,0));
                Transform3D rotTransf = new Transform3D();
                rotTransf.rotY(0.1);
                view_tf3d.mul(rotTransf);
                //view_tf3d.setTranslation(controlVec);
                view_tg.setTransform(view_tf3d); 
        	}
        	if(e.getKeyChar() == 'x'){
        		System.out.println("Bai");
                view_tg.getTransform(view_tf3d);
                //view_tf3d.setTranslation(new Vector3f(0,0,0));
                Transform3D rotTransf = new Transform3D();
                rotTransf.rotY(-0.1);
                view_tf3d.mul(rotTransf);
                //view_tf3d.setTranslation(controlVec);
                view_tg.setTransform(view_tf3d); 
        	}
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Hai");
			
		}
    }


}
