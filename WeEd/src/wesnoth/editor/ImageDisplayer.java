package wesnoth.editor;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ImageDisplayer
{
  protected static void displayPicture(File file, JPanel formPanel, JTextArea textPanel)
  {
    ImageIcon image = new ImageIcon(file.getAbsolutePath());
    formPanel.removeAll();
    JLabel label = new JLabel(image);
    formPanel.add(label);
    formPanel.updateUI();
    textPanel.append("File pathname: " + file.getAbsolutePath() + System.getProperty("line.separator"));
    textPanel.append("Image size: " + image.getIconWidth() + "x" + image.getIconHeight());
  }
}