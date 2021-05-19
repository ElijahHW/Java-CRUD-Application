import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Main {

	public static void main(String[] args) {
	
		new MainFrame();
		
	}
	public static Icon getScaledImage(ImageIcon srcIcon){
		int w = 20;
		int h = 20;
		Image image = srcIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		ImageIcon icon = new ImageIcon(newimg);
		return icon;
	}

}
