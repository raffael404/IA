import java.util.ArrayList;
import java.util.List;


public class buscaEmProfundidade {
	private Pilha pilhaDeEstados;
	private List<Movimento> movimentosPassados;
	
	public buscaEmProfundidade() {
		this.pilhaDeEstados = new Pilha();
		this.movimentosPassados = new ArrayList<Movimento>();
	}
	
	private void expandir(Estado estadoAtual, Pilha pilha, List<Movimento> movimentosPassados){
		
		if (estadoAtual.testaEstado(movimentosPassados) == false){
			if(!pilhaDeEstados.isEmpty()) expandir(pilhaDeEstados.desempilhar(), pilhaDeEstados, this.movimentosPassados);
			return;
		}
		
		if(estadoAtual.getUltimoMovimento().getDirecao() == 'D'){
			movimentosPassados.add(new Movimento(estadoAtual.getDireita().getMissionarios(), estadoAtual.getDireita().getCanibais(), 'D'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j == 1 || i + j == 2){
						if(estadoAtual.getDireita().getMissionarios() >= i && estadoAtual.getDireita().getCanibais() >=j){
							if (estadoAtual.getUltimoMovimento().getMissionarios() != i || estadoAtual.getUltimoMovimento().getCanibais() != j){
								pilhaDeEstados.empilhar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'E'));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}else{
			movimentosPassados.add(new Movimento(estadoAtual.getEsquerda().getMissionarios(), estadoAtual.getEsquerda().getCanibais(), 'E'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(estadoAtual.getEsquerda().getMissionarios() >= i && estadoAtual.getEsquerda().getCanibais() >=j){
							if (estadoAtual.getUltimoMovimento().getMissionarios() != i || estadoAtual.getUltimoMovimento().getCanibais() != j){
								pilhaDeEstados.empilhar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'D'));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		if(!pilhaDeEstados.isEmpty()) expandir(pilhaDeEstados.desempilhar(), pilhaDeEstados, this.movimentosPassados);
	}
	
	public void iniciar(Estado estadoInicial){
		this.movimentosPassados.add(new Movimento(estadoInicial.getEsquerda().getMissionarios(), estadoInicial.getEsquerda().getCanibais(), 'E'));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(1, 0, 'D'));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(0, 1, 'D'));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(1, 1, 'D'));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(2, 0, 'D'));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(0, 2, 'D'));
		System.out.println("Estado visitado - D:" + estadoInicial.getDireita().getMissionarios() + "M" + estadoInicial.getDireita().getCanibais() + "C"
				+ " E:" + estadoInicial.getEsquerda().getMissionarios() + "M" + estadoInicial.getEsquerda().getCanibais() + "C");
		expandir(pilhaDeEstados.desempilhar(), pilhaDeEstados, this.movimentosPassados);
	}
}
