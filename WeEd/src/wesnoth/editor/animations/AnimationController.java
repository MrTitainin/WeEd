package wesnoth.editor.animations;

import javax.swing.JPanel;


public class AnimationController {

	AnimationPlayer animationPlayer;
	JPanel panel;

	public AnimationController(AnimationPlayer animationPlayer, JPanel panel) {
		this.animationPlayer = animationPlayer;
		this.panel = panel;
	}

	public void play() {
		this.animationPlayer.stop();
		new Thread(this.animationPlayer).start();
	}

}
