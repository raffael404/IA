package br.ufpi.controle;
import java.util.ArrayList;
import java.util.List;

import br.ufpi.modelo.Estado;
import br.ufpi.modelo.Fila;
import br.ufpi.modelo.Movimento;


public class buscaEmLargura {
	private Fila filaDeEstados;
	private List<Movimento> movimentosPassados;
	private int tamFronteira;
	private int qtdVisitados;
	private StringBuffer log;
	
	public buscaEmLargura() {
		filaDeEstados = new Fila();
		movimentosPassados = new ArrayList<Movimento>();
		this.log = new StringBuffer();
	}
	
	private void expandir(){
		qtdVisitados = qtdVisitados + 1;
		if(filaDeEstados.size() > tamFronteira)
			tamFronteira = filaDeEstados.size();
		Estado estadoAtual = filaDeEstados.desenfileirar();
		
		if (estadoAtual.testaEstado(movimentosPassados, log) == false){
			if(!filaDeEstados.isEmpty() && estadoAtual.getEsquerda().getMissionarios() + estadoAtual.getEsquerda().getCanibais() != 0)
				expandir();
			else log.append("\nTamanho máximo da fronteira de estados: " + tamFronteira + "\nQuantidade de estados visitados: " + qtdVisitados);
			return;
		}
		
		log.append("\nEstado(s) filho(s):");
		if(estadoAtual.getUltimoMovimento().getDirecao() == 'D'){
			movimentosPassados.add(new Movimento(estadoAtual.getDireita().getMissionarios(), estadoAtual.getDireita().getCanibais(), 'D'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(estadoAtual.getDireita().getMissionarios() >= i && estadoAtual.getDireita().getCanibais() >=j){
							if (estadoAtual.getUltimoMovimento().getMissionarios() != i || estadoAtual.getUltimoMovimento().getCanibais() != j){
								log.append("\nE:" + (estadoAtual.getEsquerda().getMissionarios() + i) + "M" + (estadoAtual.getEsquerda().getCanibais() + j) + "C"
										+ " D:" + (estadoAtual.getDireita().getMissionarios() - i) + "M" + (estadoAtual.getDireita().getCanibais() - j) + "C");
								filaDeEstados.enfileirar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'E', estadoAtual.getDistancia()));
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
								log.append("\nE:" + (estadoAtual.getEsquerda().getMissionarios() - i) + "M" + (estadoAtual.getEsquerda().getCanibais() - j) + "C"
										+ " D:" + (estadoAtual.getDireita().getMissionarios() + i) + "M" + (estadoAtual.getDireita().getCanibais() + j) + "C");
								filaDeEstados.enfileirar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone()).mover(i, j, 'D', estadoAtual.getDistancia()));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		log.append("\n");
		if(!filaDeEstados.isEmpty() && estadoAtual.getEsquerda().getMissionarios() + estadoAtual.getEsquerda().getCanibais() != 0)
			expandir();
		else log.append("\nTamanho máximo da fronteira de estados: " + tamFronteira + "\nQuantidade de estados visitados: " + qtdVisitados);
	}

	public StringBuffer iniciar(Estado estadoInicial){
		filaDeEstados.enfileirar(estadoInicial);
		expandir();
		return log;
	}
}
