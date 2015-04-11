package wesnoth.editor.animations;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import wesnoth.editor.WMLTreeNode;
import wesnoth.editor.WeEd;
import wesnoth.editor.images.ImageFinder;

public class AnimationJFrameFactory {

	public JFrame buildAnimationJFrame(WMLTreeNode animation) {
		JFrame frame = new JFrame(animation.toString());
		JPanel panel = addPanel(frame);
		Preferences prefs = Preferences.userNodeForPackage(WeEd.class);
		ImageFinder imageFinder = new ImageFinder(getCorePath(prefs), getAddonPath(prefs, animation));
		List<AnimationFrame> animationFrames = new AnimationFramesBuilder(imageFinder, animation).buildAnimation(null);
		AnimationPlayer animationPlayer = new AnimationPlayer(panel, animationFrames);
		AnimationController animationController = new AnimationController(animationPlayer, panel);
		addMenuBar(frame, animationController);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400, 300));
		frame.setResizable(true);
		frame.setVisible(true);
		return frame;
	}

	private String getCorePath(Preferences prefs) {
		return prefs.get("wesPath", null) + File.separator + "data" + File.separator + "core" + File.separator + "images" + File.separator;
	}

	private String getAddonPath(Preferences prefs, WMLTreeNode node) {
		while (node.getParent() != null)
			node = node.getParent();
		int addonNamePos = node.toString().substring(prefs.get("addPath", "").length() + 1).indexOf(File.separator) + prefs.get("addPath", "").length() + 1;
		return node.toString().substring(0, addonNamePos) + File.separator + "images" + File.separator;
	}

	private JPanel addPanel(JFrame frame) {
		JPanel panel = new JPanel();
		frame.add(panel);
		return panel;
	}

	private void addMenuBar(JFrame frame, final AnimationController animationController) {
		JMenuBar menuBar = new JMenuBar();

		JButton play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Click");
				animationController.play();
			}
		});
		menuBar.add(play);

		frame.setJMenuBar(menuBar);
	}

}
