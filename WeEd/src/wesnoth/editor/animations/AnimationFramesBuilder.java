package wesnoth.editor.animations;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import wesnoth.editor.WMLTreeNode;
import wesnoth.editor.images.ImageFinder;

public class AnimationFramesBuilder {

	private ImageFinder imagefinder;
	private WMLTreeNode animationNode;

	public AnimationFramesBuilder(ImageFinder imagefinder, WMLTreeNode animationNode) {
		this.imagefinder = imagefinder;
		this.animationNode = animationNode;
	}

	public List<AnimationFrame> buildAnimation(Map<String, String> preferences) {
		List<AnimationFrame> animationFrames = new LinkedList<>();

		for (WMLTreeNode child : this.animationNode.getChildren())
			processNode(child, animationFrames);

		return animationFrames;
	}

	private void processNode(WMLTreeNode child, List<AnimationFrame> animationFrames) {
		if (child.toString().equals("[frame]")) {
			int count = getFramesCount(child);
			for (int i = 0; i < count; i++) {
				AnimationFrame animationFrame = new AnimationFrame(child);
				animationFrame.upadteFor(i, this.imagefinder);
				animationFrames.add(animationFrame);
			}
		} else if (child.toString().equals("[else]")) {

		}
	}

	private int getFramesCount(WMLTreeNode child) {
		return 1;
	}

}
