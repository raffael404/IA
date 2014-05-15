import java.util.ArrayList;
import java.util.List;


public class Estado {
	
	private Margem esquerda;
	private Margem direita;
	private Movimento ultimoMovimento;
	private Pilha pilhaDeEstados;
	private Fila filaDeEstados;
	private List<Movimento> movimentosPassados;
	
	public Estado() {
		pilhaDeEstados = new Pilha();
		filaDeEstados = new Fila();
		movimentosPassados = new ArrayList<Movimento>();
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
		
		System.out.println("Estado visitado - D:" + direita.getMissionarios() + "M" + direita.getCanibais() + "C"
		+ " E:" + esquerda.getMissionarios() + "M" + esquerda.getCanibais() + "C");
		
		//Estado proibido
		if(esquerda.getMissionarios() != 0){
			if(esquerda.getCanibais() > esquerda.getMissionarios()){
				System.out.println("Estado proibido!!!");
				return false;
			}
		}
		if(direita.getMissionarios() != 0){
			if(direita.getCanibais() > direita.getMissionarios()){
				System.out.println("Estado proibido!!!");
				return false;
			}
		}
		
		//Movimento repetido
		if(ultimoMovimento.getDirecao() == 'D'){
			for (int i = 0; i < movimentosPassados.size(); i++) {
				if(movimentosPassados.get(i).getMissionarios() ==  direita.getMissionarios()
				&& movimentosPassados.get(i).getCanibais() ==  direita.getCanibais()
				&& movimentosPassados.get(i).getDirecao() == 'D') return false;
			}
		}else{
			for (int i = 0; i < movimentosPassados.size(); i++) {
				if(movimentosPassados.get(i).getMissionarios() ==  esquerda.getMissionarios()
				&& movimentosPassados.get(i).getCanibais() ==  esquerda.getCanibais()
				&& movimentosPassados.get(i).getDirecao() == 'E') return false;
			}
		}
		
		//Estado final
		if(esquerda.getMissionarios() == 0 && esquerda.getCanibais() == 0
		&& direita.getMissionarios() == direita.getCanibais()){
			System.out.println("Solução encontrada!!!");
			return false;
		}
		
		//Estado intermediário
		return true;
	}
	

	private void buscaEmProfundidade(Pilha pilha, List<Movimento> movimentosPassados){
	
		this.pilhaDeEstados = pilha;
		this.movimentosPassados = movimentosPassados;
		
		if (testaEstado() == false){
			if(!pilhaDeEstados.isEmpty()) pilhaDeEstados.desempilhar().buscaEmProfundidade(pilhaDeEstados, this.movimentosPassados);
			return;
		}
		
		if(ultimoMovimento.getDirecao() == 'D'){
			movimentosPassados.add(new Movimento(direita.getMissionarios(), direita.getCanibais(), 'D'));
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
			movimentosPassados.add(new Movimento(esquerda.getMissionarios(), esquerda.getCanibais(), 'E'));
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
		if(!pilhaDeEstados.isEmpty()) pilhaDeEstados.desempilhar().buscaEmProfundidade(pilhaDeEstados, this.movimentosPassados);
	}
	
	private void buscaEmLargura(Fila fila, List<Movimento> movimentosPassados){
		
		this.filaDeEstados = fila;
		this.movimentosPassados = movimentosPassados;
		
		if (testaEstado() == false){
			if(!filaDeEstados.isEmpty()) filaDeEstados.desenfileirar().buscaEmLargura(filaDeEstados, this.movimentosPassados);
			return;
		}
		
		if(ultimoMovimento.getDirecao() == 'D'){
			movimentosPassados.add(new Movimento(direita.getMissionarios(), direita.getCanibais(), 'D'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(direita.getMissionarios() >= i && direita.getCanibais() >=j){
							if (ultimoMovimento.getMissionarios() != i || ultimoMovimento.getCanibais() != j){
								filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(i, j, 'E'));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}else{
			movimentosPassados.add(new Movimento(esquerda.getMissionarios(), esquerda.getCanibais(), 'E'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(esquerda.getMissionarios() >= i && esquerda.getCanibais() >=j){
							if (ultimoMovimento.getMissionarios() != i || ultimoMovimento.getCanibais() != j){
								filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(i, j, 'D'));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		if(!filaDeEstados.isEmpty()) filaDeEstados.desenfileirar().buscaEmLargura(filaDeEstados, this.movimentosPassados);
	}
	
	public void iniciarBuscaEmLargura(){
		this.movimentosPassados.add(new Movimento(3, 3, 'E'));
		this.esquerda = new Margem(3, 3);
		this.direita = new Margem(0, 0);
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(1, 0, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(0, 1, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(1, 1, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(2, 0, 'D'));
		filaDeEstados.enfileirar(new Estado(esquerda.clone(), direita.clone()).mover(0, 2, 'D'));
		System.out.println("Estado visitado - D:" + direita.getMissionarios() + "M" + direita.getCanibais() + "C"
				+ " E:" + esquerda.getMissionarios() + "M" + esquerda.getCanibais() + "C");
		filaDeEstados.desenfileirar().buscaEmLargura(filaDeEstados, this.movimentosPassados);
	}
	
	public void iniciarBuscaEmProfundidade(){
		this.movimentosPassados.add(new Movimento(3, 3, 'E'));
		this.esquerda = new Margem(3, 3);
		this.direita = new Margem(0, 0);
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(1, 0, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(0, 1, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(1, 1, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(2, 0, 'D'));
		pilhaDeEstados.empilhar(new Estado(esquerda.clone(), direita.clone()).mover(0, 2, 'D'));
		System.out.println("Estado visitado - D:" + direita.getMissionarios() + "M" + direita.getCanibais() + "C"
				+ " E:" + esquerda.getMissionarios() + "M" + esquerda.getCanibais() + "C");
		pilhaDeEstados.desempilhar().buscaEmProfundidade(pilhaDeEstados, this.movimentosPassados);
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
		new Estado().iniciarBuscaEmProfundidade();
	}
}
