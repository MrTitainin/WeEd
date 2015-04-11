package wesnoth.editor.animations;

import java.io.File;
import java.util.Map;

import wesnoth.editor.WMLTreeNode;
import wesnoth.editor.images.ImageFinder;

public class AnimationFrame {

	private WMLTreeNode frameNode;

	private int duration;
	private File image;

	public AnimationFrame(WMLTreeNode frameNode) {
		this.frameNode = frameNode;
	}

	public int getDuration() {
		return this.duration;
	}

	public File getImage() {
		return this.image;
	}

	public void upadteFor(int i, ImageFinder imagefinder) {
		Map<String, String> attributes = this.frameNode.getAttributes();

		// Update duration
		if(attributes.containsKey("duration"))
			this.duration = Integer.parseInt(attributes.get("duration"));
		else if(attributes.containsKey("end") & attributes.containsKey("begin"))
			this.duration = Integer.parseInt(attributes.get("end")) - Integer.parseInt(attributes.get("begin"));
		else {
			String img = attributes.get("image");
			if (img.contains(":"))
				this.duration = Integer.parseInt(img.substring(img.indexOf(':') + 1));
			else
				this.duration = 100;
		}

		this.image = imagefinder.get(attributes.get("image").split(":")[0]);
	}

}
