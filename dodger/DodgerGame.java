//DodgerGame.java
package dodger;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class DodgerGame extends JFrame implements ActionListener{

	//JStuff
	JFrame jframe = new JFrame();
	static int score = 0;
	static JScrollPane scrollPane = new JScrollPane();
	static Score scoreObject[] = new Score[5];
	static String scoreArray[] = new String[5],nameArray[] = new String [5];
	static JList scoreList, nameList;
	static NewScorePanel newScorePanel = new NewScorePanel();
	GamePanel gamePanel = new GamePanel();
	JPanel mainPanel = new JPanel();
	CardLayout layout = new CardLayout();
	WelcomePanel welcomePanel = new WelcomePanel();
	static ScorePanel scorePanel = new ScorePanel();
	static MenuPanel menuPanel = new MenuPanel();
	static JButton play = new JButton("PLAY"),quit = new JButton("QUIT"),
			scoreButton = new JButton("SCORE"), menuButton = new JButton("MENU"),
			nextButton = new JButton("NEXT"), continueButton = new JButton("NEXT"), gameEndButton = new JButton("NEXT");
	static JTextField nameField = new JTextField(10);
	
	//Declarations
	static boolean left = false, right = false, shootMissle = false, playerHit = false, endGame = false, pauseGame = false, gameEnding = false;
	static int offset = 0, hitTimer = 0,tick = 0;
	static Random rand = new Random();
	static Enemy[] enemyArray = new Enemy[100];
	static Missile[] missileArray = new Missile[100]; 
	boolean missileTimer = false, newHighScore = false;
	static Image background;
	static BufferedImage player, enemy, missile, explosion;
	Timer timer = new Timer(1,this);
	int i;
	String tempName, currentLayout;
	
	public DodgerGame(){
		//Images
        try {
        	player = ImageIO.read(new File("C:\\Users\\Kyle\\Desktop\\Images\\spaceship.png")); //URL: http://i.imgur.com/q8qWo6n.png
            background = ImageIO.read(new File("C:\\Users\\Kyle\\Desktop\\Images\\desertBackground.png")); //URL: http://i.imgur.com/q8qWo6n.png
            enemy = ImageIO.read(new File("C:\\Users\\Kyle\\Desktop\\Images\\enemy.png")); //URL: http://initialscommand.com/main/wp-content/uploads/2011/04/player_detailed_raw.png
            missile = ImageIO.read(new File("C:\\Users\\Kyle\\Desktop\\Images\\missile-sprite.png")); //URL: http://4.bp.blogspot.com/_6_68Px79PIg/SotMKnfcu2I/AAAAAAAAA14/nGZyGqzl7EU/s400/missile-sprite.png
            explosion = ImageIO.read(new File("C:\\Users\\Kyle\\Desktop\\Images\\explosions.png")); //URL: http://www.xnaresources.com/images/tutorialimages/stardefense/explosions.png
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Card layout setup
        mainPanel.setLayout(layout);
        menuPanel.add(play);
        menuPanel.add(scoreButton);
        gamePanel.add(gameEndButton);
        gameEndButton.setVisible(false);
        welcomePanel.add(continueButton);
        scorePanel.add(menuButton);
        newScorePanel.add(nextButton);
        newScorePanel.add(nameField);
        mainPanel.add(menuPanel,"1");
        mainPanel.add(gamePanel,"2");
        mainPanel.add(scorePanel, "3");
        mainPanel.add(newScorePanel,"4");
        mainPanel.add(welcomePanel,"5");
        layout.show(mainPanel, "5");
        
        //movement KeyListener
        mainPanel.addKeyListener(new KeyListener(){
        	public void keyPressed(KeyEvent e) {
        		int i = e.getKeyCode();
        		if(currentLayout=="2"){
        			if(pauseGame==false){
        				if(i == KeyEvent.VK_A||i == KeyEvent.VK_LEFT){
        					left = true;
        				}
        				if(i == KeyEvent.VK_D||i == KeyEvent.VK_RIGHT){
        					right = true;
        				}
        				if(i == KeyEvent.VK_SPACE){
        					shootMissle = true;
        				}
        			}
        			if(i == KeyEvent.VK_ESCAPE){
        				if(pauseGame==false){
        					System.out.println("pause");
        					pauseGame=true;
        				}
        				else if(pauseGame==true){
        					System.out.println("unpause");
        					pauseGame=false;
        				}
        			}
        		}
        	}

			public void keyReleased(KeyEvent arg0) {
				//empty
			}

			public void keyTyped(KeyEvent arg0) {
				//empty
			}
        });
        //sets action listener focus
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();
        
        //Button Setup for all buttons
        play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentLayout = "2";
				layout.show(mainPanel, "2");
				timer.start();
			}	
        });
        quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}	
        });
        scoreButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				for(i=0;i<5;i++){
					scoreArray[i]=scoreObject[i].score;
					nameArray[i]=scoreObject[i].name;
				}
				currentLayout = "3";
				layout.show(mainPanel, "3");
			}	
        });
        menuButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentLayout = "1";
				layout.show(mainPanel, "1");
			}	
        });
        nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				scoreObject[4].name = nameField.getText();
				sortScore(scoreObject);
				Serialize(scoreObject);
				currentLayout = "1";
				layout.show(mainPanel, "1");
			}	
        }); 
        continueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentLayout = "1";
				layout.show(mainPanel, "1");
			}	
        });  
        gameEndButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				DodgerGame.endGame=true;
			}	
        });  
        
        //JFrame setup
		jframe.setVisible(true);
		jframe.setSize(800, 800);
		jframe.add(mainPanel);
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		
		//Create enemy arrays
		enemyArray = new Enemy[100];
		for(i=0;i<100;i++){
			enemyArray[i] = new Enemy();
		}
		//Create missile arrays
		missileArray = new Missile[100];
		for(i=0;i<100;i++){
			missileArray[i] = new Missile();
		}
		
		//scoreList setup
		scoreObject = Deserialize();
		for(i=0;i<5;i++){
			scoreArray[i]=scoreObject[i].score;
			nameArray[i]=scoreObject[i].name;
		}
		scoreList = new JList(scoreArray);
		scoreList.setVisibleRowCount(5);
		scoreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scoreList.setFixedCellWidth(100);
		scoreList.setFixedCellHeight(20);
        scorePanel.add(scoreList);
        
        //nameList setup
		nameList = new JList(nameArray);
		nameList.setVisibleRowCount(5);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameList.setFixedCellWidth(100);
		nameList.setFixedCellHeight(20);
        scorePanel.add(nameList);
	}

	//game loop for Timer
	public void actionPerformed(ActionEvent arg0) {
		//resets settings and performs any actions needed as the game is ending
		if(endGame==true){
			timer.stop();
			gameEndButton.setVisible(false);
			DodgerGame.gameEnding=false;
			int i,j;
			
			for(i=0;i<100;i++){
				missileArray[i].inUse = false;
				missileArray[i].x = 0;
				missileArray[i].y = 0;
				enemyArray[i].inUse = false;
				enemyArray[i].x = 0;
				enemyArray[i].y = 0;
			}
			endGame = false;
			offset=0;
			sortScore(scoreObject);
			for(i=0;i<5;i++){
				if(score>Integer.parseInt(scoreObject[i].score)){
					scoreObject[4].score=Integer.toString(score);
					newHighScore = true;
					break;
				}
			}
			//If a new high score has been achieved go to new NewScorePanel if not go to menuPanel
			if(newHighScore == true){
				currentLayout = "4";
				layout.show(mainPanel, "4");
				newHighScore = false;
			}
			else{
				currentLayout = "1";
				layout.show(mainPanel, "1");
			}
			score=0;
			hitTimer=0;
		}
		
		//Offset controls player location
		if(left==true){
			offset=offset-20;
			left=false;	
		}
		if(right==true){
			offset=offset+20;
			right=false;
		}
		
		//missile shoot
		if(shootMissle==true){
			if(missileTimer==false){
				newMissile();
				missileTimer=true;
			}
			shootMissle=false;
		}
		
		//tick is used to add pauses between events in game
		tick++;
		
		//prevents new enemy's or missiles from being generated if game is paused or ending
		if(pauseGame==false&&gameEnding==false){
			if(tick%50==0){
				newEnemy();
			}
			if(tick%500==0){
				missileTimer=false;
			}
			score++;
		}
		gamePanel.repaint();
	}
	
	public static void main(String[] args){
		new DodgerGame();
	}
	//generates new missiles
	public static void newMissile(){
		int i;
		boolean keepgoing = true;
		while(keepgoing){
			for(i=0;i<100;i++){
				if(missileArray[i].inUse==false){
					missileArray[i].x=345 + DodgerGame.offset;
					System.out.println(missileArray[i].x);
					missileArray[i].y=650;
					missileArray[i].inUse=true;
					missileArray[i].hit = false;
					missileArray[i].hitTimer = 0;
					keepgoing=false;
					break;
				}
			}
		}
	}
	//generates new enemy's
	public static void newEnemy(){
		int i;
		boolean keepgoing = true;
		while(keepgoing){
			for(i=0;i<100;i++){
				if(enemyArray[i].inUse==false){
					enemyArray[i].x=rand.nextInt(800);
					enemyArray[i].y=-20;
					enemyArray[i].inUse=true;
					keepgoing=false;
					break;
				}
			}
		}
	}
	//sorts Score object array based on score
	public void sortScore(Score[] ScoreArray){
		int i,j;
		Score temp;
		for(i=0;i<5;i++){
			for(j=0;j<4;j++){
				if(Integer.parseInt(ScoreArray[j].score)<Integer.parseInt(ScoreArray[j+1].score)){
					temp=ScoreArray[j];
					ScoreArray[j]=ScoreArray[j+1];
					ScoreArray[j+1]=temp;
				}
			}
		}
	}
	//Saves scores and names to highscores.ser
	public static void Serialize(Score[] array){
	try{
		FileOutputStream fileOut = new FileOutputStream("highscores.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(array);
		out.close();
		fileOut.close();
	}catch(IOException i){
		i.printStackTrace();
		}
	}
	//Retrieves scores and names from highscores.ser
	public static Score[] Deserialize(){
	    Score[] deserialArray = null;

        try{
			FileInputStream fileIn = new FileInputStream("highscores.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			deserialArray = (Score[]) in.readObject();
			in.close();
			fileIn.close();
		}catch (IOException i){
			i.printStackTrace();
		}catch(ClassNotFoundException c){
			System.out.println("class not found");
			c.printStackTrace();
		}

		return deserialArray;
	}
}
