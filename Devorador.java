package Robo_N2_IA;
import robocode.*;
import java.awt.Color;

	
public class Devorador extends TeamRobot {
boolean movingForward;

int dist = 50;
int turnDirection = 0;
double turnGunRight = 0;

// A inicialização do robô deve ser colocada aqui
	public void run() {
	
		setBodyColor(Color.blue);  	//Corpo
		setGunColor(Color.white);  	//Arma
		setRadarColor(Color.yellow);//Radar
		setScanColor(Color.yellow);	//Varredura
		setBulletColor(Color.red);  //Tiro
		
// Loop principal do robô
	while (true) {
			setAhead(40000);
			movingForward = true;
			setTurnRight(90);
			waitFor(new TurnCompleteCondition(this));
			setTurnLeft(180);
			waitFor(new TurnCompleteCondition(this));
			setTurnRight(180);
			waitFor(new TurnCompleteCondition(this));
		}
	}

//quando o robo detectar seu inimigo vem o evento onScannedRobot ATIRAR
	public void onScannedRobot(ScannedRobotEvent e) { //O que fazer quando você vê outro robô
			

	//Se o adiverssario estiver póximo e com bastante vida vou disparar intensamente
		if (isTeammate(e.getName())){
			return;
		}
		else if(e.getDistance() < 50 && getEnergy() > 50){
			fire(7); //vai dar tiro com potencia 7
		}else{
			fire(3); //vai dar um tiro com potencia 1
		}
		return; //chamo o radar novamente antes de girar o canhão
	} 

	public void onHitByBullet(HitByBulletEvent e) { //O que fazer quando você é atingido por um tiro
		turnGunRight = 90 - (getHeading() - e.getHeading()); //normalizar o ângulo 
		ahead(dist); //avança
		dist *= -1;
		scan();
	}

//Quando o robô coledir com o oponente vai ajustar a mira e disparar intensamente 
	public void onHitRobot(HitRobotEvent e){
		if (e.getBearing() >= 0) { // verifica o ângulo do oponente
			turnDirection = 1; // e toma a direção
		} else {
			turnDirection = -1;
			}
		turnRight(e.getBearing()); 
		// Faz o cálculo da intensidade necessária para enfraquecer o oponente
		if (e.getEnergy() > 16) {
			fire(5);
		} else if (e.getEnergy() > 10) {
			fire(3);
		} else if (e.getEnergy() > 4) {  //Dependendo da energia do adiversário vai baixando a intensidades dos tiros
		fire(1);
		} else if (e.getEnergy() > 2) {
			fire(.5);
		} else if (e.getEnergy() > .4) {
			fire(.1);
		}
		ahead(40); // E avança para se chocar com ele

	}
	
}
