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

public class NewScorePanel extends JPanel{
	
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
		
		//Player
		temp = DodgerGame.player.getSubimage(38, 0, 40, 40);
		g.drawImage(temp, 345 + DodgerGame.offset, 650,null);
		
		//New High Score Text
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.BOLD, 30));
		g.drawString("New High Score!",290,300);
		
		//Menu Buttons
		//NAME FIELD
		DodgerGame.nameField.setBounds(345, 345, 100, 25);
		DodgerGame.nameField.setBackground(new Color(255, 102, 0));
		DodgerGame.nameField.setFont(new Font("Helvetica", Font.BOLD, 16));
		//QUIT
		DodgerGame.newScorePanel.add(DodgerGame.quit);
		DodgerGame.quit.setBounds(345, 445, 100, 25);
		buttonSet(DodgerGame.quit);
        //NEXT
        DodgerGame.nextButton.setBounds(345, 410, 100, 25);
		buttonSet(DodgerGame.nextButton);
	
	}
	//JButton quick setup method
	public void buttonSet(JButton jbutton){
		jbutton.setBackground(new Color(255, 102, 0));
        jbutton.setFont(new Font("Helvetica", Font.BOLD, 16));
	}
}