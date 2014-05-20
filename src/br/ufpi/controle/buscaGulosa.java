package br.ufpi.controle;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.modelo.Estado;
import br.ufpi.modelo.Margem;
import br.ufpi.modelo.Movimento;

public class buscaGulosa {
	private List<Estado> fronteiraDeEstados;
	private List<Movimento> movimentosPassados;
	
	public buscaGulosa() {
		this.fronteiraDeEstados = new ArrayList<Estado>();
		this.movimentosPassados = new ArrayList<Movimento>();
	}

	private Estado funcaoHeuristica(List<Estado> fronteiraDeEstados){
		Estado melhor = new Estado(new Margem(0, 0), new Margem(0, 0));
		for (int i = 0; i < fronteiraDeEstados.size(); i++) {
			if(fronteiraDeEstados.get(i).getDireita().getMissionarios() + fronteiraDeEstados.get(i).getDireita().getCanibais()
			> melhor.getDireita().getMissionarios() + melhor.getDireita().getCanibais()){
				melhor = fronteiraDeEstados.get(i);
			}
		}
		fronteiraDeEstados.remove(melhor);
		return melhor;
	}
	
	private void expandir(List<Estado> fronteiraDeEstados, List<Movimento> movimentosPassados){
		
		Estado estadoAtual = funcaoHeuristica(fronteiraDeEstados);
		
		if (estadoAtual.testaEstado(movimentosPassados) == false){
			if(!fronteiraDeEstados.isEmpty()) expandir(fronteiraDeEstados, movimentosPassados);
			return;
		}
		
		if(estadoAtual.getUltimoMovimento().getDirecao() == 'D'){
			movimentosPassados.add(new Movimento(estadoAtual.getDireita().getMissionarios(), estadoAtual.getDireita().getCanibais(), 'D'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(estadoAtual.getDireita().getMissionarios() >= i && estadoAtual.getDireita().getCanibais() >=j){
							if (estadoAtual.getUltimoMovimento().getMissionarios() != i || estadoAtual.getUltimoMovimento().getCanibais() != j){
								fronteiraDeEstados.add(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'E'));
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
								fronteiraDeEstados.add(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'D'));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		if(!fronteiraDeEstados.isEmpty()) expandir(fronteiraDeEstados, movimentosPassados);
	}
	
	public void iniciar(Estado estadoInicial){
		this.movimentosPassados.add(new Movimento(estadoInicial.getEsquerda().getMissionarios(), estadoInicial.getEsquerda().getCanibais(), 'E'));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(1, 0, 'D'));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(0, 1, 'D'));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(1, 1, 'D'));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(2, 0, 'D'));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone()).mover(0, 2, 'D'));
		System.out.println("Estado visitado - D:" + estadoInicial.getDireita().getMissionarios() + "M" + estadoInicial.getDireita().getCanibais() + "C"
				+ " E:" + estadoInicial.getEsquerda().getMissionarios() + "M" + estadoInicial.getEsquerda().getCanibais() + "C");
		expandir(fronteiraDeEstados, this.movimentosPassados);
	}
	
	public static void main(String[] args) {
		Estado inicial = new Estado(new Margem(3, 3), new Margem(0, 0));
		buscaGulosa busca = new buscaGulosa();
		busca.iniciar(inicial);
	}
}
