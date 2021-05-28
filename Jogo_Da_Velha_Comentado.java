package br.ucsal;

public class Jogo_Da_Velha_Comentado {
	
	public static void main(String[] args) {
		String[][] tabuleiro = {{" "," "," ","|"," ", " "," ", "|"," "," ", " "},
								{"-","-","-","+","-","-","-","+","-","-","-"},
								{" "," "," ","|"," ", " "," ", "|"," "," ", " "},
								{"-","-","-","+","-","-","-","+","-","-","-"},
								{" "," "," ","|"," ", " "," ", "|"," "," ", " "}};
		
		String jogadorAtual= "X";
		String modoJogo = modoJogo();
		/*Loop que só termina quando o jogo acaba*/
		//Cada loop é uma rodada completa
		while(true) {
		/*Faz uma jogada e atualiza o jogador atual*/	
		jogadorAtual = controleTurnos(tabuleiro,jogadorAtual,modoJogo); 
		imprimirTab(tabuleiro);//atualiza o tabuleiro
		/*Testa todas as possibilidades de fins de jogo com a ultima jogada*/
		int testeVitoria = testeVitoria(tabuleiro);
		if( testeVitoria == 10) {System.out.println("Jogador X venceu"); break;}
		else if(testeVitoria == -10) {System.out.println("Jogador O venceu");break;}
		else if(testeVitoria == 1) {System.out.println("Empate");break;}
		else if(jogadorAtual == "break") {break;}// Caso a cpu tenha vencido ou empatado o jogo
		}
		
	}
	
