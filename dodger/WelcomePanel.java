package dodger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel{
	
	int i,counter = 0;
	BufferedImage temp;
	
	public void paintComponent(Graphics g){
		//declarations
		super.paintComponent(g);
		
		//Background
		g.drawImage(DodgerGame.background, 0,0,null);
		
		//Menu
		g.setColor(Color.black);
		g.fillRoundRect(245, 195, 310, 310, 20, 20);
		g.setColor(new Color(51,153,255));
		g.fillRoundRect(250, 200, 300, 300, 20, 20);
		
		//Control Hints
		g.setColor(Color.black);
		g.fillRoundRect(275, 235, 250, 150, 20, 20);
		g.setColor(new Color(255, 102, 0));
		g.fillRoundRect(280, 240, 240, 140, 20, 20);
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.BOLD, 16));
		g.drawString("A = LEFT",300,275);
		g.drawString("D = RIGHT",400,275);
		g.drawString("SHOOT MISSILE = SPACEBAR",285,315);
		g.drawString("ESC = PAUSE",340,355);

		//Player
		temp = DodgerGame.player.getSubimage(38, 0, 40, 40);
		g.drawImage(temp, 345 + DodgerGame.offset, 650,null);
		
		//Buttons
		//CONTINUE
        DodgerGame.continueButton.setBounds(345, 410, 100, 25);
		buttonSet(DodgerGame.continueButton);
	
	}
	//JButton quick setup method
	public void buttonSet(JButton jbutton){
		jbutton.setBackground(new Color(255, 102, 0));
        jbutton.setFont(new Font("Helvetica", Font.BOLD, 16));
	}
}