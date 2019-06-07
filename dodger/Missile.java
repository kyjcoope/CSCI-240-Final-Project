package dodger;

public class Missile {
	
	int x, y, hitTimer;
	boolean inUse,hit; //inUse same as for Enemy/hit used to let GamePanel know that missile should explode
	
	public Missile(){
		hit = false;
		inUse = false;
		hitTimer = 0;
	}
	//Same as Enemy collision only between enemy's and missiles
	public void detectHit(){
		int i;
		
		for(i=0;i<100;i++){
			if(DodgerGame.enemyArray[i].y>=y&&DodgerGame.enemyArray[i].y<=y+40){	
				if(DodgerGame.enemyArray[i].x>=x&&DodgerGame.enemyArray[i].x<=x+20){
					inUse=false;
					DodgerGame.enemyArray[i].inUse = false;
					hit = true;
					
				}
				else if(DodgerGame.enemyArray[i].x+20>=x&&DodgerGame.enemyArray[i].x+20<=x+20){
					inUse=false;
					DodgerGame.enemyArray[i].inUse = false;
					hit = true;
				}
			}
			if(DodgerGame.enemyArray[i].y+20>=y&&DodgerGame.enemyArray[i].y+20<=y+40){
				if(DodgerGame.enemyArray[i].x>=x&&DodgerGame.enemyArray[i].x<=x+20){
					inUse=false;
					DodgerGame.enemyArray[i].inUse = false;
					hit = true;
				}
				else if(DodgerGame.enemyArray[i].x+20>=x&&DodgerGame.enemyArray[i].x+20<=x+20){
					inUse=false;
					DodgerGame.enemyArray[i].inUse = false;
					hit = true;
				}
			}
		}
	}
}