	private static String modoJogo() {	
		/*Menu de seleção do modo de Jogo*/
		while(true) {	
			Main.imprimir("Escolha um modo de jogo (1 ou 2) \n"
					+ "1. Jogador x Jogador \n"
					+ "2. Jogador x CPU");
			
			switch (Main.scan()) {
				case "1":
					return "JogadorXJogador";	
				case "2":
					return "JogadorXCPU";
				default:
					Main.imprimir("Numero fora Permitido! Tente Novamente.\n");
			}
		}
		
	}
	private static Boolean iguais3(String a,String b,String c) {
		/*testa se tres valores são iguais e não vazios */
		// vai ser usado para diminuir o tamanho do codico no teste de vitoria
		  return (a == b && b == c && a != " ");
		}
	private static int testeVitoria(String[][] tabuleiro) {
		String vencedor = "em andamento";
		
		/*Testa as vitorias horizontalmente*/
		 for (int x = 0; x <= 4; x+=2) {
			    if (iguais3(tabuleiro[x][1], tabuleiro[x][5], tabuleiro[x][9])) {
			      vencedor = tabuleiro[x][1];
			    }
			  }
		 /*Testa as vitorias verticalmente*/
		 for (int y = 1; y <= 9; y+=4) {
			    if (iguais3(tabuleiro[0][y], tabuleiro[2][y], tabuleiro[4][y])) {
			      vencedor = tabuleiro[0][y];
			    }
			  }
		 /*Testa as diagonais*/
		  if (iguais3(tabuleiro[0][1], tabuleiro[2][5], tabuleiro[4][9])) {
			    vencedor = tabuleiro[0][1];
			  }
			  if (iguais3(tabuleiro[4][1], tabuleiro[2][5], tabuleiro[0][9])) {
			    vencedor = tabuleiro[4][1];
			  }

		 /*Testa se ainda tem espaços vazios*/
		 int espaçosVazios = 0;
		  for (int x = 0; x <= 4; x+=2) {
		    for (int y = 1; y <= 7; y+=4) {
		      if (tabuleiro[x][y] == " ") {
		        espaçosVazios++;
		      }
		    }
		  }
		 /*Analisa o resultado e retorna um valor correspondente*/
		 if (vencedor == "em andamento" && espaçosVazios == 0) {// se niguem ganhou e não tem mais espaços vazios
			    return 1;// O jogo teminou em Empate
			    
			  } else  {
				  switch (vencedor) {
					case "X": return 10; //X ganhou
					case "O": return -10;//O ganhou
					default:  return 0; //O jogo não acabou

					}
			  }
	}
	private static String controleTurnos(String[][] tabuleiro, String jogadorAtual , String modoJogo) {
		//Faz uma jogada no Tabuleiro
		String jogador1 ="X";
		String jogador2 ="O";
		
		if(modoJogo =="JogadorXJogador") {
			 /*Jogador contra jogador */
			jogadaJogador(tabuleiro,jogadorAtual,modoJogo);//metodo que efetua a jogada do jogador (existe para evitar codigo duplicado na parte da CPU)
			/*Alterna entre as jogadas entre os jogadores */
			if(jogadorAtual == jogador1) { jogadorAtual = jogador2;} 
			else {jogadorAtual = jogador1;}	
			
		}else {
			 /*Jogador contra CPU */
			jogadaCPU(tabuleiro);//jogada da CPU
			int testeVitoria = testeVitoria(tabuleiro); // salva o valor em um variavel para economizar recursos
			/*Testa todas as possibilidades de fins de jogo com a ultima jogada da CPU*/
			if( testeVitoria == 10) {return "break";} // se a CPU ganhou
			else if(testeVitoria == 1) {return "break";} // se o jogo empatou
			/*Jogando contra cpu o usuario sera sempre "O" */
			jogadorAtual = jogador2;
			imprimirTab(tabuleiro);//Atualiza o tabuleiro
			jogadaJogador(tabuleiro,jogadorAtual,modoJogo);//Jogada do jogador
			
		}
	
		/*Devolve o jogador atual para o metodo main para manter o controle*/
		return jogadorAtual;
	}
	private static void jogadaJogador(String[][] tabuleiro,String jogadorAtual,String modoJogo) {
		Main.imprimir("Jogador "+jogadorAtual+ " Escolha uma casa (1 a 9)\n");
		/*Acões para efetuar a jogada*/
		int casa = Integer.parseInt(Main.scan()); // Recebe a casa do usuario
		int[][] numCasa = new int[9][3]; // Cria um array para as casa utilizaveis do tabuleiro
		int loop = 0; //Valor que vai ser usado como indice das casas
		/*Adiciona todos as posições das casas uteis ao array nunCasa com o indice loop (1 a 9) */
		for(int x =0 ;x<=4;x+=2) {	
			for(int y =1 ;y<=9;y +=4 ) {
				numCasa[loop][0] = loop+1;
				numCasa[loop][1] = x;
				numCasa[loop][2] = y;
				loop ++;
			}
		}
		/*Percorre todos os indices do array nunCasa e compara eles com a escolha do usuario*/	
		for(loop =0; loop<9;loop++){
			if(casa == numCasa[loop][0]) {
				if((tabuleiro[numCasa[loop][1]][numCasa[loop][2]]).isBlank()) { //Se o espaço estive vazio 
					tabuleiro[numCasa[loop][1]][numCasa[loop][2]] = jogadorAtual; // Coloque marca no espaço
					break;// fim da jogada
					}else {//Se o espaço não estiver vazio tente outra vez
						Main.imprimir("Espaço ja preenchido tente outro " + tabuleiro[numCasa[loop][1]][numCasa[loop][2]] );
						jogadaJogador(tabuleiro,jogadorAtual,modoJogo); //chama o propio metodo para dar a chance o usuario escolher outra casa
						}
				}
			}
		
		
		
	}
	private static void jogadaCPU(String[][] tabuleiro) {
		int xF=0,yF=1; //coodenadas da jogada 
		/*Procura a melhor jogada em todos os espaços do tabuleiro  
		 * utilizando um algoritimo minimax para determinar a jogada 
		 * Para uma explicação mais detalhada sobre o algoritimo acesse:
		 * https://bit.ly/2Q5sEny (está em inglês) ou
		 * https://bit.ly/3bwuhST (tradução da pagina de cima)  */
		int melhorPontuacao = -1000; // valor inicial tem que ser um valor bem pequeno
		for(int x =0 ;x<=4;x+=2) {
			for(int y =1 ;y<=9;y +=4 ) {
			 if(tabuleiro[x][y] == " ") {// se o espaço estiver livre
				 tabuleiro[x][y] = "X"; // Faça a jogada
				 int pontuacaoAtual = minimax(tabuleiro,0,false); // teste os cenarios futuros
				 tabuleiro[x][y] =" "; // desfaça a jogada
				if (pontuacaoAtual > melhorPontuacao) {// se a jogada atual for a melhor jogada
					melhorPontuacao = pontuacaoAtual;// salve a jogada atual
					xF= x;yF = y;	// salve a posição x e y da jogada
				 }
			 }  
			}

		}
		
		 tabuleiro[xF][yF] = "X";//Efetua a melhor jogada Possivel
		
	}
	private static int minimax(String[][] tabuleiro, int nivel, boolean jogadorCPU) {
		int puntuacao = testeVitoria(tabuleiro); // valores possiveis 0, 10,-10,1
		// 0 = jogo em andamento 
		// 10 = Cpu venceu 
		//-10 = Cpu perdeu 
		// 1  = Empate  
		if(puntuacao == 10) {  // se a primeireia jodada for ganhar o jogo faça
			return puntuacao;
		}else if(puntuacao==-10) {
			return puntuacao;
		}
		if(jogadorCPU) { // se for a vez da CPU
			int melhorPontuacao = -1000; // valor inicial qualquer e muito pequeno
			/* testa todas as pocibilidade*/
			 for (int x = 0; x <= 4; x+=2) { 
				    for (int y = 1; y <= 9; y+=4) {
				      if (tabuleiro[x][y] == " ") {// se o espaço estiver livre
				    	  tabuleiro[x][y] = "X";// Faça a jogada
				    	  int pontuacao = minimax(tabuleiro,nivel+1,false);// teste os cenarios futuro
				    	  tabuleiro[x][y] = " ";// desfaça a jogada
				    	  if(pontuacao > melhorPontuacao) { // se a jogada atual for a melhor jogada
					    	  melhorPontuacao= pontuacao; // salve a jogada atual
					    	  }
				    	
				      }
				    }
				  }
			return melhorPontuacao;
		}else {
			int melhorPontuacao = 1000;  // valor inicial qualquer e muito grande
			 /* testa todas as possibilidades */
			 for (int x = 0; x <= 4; x+=2) {
				    for (int y = 1; y <= 9; y+=4) {
				      if (tabuleiro[x][y] == " ") {// se o espaço estiver livre
				    	  tabuleiro[x][y] = "O"; // Faça a jogada
				    	  int pontuacao = minimax(tabuleiro,nivel+1,true);// teste os cenarios futuros
				    	  tabuleiro[x][y] = " ";// desfaça a jogada
				    	  if(pontuacao < melhorPontuacao) {// se a jogada atual for a melhor jogada
					    	  melhorPontuacao= pontuacao;// salve a jogada atual
					    	  }
				    	  
				      }
				    }
				  }
			return melhorPontuacao;// retorna o valor da melho jogada
		}
	}
	private static void imprimirTab( String[][] tabuleiro) {
		
	//Passa por todos o elementos do Array Bidimencional.
	// e Imprime os caracteres formando o tabuleiro.
		for (int x = 0; x< tabuleiro.length;x++) {
			for(int y = 0; y< tabuleiro[0].length;y++) {
			Main.imprimir(tabuleiro[x][y]);
			}
			Main.imprimir("\n");
		}	
	}
}
