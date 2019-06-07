package dodger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{

	int i,counter = 0;
	BufferedImage temp;
	
	public void paintComponent(Graphics g){
		//declarations
		super.paintComponent(g);
		
		//Background
		g.drawImage(DodgerGame.background, 0,0,null);

		//Enemy
		temp = DodgerGame.enemy.getSubimage(223, 0, 33, 31);
		for(i=0;i<100;i++){
			if(DodgerGame.enemyArray[i].y>=750)
				DodgerGame.enemyArray[i].inUse=false;
			if(DodgerGame.enemyArray[i].inUse==true){
				g.drawImage(temp,DodgerGame.enemyArray[i].x, DodgerGame.enemyArray[i].y,null);
				if(DodgerGame.pauseGame==false&&DodgerGame.gameEnding==false)
					DodgerGame.enemyArray[i].y+=1;
				DodgerGame.enemyArray[i].detectHit();
			}			
		}
		
		//Missiles
		for(i=0;i<100;i++){
			temp = DodgerGame.missile.getSubimage(3, 0, 18, 44);
				//boundary
				if(DodgerGame.missileArray[i].y<=-20)
					DodgerGame.missileArray[i].inUse=false;
				//draw missile
				if(DodgerGame.missileArray[i].inUse==true){
					g.drawImage(temp,DodgerGame.missileArray[i].x, DodgerGame.missileArray[i].y,null);
					if(DodgerGame.pauseGame==false&&DodgerGame.gameEnding==false)
						DodgerGame.missileArray[i].y-=1;
					DodgerGame.missileArray[i].detectHit();
				}
				//Missile Explosions
				if(DodgerGame.missileArray[i].hit==true&&DodgerGame.missileArray[i].hitTimer<=14){
					temp = DodgerGame.explosion.getSubimage(64*DodgerGame.missileArray[i].hitTimer, 0, 65, 64);
					g.drawImage(temp,DodgerGame.missileArray[i].x, DodgerGame.missileArray[i].y,null);
					if(DodgerGame.pauseGame==false&&DodgerGame.gameEnding==false){
						if(DodgerGame.tick%10==0)
							DodgerGame.missileArray[i].hitTimer++;
					}
				}
				
		}
		
		//Player
		temp = DodgerGame.player.getSubimage(38, 0, 40, 40);
		g.drawImage(temp, 345 + DodgerGame.offset, 650,null);
		//player Explosion
		if(DodgerGame.gameEnding==true){
			temp = DodgerGame.explosion.getSubimage(64*DodgerGame.hitTimer, 0, 65, 64);
			g.drawImage(temp,345 + DodgerGame.offset, 650,null);
			if(DodgerGame.tick%10==0)
				if(DodgerGame.hitTimer<14){
					DodgerGame.hitTimer++;
				}
					
		}

		//Score
		g.setColor(Color.green);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString("Score:",15,45);
		String score = Integer.toString(DodgerGame.score);
		g.drawString(score,100,45);
		
		//End Game Text
		if(DodgerGame.gameEnding==true){
			g.setColor(Color.black);
			g.fillRoundRect(275, 235, 250, 150, 20, 20);
			g.setColor(new Color(51,153,255));
			g.fillRoundRect(280, 240, 240, 140, 20, 20);
			g.setColor(Color.black);
			g.setFont(new Font("Helvetica", Font.BOLD, 30));
			g.drawString("GAME OVER",300,275);
			DodgerGame.gameEndButton.setVisible(true);
			DodgerGame.gameEndButton.setBounds(345, 320, 100, 25);
			DodgerGame.gameEndButton.setBackground(new Color(255, 102, 0));
			DodgerGame.gameEndButton.setFont(new Font("Helvetica", Font.BOLD, 16));
		}
		//Pause Text
		if(DodgerGame.pauseGame==true){
			g.setColor(Color.black);
			g.fillRoundRect(275, 235, 250, 150, 20, 20);
			g.setColor(new Color(51,153,255));
			g.fillRoundRect(280, 240, 240, 140, 20, 20);
			g.setColor(Color.black);
			g.setFont(new Font("Helvetica", Font.BOLD, 30));
			g.drawString("PAUSE",345,315);
		}
	}
}
