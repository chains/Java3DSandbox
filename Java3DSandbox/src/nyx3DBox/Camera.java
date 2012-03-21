package nyx3DBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.ViewingPlatform;


/**
 * Camera class. Contains listeners for keys and manipulates the camera accordingly.
 * @author Nyx
 *
 */
public class Camera implements KeyListener, ActionListener, MouseMotionListener {

	
	
	private Transform3D view_tf3d;
	private TransformGroup view_tg;
	private int mouseY = 0;
	private int mouseX = 0;
	
	
	/**
	 * Creates a camera controller for the provided ViewingPlatform
	 * @param vp
	 */
	public Camera(ViewingPlatform vp){
        view_tg = vp.getMultiTransformGroup().getTransformGroup(0);
        view_tf3d = new Transform3D();
        view_tg.getTransform(view_tf3d);
	}
	
	/**
	 * Sets the camera to the X-axis, looking along it in the positive direction.
	 */
	public void setToX(){
		//TODO
	}
	
	/**
	 * Sets the camera to the X-axis, looking along it in the negative direction.
	 */
	public void setToXInverse(){
		//TODO
	}
	
	/**
	 * Sets the camera to the Y-axis, looking along it in the positive direction.
	 */
	public void setToY(){
		//TODO
	}
	
	/**
	 * Sets the camera to the Y-axis, looking along it in the negative direction.
	 */
	public void setToYInverse(){
		//TODO
	}
	
	/**
	 * Sets the camera to the Z-axis, looking along it in the positive direction.
	 */
	public void setToZ(){
		//TODO
	}
	
	
	/**
	 * Sets the camera to the Z-axis, looking along it in the positive direction.
	 */
	public void setToZInverse(){
		//TODO
	}
	
	/**
	 * Sets the camera to Free-look mode.
	 */
	public void setToFreeLook(){
		
	}
	
	
	/**
	 * Key listeneres
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// Unused
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
    	if(e.getKeyChar() == 'd'){
    		Transform3D dirTransf = new Transform3D();
    		dirTransf.setTranslation(new Vector3d(0.05d, 0.0d, 0.0d));
            view_tg.getTransform(view_tf3d);
            view_tf3d.mul(dirTransf);
            view_tg.setTransform(view_tf3d); 
    	}
    	if(e.getKeyChar() == 'a'){
    		Transform3D dirTransf = new Transform3D();
    		dirTransf.setTranslation(new Vector3d(-0.05d, 0.0d, 0.0d));
            view_tg.getTransform(view_tf3d);
            view_tf3d.mul(dirTransf);
            view_tg.setTransform(view_tf3d); 
    	}
    	if(e.getKeyChar() == 'w'){
    		Transform3D dirTransf = new Transform3D();
    		dirTransf.setTranslation(new Vector3d(0.0d, 0.0d, -0.05d));
            view_tg.getTransform(view_tf3d);
            view_tf3d.mul(dirTransf);
            view_tg.setTransform(view_tf3d); 
    	}
    	if(e.getKeyChar() == 's'){
    		Transform3D dirTransf = new Transform3D();
    		dirTransf.setTranslation(new Vector3d(0.0d, 0.0d, 0.05d));
            view_tg.getTransform(view_tf3d);
            view_tf3d.mul(dirTransf);
            view_tg.setTransform(view_tf3d); 
    	}
    	if(e.getKeyChar() == 'q'){
    		Transform3D dirTransf = new Transform3D();
    		dirTransf.setTranslation(new Vector3d(0.0d, 0.0d, 0.05d));
            view_tg.getTransform(view_tf3d);
            view_tf3d.mul(dirTransf);
            view_tg.setTransform(view_tf3d); 
    	}
    	if(e.getKeyChar() == 'e'){
    		Transform3D dirTransf = new Transform3D();
    		dirTransf.setTranslation(new Vector3d(0.0d, 0.0d, -0.05d));
            view_tg.getTransform(view_tf3d);
            view_tf3d.mul(dirTransf);
            view_tg.setTransform(view_tf3d); 
    	}
    	if(e.getKeyChar() == 'z'){
            view_tg.getTransform(view_tf3d);
            Transform3D rotTransf = new Transform3D();
            rotTransf.rotY(0.1);
            view_tf3d.mul(rotTransf);
            view_tg.setTransform(view_tf3d); 
    	}
    	if(e.getKeyChar() == 'x'){
            view_tg.getTransform(view_tf3d);
            Transform3D rotTransf = new Transform3D();
            rotTransf.rotY(-0.1);
            view_tf3d.mul(rotTransf);
            view_tg.setTransform(view_tf3d); 
    	}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Unused
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Unused
		
	}


	/**
	 * Mouse listeners
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Hai");
		if(e.getY() < mouseY){
            view_tg.getTransform(view_tf3d);
            Transform3D rotTransf = new Transform3D();
            rotTransf.rotX(-0.01);
            view_tf3d.mul(rotTransf);
            view_tg.setTransform(view_tf3d); 
		}
		if(e.getY() > mouseY){
            view_tg.getTransform(view_tf3d);
            Transform3D rotTransf = new Transform3D();
            rotTransf.rotX(0.01);
            view_tf3d.mul(rotTransf);
            view_tg.setTransform(view_tf3d); 
		}
		if(e.getX() > mouseX){
            view_tg.getTransform(view_tf3d);
            Transform3D rotTransf = new Transform3D();
            rotTransf.rotY(0.01);
            view_tf3d.mul(rotTransf);
            view_tg.setTransform(view_tf3d); 
		}
		if(e.getX() < mouseX){
            view_tg.getTransform(view_tf3d);
            Transform3D rotTransf = new Transform3D();
            rotTransf.rotY(-0.01);
            view_tf3d.mul(rotTransf);
            view_tg.setTransform(view_tf3d); 
		}
		mouseX = e.getX();
		mouseY = e.getY();
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// Unused
		
	}
	
	
	
	
	
	
	
}
