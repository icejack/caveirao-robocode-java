package robo2012;


//Aqui estamos importando as classes dos metodos E EVENTOS a serem utilizados no decorrer do codigo


import robocode.*;
import java.awt.geom.*;
import robocode.util.*;
import java.awt.*; //utilizado tambem para as cores




public class CAVEIRAO extends AdvancedRobot {

int FORWARD = 1;
Inimigo[] data; // array tipo Inimigo em data 
int posicao;
int count;
String Atualposicao;
double mudancadaenergia;
int movidirecao = 1;
double Previsaoenergia = 100;


//Criada a classe "inimigo"
public class Inimigo {
	//colocado como publico as variaveis para conseguir acesso.
	public String Nome; 
	public int AcertouComTecnica1 = 2;
	public int AcertouComTecnica2 = 1;
	public int AcertouComTecnica3 = 1;
	public int AtualTecnica = 1;
}

public void run() { // executado quando o round for iniciado.

	// >> Separa o movimento radar do da arma. 
	// o AdjustRadar utilizando como parametro "boolean" >> verdadeiro ou falso 
	setAdjustRadarForGunTurn(true);//aki estou definnindo q o radar vire independente do :turn: arma.
	setAdjustRadarForRobotTurn(true); //aki estou definnindo a arma para virar independente do :turn: do robo.


    // Set colors
	data = new Inimigo[15];
	count = 0;
	for (int a = 0; a < 15; a++) {
		data[a] = new Inimigo();
		data[a].Nome = "";
	}
	//cores, essas cores peguei no site www.top-solucoes.com >> Prof. Bruno de Brito
	setBodyColor(Color.black);
	setGunColor(Color.black);
	setRadarColor(Color.black);
	setBulletColor(Color.red);
	setScanColor(Color.black);
	
	//Set aqui utilizado para o radar para virar a arma de volta
	setAdjustRadarForGunTurn(true);
	setAdjustRadarForRobotTurn(true);
	setAdjustGunForRobotTurn(true);

	while (true) { //enquanto verdadeiro, faca...
		turnRadarRight(360); //Vire o radar do robo no sentido horario quando o grau for > 0 em graus
//			Esta chamada e executada imediatamente e nao retorna nada ate que seja concluida.
//			
//			Obs: Valores negativos significam que o radar do
//			roba esta definido para virar  esquerda em 
//			vez da direita.
//
//		Exemplo:
//
//		   / / Ligue o robo de radar de 180 graus para a direita
//		   turnRadarRight (180);
//
//		   / / Depois, vire o robo radar 90 graus para a esquerda
//		   turnRadarRight (-90);
//		 
//		Parametros:
//		graus - a quantidade de graus para transformar radar do 
//			robo para a direita. Se graus > 0 radar do robo vai 
//			virar  direita. Se graus <0 radar do robo vai virar 
//			 esquerda. Se graus = 0 radar do robo nao vai virar, 
//			mas executar.
		
		
	}
}

//	 Executado quando o robo bate em outro. Estou utilizando a classe
//	 "HitRobotEvent" para usar o metodo "getBearing".
//	 Esse metodo  padrao e encontrado na wiki do robocode
public void onHitRobot(HitRobotEvent e) { 		
	//Este evento avisa quando o robo colide com outro.


	if (e.getBearing() > -90 && e.getBearing() < 45) { // O angulo relativo quando esta na frente do robo. 
		//Quando for zero esta na frente do robo.
		setBack(40); //volta 80 pixels .. utilzando set para um robo mais avancado (extends AdvancedRobot)
	} else {
		setAhead(90); //frente > 120 pixel de acorodo com as configuracoes da tela, utilzando set para um robo mais avancado (extends AdvancedRobot)
	}
}

// Evento se inicia ao detectar inimigo:
public void onScannedRobot(ScannedRobotEvent e) {//Executado quando o radar do seu robo encontra um adversario.
	//Esse evento  iniciado quando um robo for dectado pelo scanner.
	// Esse classe nao  herdade, a logica interna dessa classe de envento pode mudar.
	
	//Metodos da classe ScannedRobotEvent
	//Comandos	Tipo Retorno	Descricao Retorno
	//getName()	String	Retorna o nome do robo adversario scaneado.
	//getBearing()	double	Retorna o angulo do robo adversario em relacao ao seu robo
	//getBearingRadians()	double	angulo em radianos do robo adversario em relacao ao seu.
	//getDistance()	double	Retorna a distacia do robo adversario em relacao ao seu robo.
	//getEnergy()	double	Retorna a energia do robo adversario.
	//getHeading()	double	Retorna o angulo em graus do adversario em relacao a tela.
	//getHeadingRadians()	double	Retorna o angulo em radiaons do adversario em relacao a tela.
	//getVelocity()	double	Retorna a velocidade do robo scaneado.

	Atualposicao = e.getName(); // String	Retorna o nome do robo adversario scaneado.
	if (Verificarinimigo(e.getName()) == -1) {
		System.out.println(count);
		data[count] = new Inimigo();
		data[count].Nome = e.getName();
		posicao = count;
		count++;
	} else {

		posicao = Verificarinimigo(e.getName());// getName guarda o nome do ultimo inimigo e depois verifica

	}
	//**********************************************************
	//Tiros do ROBO< Ajustar aqui > Distancia e power do canhao/
	//**********************************************************
	double bulletPower;
	// Assumindo radar e arma sao alinhados 
	//Aqui sao verificados a distancia do robo inimigo, onde o nome foi armazenado (getname)
	if (e.getDistance() < 100) { //se distancia menor que 150 pixels
		bulletPower = 3.0; //forca total
	} else if (e.getDistance() > 150 && e.getDistance() < 400) {
		bulletPower = 2.0; //forca media
	} else {
		bulletPower = 1.0; //forca > fraca
	}
	//********************************************************
	// / / / / / / /  / / / / / / / / / / / / / / / / / / / / /
	//********************************************************



	int x1Acerto = 0;
	if ((getOthers() > 1) || (x1Acerto < 3) || x1Acerto >= 8) {

		
		//********************************************************************************
		// Movimento oscilatorio baseado nessa formula									/
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		// Retorna a distancia restante em movimento atual do robo medida em pixels.
		if (getDistanceRemaining() == 0) { // Se o valor retornado  0, o robo esta atualmente parado.
			FORWARD = -FORWARD; // Essa chamada retorna valores positivos e negativos. 
			setAhead(100 * FORWARD); // Valores positivos significam que o robo esta se movendo para a frente. 
		}
		setTurnRightRadians(e.getBearingRadians() + Math.PI / 2 - 0.5236 //calculo matematico
				* FORWARD * (e.getDistance() > 150 ? 1 : -1));
		// ********************************************************************************
	}

	else {// Valores negativos significam que o robo esta se movendo para tras. 
		// Se o robo levar mais de 2 tiros no x1, ele muda o movimento
		if ((x1Acerto >= 3) && (getOthers() == 1)) {

			setTurnRight(e.getBearing() + 55 - 55 * movidirecao);

			mudancadaenergia = Previsaoenergia - e.getEnergy();
			if (mudancadaenergia > 0 && mudancadaenergia <= 3) {

				movidirecao = -movidirecao; // Valores negativos significam que o robo esto se movendo para tras. 
				setAhead((e.getDistance() / 8 + 25.0) * movidirecao);
			}

		}

	}
	int tecnicafaz = VerificaTecnica();

	if (getOthers() > 1) {
		tecnicafaz = 1;
	}
	System.out.println("Tecnica atual: " + tecnicafaz + "No alvo: "
			+ e.getName());
	if (tecnicafaz == 1) {
		LinearTargeting(e, bulletPower);
		fire(bulletPower);
	} else if (tecnicafaz == 2) {
		CircularTargeting(e, bulletPower);
		fire(bulletPower);
	} else {
		HeadOnTargeting(e, bulletPower);
		fire(bulletPower);
	}

	Previsaoenergia = e.getEnergy();

}

/*
 * Coloquei a classe LinearTargeting e CircularTargeting, pesquisei na
 * internet e achei uma logica interessante, para autodetectar a posicao de
 * robos inimigos, sendo assim adaptei ao meu codigo, nao sei exatamente
 * como funciona os calculos matematicos.
 */

public void LinearTargeting(ScannedRobotEvent e, double bulletPower)

{
	//para entender melhor como funciona o codigo abaixo: http://robowiki.net/wiki/LinearTargeting#How_It_Works

	double myX = getX();
	double myY = getY();
	double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
	double InimigoX = getX() + e.getDistance() * Math.sin(absoluteBearing);
	double InimigoY = getY() + e.getDistance() * Math.cos(absoluteBearing);
	double InimigoHeading = e.getHeadingRadians();
	double InimigoVelocity = e.getVelocity();

	double deltaTime = 0;
	double battleFieldHeight = getBattleFieldHeight(), battleFieldWidth = getBattleFieldWidth();
	double predictedX = InimigoX, predictedY = InimigoY;
	while ((++deltaTime) * (20.0 - 3.0 * bulletPower) < Point2D.Double
			.distance(myX, myY, predictedX, predictedY)) {
		predictedX += Math.sin(InimigoHeading) * InimigoVelocity;
		predictedY += Math.cos(InimigoHeading) * InimigoVelocity;
		if (predictedX < 23.0 || predictedY < 23.0
				|| predictedX > battleFieldWidth - 23.0
				|| predictedY > battleFieldHeight - 23.0) {
			predictedX = Math.min(Math.max(23.0, predictedX),
					battleFieldWidth - 23.0);
			predictedY = Math.min(Math.max(23.0, predictedY),
					battleFieldHeight - 23.0);
			break;
		}
	}
	double theta = Utils.normalAbsoluteAngle(Math.atan2(
			predictedX - getX(), predictedY - getY()));

	setTurnRadarRightRadians(Utils.normalRelativeAngle(absoluteBearing
			- getRadarHeadingRadians()));
	setTurnGunRightRadians(Utils.normalRelativeAngle(theta
			- getGunHeadingRadians()));
}

public void CircularTargeting(ScannedRobotEvent e, double bulletPower)
{//http://www.gsigma.ufsc.br/~popov/aulas/robocode/eventos.html

	//Adicionar robocode.util importacao. * Para Utils e java.awt.geom importacao. * Para Point2D 
// Este codigo vai esta no manipulador de evento onScannedRobot ();

	double myX = getX();
	double myY = getY();
	double oldInimigoHeading = 0;
	double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
	double InimigoX = getX() + e.getDistance() * Math.sin(absoluteBearing);
	double InimigoY = getY() + e.getDistance() * Math.cos(absoluteBearing);
	double InimigoHeading = e.getHeadingRadians();
	double InimigoHeadingChange = InimigoHeading - oldInimigoHeading;
	double InimigoVelocity = e.getVelocity();
	oldInimigoHeading = InimigoHeading;

	double deltaTime = 0;
	double battleFieldHeight = getBattleFieldHeight(), battleFieldWidth = getBattleFieldWidth();
	double predictedX = InimigoX, predictedY = InimigoY;
	while ((++deltaTime) * (20.0 - 3.0 * bulletPower) < Point2D.Double
			.distance(myX, myY, predictedX, predictedY)) {
		predictedX += Math.sin(InimigoHeading) * InimigoVelocity;
		predictedY += Math.cos(InimigoHeading) * InimigoVelocity;
		InimigoHeading += InimigoHeadingChange;
		if (predictedX < 23.0 || predictedY < 23.0
				|| predictedX > battleFieldWidth - 23.0
				|| predictedY > battleFieldHeight - 23.0) {

			predictedX = Math.min(Math.max(23.0, predictedX),
					battleFieldWidth - 23.0);
			predictedY = Math.min(Math.max(23.0, predictedY),
					battleFieldHeight - 23.0);
			break;
		}
	}
	double theta = Utils.normalAbsoluteAngle(Math.atan2(
			predictedX - getX(), predictedY - getY()));

	setTurnRadarRightRadians(Utils.normalRelativeAngle(absoluteBearing
			- getRadarHeadingRadians()));
	setTurnGunRightRadians(Utils.normalRelativeAngle(theta
			- getGunHeadingRadians()));

}

public void HeadOnTargeting(ScannedRobotEvent e, double bulletpower)
//Metodos da classe ScannedRobotEvent
//Comandos	Tipo Retorno	Descricao Retorno
//getName()	String	Retorna o nome do robo adversario scaneado.
//getBearing()	double	Retorna o angulo do robo adversario em relacao ao seu robo
//getBearingRadians()	double	angulo em radianos do robo adversario em relacao ao seu.
//getDistance()	double	Retorna a distacia do robo adversorio em relacao ao seu robo.
//getEnergy()	double	Retorna a energia do robo adversario.
//getHeading()	double	Retorna o angulo em graus do adversario em relacao a tela.
//getHeadingRadians()	double	Retorna o angulo em radiaons do adversario em relacao a tela.
//getVelocity()	double	Retorna a velocidade do robo scaneado.
{
/*
 * funcao que prover uma estrategia simples de apontar onde voce viu pela
 * ultima vez o inimigo.
 */

	//Codigo: http://robowiki.net/wiki/HeadOnTargeting
	double absoluteBearing = getHeadingRadians() + e.getBearingRadians();

	setTurnGunRightRadians(robocode.util.Utils
			.normalRelativeAngle(absoluteBearing - getGunHeadingRadians()));
}

public int Verificarinimigo(String Nome)
/*
 * Verifica o nome dos inimigos e retorna
 */
{
	for (int i = 0; i < 15; i++) {
		if (data[i].Nome.equals(Nome)) {
			return i;
		}
	}
	return -1;
}

public int VerificaTecnica(){
/*
 * Verifica tecnica utilizada baseado na prioridade de valores declarado na
 * classe "Inimigo"
 */

	int tecnica1 = data[posicao].AcertouComTecnica1;
	int tecnica2 = data[posicao].AcertouComTecnica2;
	int tecnica3 = data[posicao].AcertouComTecnica3;

	if ((tecnica1 >= tecnica2) && (tecnica1 >= tecnica3)) {
		data[posicao].AtualTecnica = 1;
		return 1;
	} else if ((tecnica2 > tecnica1) && (tecnica2 >= tecnica3)) {
		data[posicao].AtualTecnica = 2;
		return 2;
	} else {
		data[posicao].AtualTecnica = 3;
		return 3;
	}
}

public void onBulletHit(BulletHitEvent evento)
{
/*
 * Utilizado quando meu robo acerta um tiro no inimigo   
 */


	String AcertouRobo = evento.getName();
	int alvo;
	alvo = data[posicao].AtualTecnica;
	// Se o tiro acertou o robo alvo
	if (Atualposicao.equals(AcertouRobo)) {
		System.out.println("Acertou alvo: Incrementando tecnica " + alvo);
		if (alvo == 1) {
			data[posicao].AcertouComTecnica1++;
			data[posicao].AcertouComTecnica1++;
			data[posicao].AcertouComTecnica1++;

		} else if (alvo == 2) {
			data[posicao].AcertouComTecnica2++;
			data[posicao].AcertouComTecnica2++;
			// data[posicao].AcertouComTecnica2++;
		} else {
			data[posicao].AcertouComTecnica3++;
			data[posicao].AcertouComTecnica3++;
			// data[posicao].AcertouComTecnica3++;
		}
	}

	// Se o tiro acertou outro robo que nao o alvo:
	else {
		if (alvo == 1) {
			data[posicao].AcertouComTecnica1--;
		} else if (alvo == 2) {
			data[posicao].AcertouComTecnica2--;
		} else
			data[posicao].AcertouComTecnica3--;
	}
	return;

}

public void onBulletMissed(BulletMissedEvent evento){
	// http://robocode.sourceforge.net/docs/robocode/robocode/BulletMissedEvent.html
/*
 * Esse evento chamado quando o robo acertar tiros na parede, errando o seu alvo. se
 * tais erros foram feitos com respectivas tecnicas, ele sera informado e vai mudando as
 * tecnicas
 */


	int tecnica;
	tecnica = data[posicao].AtualTecnica;
	if (tecnica == 1) {
		data[posicao].AcertouComTecnica1--;
	} else if (tecnica == 2) {
		data[posicao].AcertouComTecnica2--;
	} else
		data[posicao].AcertouComTecnica3--;

	return;
}

public void onHitWall(HitWallEvent parede){ // onHitWall  executado quando o robo colide com a parede.
/*
 * A HitWallEvent  enviado para onHitWall(), quando o robo colide na parede,
 * e o getBearing () retorna o rumo para a parede que voce bateu, em relacao
 * a posicao do seu robo.
 */



	if (parede.getBearing() > -90 && parede.getBearing() < 90) { // getBearing()	< double	>angulo em graus da parede batida em relacao ao seu robo.
		back(40);
		setTurnRight(35);
	} else {
		ahead(50);
		setTurnRight(35);

	}
}
}


//Configuracoes
//argumentos
//-Xmx512M -Dsun.io.useCanonCaches=false -Ddebug=true
