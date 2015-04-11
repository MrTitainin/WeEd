package wesnoth.editor.animations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class AnimationPlayer implements Runnable {

	private boolean stop = false;
	private boolean running = false;

	JPanel panel;
	List<AnimationFrame> animation;

	public AnimationPlayer(JPanel panel, List<AnimationFrame> animation) {
		this.panel = panel;
		this.animation = animation;
	}

	@Override
	public void run() {
		this.running = true;
		for (AnimationFrame frame : this.animation) {
			if (this.stop)
				break;
			try {
				int duration = frame.getDuration();
				showPicture(frame.getImage());
				Thread.sleep(duration);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.running = false;
	}

	public void stop() {
		this.stop = true;
		while (this.running) {
		}
		this.stop = false;
	}

	private void showPicture(File image) {
		this.panel.removeAll();
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(image);
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			this.panel.add(picLabel);
		} catch (IOException e) {
			// No image, show nothing
		}
		this.panel.validate();
		this.panel.repaint();
	}
}
