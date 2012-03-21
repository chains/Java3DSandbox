package nyx3DBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.ViewingPlatform;

public class Camera implements KeyListener, ActionListener {

	
	
	private Transform3D view_tf3d;
	private TransformGroup view_tg;
	
	public Camera(ViewingPlatform vp){
        view_tg = vp.getMultiTransformGroup().getTransformGroup(0);
        view_tf3d = new Transform3D();
        view_tg.getTransform(view_tf3d);
        
        view_tg.setTransform(view_tf3d); 
	}
	
	
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
    	System.out.println("Hai");
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
