package dodger;

import java.util.Random;

public class Enemy{

	int x, y;
	boolean inUse; //lets GamePanel know if it needs to draw enemy
	
	public Enemy(){
		inUse = false; //Enemy's are not drawn by default
	}
	//detects enemy collisions with player, if true goes into gameEnding sequence
	public void detectHit(){

			if(y>=650&&y<=690){	
				if(x>=345+DodgerGame.offset&&x<=365+DodgerGame.offset){
					DodgerGame.gameEnding = true;
				}
				else if(x+20>=345+DodgerGame.offset&&x+20<=365+DodgerGame.offset){
					DodgerGame.gameEnding = true;
				}
			}
			if(y+20>=650&&y+20<=690){	
				if(x>=345+DodgerGame.offset&&x<=365+DodgerGame.offset){
					DodgerGame.gameEnding = true;
				}
				else if(x+20>=345+DodgerGame.offset&&x+20<=365+DodgerGame.offset){
					DodgerGame.gameEnding = true;
				}
			}
		}
}
