package br.ufpi.modelo;
import java.util.List;


public class Estado {
	
	private Margem esquerda;
	private Margem direita;
	private Movimento ultimoMovimento;
	
	public Estado(Margem esquerda, Margem direita) {
		this.esquerda = esquerda;
		this.direita = direita;
	}

	public Margem getEsquerda() {
		return esquerda;
	}

	public Margem getDireita() {
		return direita;
	}

	public Movimento getUltimoMovimento() {
		return ultimoMovimento;
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
	
	public boolean testaEstado(List<Movimento> movimentosPassados){
		
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
}
