
public class Estado {
	
	private Margem esquerda;
	private Margem direita;
	private Movimento ultimoMovimento;
	private Pilha pilhaDeEstados;
	private Fila filaDeEstados;
	
	public Estado() {
		pilhaDeEstados = new Pilha();
		filaDeEstados = new Fila();
	}
	
	public Estado(Margem esquerda, Margem direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	public Estado mover(int missionarios, int canibais, char direcao){
		this.ultimoMovimento = new Movimento(missionarios, canibais, direcao);
		
		if(direcao == 'D'){
			this.esquerda.retirar(missionarios, canibais);
			this.direita.colocar(missionarios, canibais);
		}
		if(direcao == 'E'){
			this.direita.retirar(missionarios, canibais);
			this.esquerda.colocar(missionarios, canibais);
		}
		return this;
	}
	
	public boolean testaEstado(){
		
		//Estado proibido
		if(esquerda.getMissionarios() != 0){
			if(esquerda.getCanibais() > esquerda.getMissionarios()){
//				System.out.println("Estado proibido!!!");
				return false;
			}
		}
		if(direita.getMissionarios() != 0){
			if(direita.getCanibais() > direita.getMissionarios()){
//				System.out.println("Estado proibido!!!");
				return false;
			}
		}
		
		//Estado final
		if(esquerda.getMissionarios() == 0 && esquerda.getCanibais() == 0
		&& direita.getMissionarios() == direita.getCanibais()){
			System.out.println("Solução encontrada!!!");
			return true;
		}
		
		//Estado intermediário
		return true;
	}
	
//	public void buscaEmProfundidade(int missionarios, int canibais, char direcao){
//		
//		this.mover(missionarios, canibais, direcao);
//		
//		if(esquerda.getMissionarios() != 0){
//			if(esquerda.getCanibais() > esquerda.getMissionarios()){
//				System.out.println("Estado proibido!!!");
//				return;
//			}
//		}
//		if(direita.getMissionarios() != 0){
//			if(direita.getCanibais() > direita.getMissionarios()){
//				System.out.println("Estado proibido!!!");
//				return;
//			}
//		}
//		
//		if(esquerda.getMissionarios() == 0 && esquerda.getCanibais() == 0
////		&& direita.getMissionarios() == 3 && direita.getCanibais() == 3){
//		&& direita.getMissionarios() == direita.getCanibais()){
//			System.out.println("Solução encontrada!!!");
//			return;
//		}
//		
////		filhos.empilhar(new Estado(esquerda.clone(), direita.clone()));
////		filhos.empilhar(new Estado(esquerda.clone(), direita.clone()));
////		filhos.empilhar(new Estado(esquerda.clone(), direita.clone()));
////		filhos.empilhar(new Estado(esquerda.clone(), direita.clone()));
//		
//		if(ultimoMovimento.getDirecao() == 'D'){
//			for (int i = 0; i < 3; i++) {
//				for (int j = 0; j < 3; j++) {
//					if(i + j >= 1 && i + j <= 2){
//						if(direita.getMissionarios() >= i && direita.getCanibais() >=j){
//							if (ultimoMovimento.getMissionarios() != i && ultimoMovimento.getCanibais() != j){
////								filhos.desempilhar().buscaEmProfundidade(i, j, 'E');
//								new Estado(esquerda.clone(), direita.clone()).buscaEmProfundidade(i, j, 'E');
//							}
//						}//else filhos.desempilhar();
//					}
//				}
//			}
////			if(direita.getMissionarios() >= 1){
////				if (ultimoMovimento.getMissionarios() != 1 && ultimoMovimento.getCanibais() != 0){ 
////					//Problema = não está entrando aqui
////					filhos.desempilhar().buscaEmProfundidade(1, 0, 'E');
////				}
////			}else filhos.desempilhar();
////			if(direita.getCanibais() >= 1){
////				if (ultimoMovimento.getMissionarios() != 0 && ultimoMovimento.getCanibais() != 1){
////					filhos.desempilhar().buscaEmProfundidade(0, 1, 'E');
////				}
////			}else filhos.desempilhar();
////			if(direita.getMissionarios() >= 1 && direita.getCanibais() >= 1){
////				if (ultimoMovimento.getMissionarios() != 1 && ultimoMovimento.getCanibais() != 1){
////					filhos.desempilhar().buscaEmProfundidade(1, 1, 'E');
////				}
////			}else filhos.desempilhar();
////			if(direita.getMissionarios() >= 2){
////				if (ultimoMovimento.getMissionarios() != 2 && ultimoMovimento.getCanibais() != 0){
////					filhos.desempilhar().buscaEmProfundidade(2, 0, 'E');
////				}
////			}else filhos.desempilhar();
////			if(direita.getCanibais() >= 2){
////				if (ultimoMovimento.getMissionarios() != 0 && ultimoMovimento.getCanibais() != 2){
////					filhos.desempilhar().buscaEmProfundidade(0, 2, 'E');
////				}
////			}else filhos.desempilhar();
//		}else{
//			for (int i = 0; i < 3; i++) {
//				for (int j = 0; j < 3; j++) {
//					if(i + j >= 1 && i + j <= 2){
//						if(esquerda.getMissionarios() >= i && esquerda.getCanibais() >=j){
//							if (ultimoMovimento.getMissionarios() != i && ultimoMovimento.getCanibais() != j){
////								filhos.desempilhar().buscaEmProfundidade(i, j, 'D');
//								new Estado(esquerda.clone(), direita.clone()).buscaEmProfundidade(i, j, 'D');
//							}
//						}//else filhos.desempilhar();
//					}
//				}
//			}
//		}
//		
//	}

private void buscaEmProfundidade(Pilha pilha){
	
		this.pilhaDeEstados = pilha;
		if (testaEstado() == false){
			if(!pilhaDeEstados.isEmpty()) pilhaDeEstados.desempilhar().buscaEmProfundidade(pilhaDeEstados);
			return;
		}
		
		if(ultimoMovimento.getDirecao() == 'D'){
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j == 1 || i + j == 2){
						if(direita.getMissionarios() >= i && direita.getCanibais() >=j){
							if (ultimoMovimento.getMissionarios() != i || ultimoMovimento.getCanibais() != j){
								pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(i, j, 'E'));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}else{
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(esquerda.getMissionarios() >= i && esquerda.getCanibais() >=j){
							if (ultimoMovimento.getMissionarios() != i || ultimoMovimento.getCanibais() != j){
								pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(i, j, 'D'));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		if(!pilhaDeEstados.isEmpty()) pilhaDeEstados.desempilhar().buscaEmProfundidade(pilhaDeEstados);
	}
	
private void buscaEmLargura(Fila fila){
		
		this.filaDeEstados = fila;
		if (testaEstado() == false){
			if(!filaDeEstados.isEmpty()) filaDeEstados.desenfileirar().buscaEmLargura(filaDeEstados);
			return;
		}
		
		if(ultimoMovimento.getDirecao() == 'D'){
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(direita.getMissionarios() >= i && direita.getCanibais() >=j){
							if (ultimoMovimento.getMissionarios() != i || ultimoMovimento.getCanibais() != j){
								filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(i, j, 'E'));
							}else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}else{
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(esquerda.getMissionarios() >= i && esquerda.getCanibais() >=j){
							if (ultimoMovimento.getMissionarios() != i || ultimoMovimento.getCanibais() != j){
								filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(i, j, 'D'));
							}else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		if(!filaDeEstados.isEmpty()) filaDeEstados.desenfileirar().buscaEmLargura(filaDeEstados);
	}
	
	public void iniciarBuscaEmLargura(){
		this.esquerda = new Margem(3, 3);
		this.direita = new Margem(0, 0);
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(1, 0, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(0, 1, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(1, 1, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(2, 0, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(0, 2, 'D'));
		filaDeEstados.desenfileirar().buscaEmLargura(filaDeEstados);
	}
	
	public void iniciarBuscaEmProfundidade(){
		this.esquerda = new Margem(3, 3);
		this.direita = new Margem(0, 0);
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(1, 0, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(0, 1, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(1, 1, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(2, 0, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(0, 2, 'D'));
		pilhaDeEstados.desempilhar().buscaEmProfundidade(pilhaDeEstados);
	}

	public static void main(String[] args) {
//		Margem esquerda = new Margem(3, 3);
//		Margem direita = new Margem(0, 0);
//		
//		Estado estadoInicial = new Estado(esquerda.clone(), direita.clone());
//		
//		estadoInicial.buscaEmProfundidade(1, 0, 'D');
//		estadoInicial.buscaEmProfundidade(0, 1, 'D');
//		estadoInicial.buscaEmProfundidade(1, 1, 'D');
//		estadoInicial.buscaEmProfundidade(2, 0, 'D');
//		estadoInicial.buscaEmProfundidade(0, 2, 'D');
		new Estado().iniciarBuscaEmLargura();
	}
}
