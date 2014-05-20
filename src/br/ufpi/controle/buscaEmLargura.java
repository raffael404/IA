package br.ufpi.controle;
import java.util.ArrayList;
import java.util.List;

import br.ufpi.modelo.Estado;
import br.ufpi.modelo.Fila;
import br.ufpi.modelo.Movimento;


public class buscaEmLargura {
	private Fila filaDeEstados;
	private List<Movimento> movimentosPassados;
	
	public buscaEmLargura() {
		filaDeEstados = new Fila();
		movimentosPassados = new ArrayList<Movimento>();
	}
	
	private void expandir(Estado estadoAtual, Fila filaDeEstados, List<Movimento> movimentosPassados){
	
	if (estadoAtual.testaEstado(movimentosPassados) == false){
		if(!filaDeEstados.isEmpty()) expandir(filaDeEstados.desenfileirar(), filaDeEstados, this.movimentosPassados);
		return;
	}
	
	if(estadoAtual.getUltimoMovimento().getDirecao() == 'D'){
		movimentosPassados.add(new Movimento(estadoAtual.getDireita().getMissionarios(), estadoAtual.getDireita().getCanibais(), 'D'));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(i + j >= 1 && i + j <= 2){
					if(estadoAtual.getDireita().getMissionarios() >= i && estadoAtual.getDireita().getCanibais() >=j){
						if (estadoAtual.getUltimoMovimento().getMissionarios() != i || estadoAtual.getUltimoMovimento().getCanibais() != j){
							filaDeEstados.enfileirar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'E'));
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
							filaDeEstados.enfileirar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'D'));
						}//else System.out.println("Movimento repetido!!!");
					}
				}
			}
		}
	}
	if(!filaDeEstados.isEmpty()) expandir(filaDeEstados.desenfileirar(), filaDeEstados, this.movimentosPassados);
}

public void iniciar(Estado estadoInicial){
	this.movimentosPassados.add(new Movimento(estadoInicial.getEsquerda().getMissionarios(), estadoInicial.getEsquerda().getCanibais(), 'E'));
	filaDeEstados.enfileirar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(1, 0, 'D'));
	filaDeEstados.enfileirar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(0, 1, 'D'));
	filaDeEstados.enfileirar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(1, 1, 'D'));
	filaDeEstados.enfileirar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(2, 0, 'D'));
	filaDeEstados.enfileirar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(0, 2, 'D'));
	System.out.println("Estado visitado - D:" + estadoInicial.getDireita().getMissionarios() + "M" + estadoInicial.getDireita().getCanibais() + "C"
			+ " E:" + estadoInicial.getEsquerda().getMissionarios() + "M" + estadoInicial.getEsquerda().getCanibais() + "C");
	expandir(filaDeEstados.desenfileirar(), filaDeEstados, this.movimentosPassados);
}
}
