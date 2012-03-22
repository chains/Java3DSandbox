package loadstl;

import java.awt.BorderLayout;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import johan.sphere.Spheres;

public class stltest {

	public static void main(String[] args) {
		new stltest();

	}

	private SimpleUniverse universe;
	private BranchGroup group;

	public stltest() {
		run();
	}

	public void run() {
		JFrame frame = new JFrame("STL Test");
		
//		frame.getContentPane();
//
//		frame.setBounds(0, 0, 500, 500);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
//		canvas.setBounds(0, 0, 500, 500);
//
//		frame.add(canvas);
		
		//universe = new SimpleUniverse(canvas);
		
		String[] args = {"-s","porsche.stl"};
		
		ObjLoad obj = new ObjLoad(args);
		
//		group = new BranchGroup();
		
		
		
		
		
		
		
//		
//		universe.addBranchGraph(group);
//		universe.getViewingPlatform().setNominalViewingTransform();
//		frame.pack();
//		frame.setVisible(true);

	}

	private void loadSTL() {
		// TODO Auto-generated method stub
		
	}

}
