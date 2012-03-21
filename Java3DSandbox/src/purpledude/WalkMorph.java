package purpledude;

/*
 * WalkMorph.java
 *
 * Created on 08 April 2003, 22:25
 */
import java.util.Enumeration;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Ben Moxon
 */
public class WalkMorph extends javax.media.j3d.Behavior {

	private Alpha alpha;
	private Morph morph;
	private double weights[];
	private boolean walking;

	private WakeupOnElapsedFrames framewake = null;
	private WakeupOnAWTEvent starter = null;
	private WakeupOnAWTEvent stopper = null;
	private WakeupCondition wakeUpCondition = null;
	private WakeupCondition keepUpCondition = null;
	private WakeupCriterion[] wakeupArray;
	private WakeupCriterion[] continueArray;

	/**
	 * Creates a new WalkMorph with the Alpha and Morph objects it has been
	 * given.
	 * 
	 * @param a
	 *            The alpha to base the behaviour on.
	 * @param m
	 *            The morph object we are animating.
	 */
	public WalkMorph(Alpha a, Morph m) {
		alpha = a;
		morph = m;
		weights = morph.getWeights();
		starter = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		stopper = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED);
		framewake = new WakeupOnElapsedFrames(0);
		continueArray = new WakeupCriterion[2];
		continueArray[0] = stopper;
		continueArray[1] = framewake;
		keepUpCondition = new WakeupOr(continueArray);
	}

	/**
	 * initializes the behaviour. Our intial starting condition is a keypress.
	 */
	public void initialize() {
		wakeupOn(starter);
	}

	/**
	 * Overrides behaviour's processStimulus method. Triggered by a keypress or
	 * release or on every frame, depending on whether the animation is running
	 * or not.
	 */
	public void processStimulus(Enumeration criteria) {

		WakeupCriterion ster;

		while (criteria.hasMoreElements()) {
			ster = (WakeupCriterion) criteria.nextElement();
			if (ster instanceof WakeupOnAWTEvent)
				processAWTEvent(((WakeupOnAWTEvent) ster).getAWTEvent());
		}

		if (walking) {
			double val = alpha.value();
			if (val < 0.5) {
				double a = val * 2.0;
				weights[0] = 1.0 - a;
				weights[1] = a;
				weights[2] = 0.0;
			} else {
				double a = (val - 0.5) * 2.0;
				weights[0] = 0.0;
				weights[1] = 1.0f - a;
				weights[2] = a;
			}

			morph.setWeights(weights);
			wakeupOn(keepUpCondition);
		}
	}

	private void processAWTEvent(AWTEvent[] events) {
		for (int n = 0; n < events.length; n++) {
			if (events[n] instanceof KeyEvent) {
				KeyEvent ek = (KeyEvent) events[n];

				if (ek.getID() == KeyEvent.KEY_PRESSED) {
					if (ek.getKeyChar() == 'w') {
						alpha.setStartTime(System.currentTimeMillis());
						walking = true;
					}
				} else if (ek.getID() == KeyEvent.KEY_RELEASED) {
					if (ek.getKeyChar() == 'w') {
						walking = false;
						wakeupOn(starter);
					}
				}
			}
		}
	}

}
