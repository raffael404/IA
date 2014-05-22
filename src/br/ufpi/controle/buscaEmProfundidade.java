package br.ufpi.controle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import br.ufpi.modelo.Estado;
import br.ufpi.modelo.Movimento;
import br.ufpi.modelo.Pilha;


public class buscaEmProfundidade {
	private Pilha pilhaDeEstados;
	private List<Movimento> movimentosPassados;
	private JTextArea textArea;
	
	public buscaEmProfundidade(JTextArea textArea) {
		this.pilhaDeEstados = new Pilha();
		this.movimentosPassados = new ArrayList<Movimento>();
		this.textArea = textArea;
	}
	
	private void expandir(Estado estadoAtual, Pilha pilha, List<Movimento> movimentosPassados){
		
		if (estadoAtual.testaEstado(movimentosPassados) == false){
			if(!pilhaDeEstados.isEmpty()) expandir(pilhaDeEstados.desempilhar(), pilhaDeEstados, movimentosPassados);
			return;
		}
		
		if(estadoAtual.getUltimoMovimento().getDirecao() == 'D'){
			movimentosPassados.add(new Movimento(estadoAtual.getDireita().getMissionarios(), estadoAtual.getDireita().getCanibais(), 'D'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j == 1 || i + j == 2){
						if(estadoAtual.getDireita().getMissionarios() >= i && estadoAtual.getDireita().getCanibais() >=j){
							if (estadoAtual.getUltimoMovimento().getMissionarios() != i || estadoAtual.getUltimoMovimento().getCanibais() != j){
								pilhaDeEstados.empilhar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone(), textArea).mover(i, j, 'E', estadoAtual.getDistancia()));
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
								pilhaDeEstados.empilhar(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone(), textArea).mover(i, j, 'D', estadoAtual.getDistancia()));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		if(!pilhaDeEstados.isEmpty()) expandir(pilhaDeEstados.desempilhar(), pilhaDeEstados, movimentosPassados);
	}
	
	public void iniciar(Estado estadoInicial){
		movimentosPassados.add(new Movimento(estadoInicial.getEsquerda().getMissionarios(), estadoInicial.getEsquerda().getCanibais(), 'E'));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(1, 0, 'D', 0));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(0, 1, 'D', 0));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(1, 1, 'D', 0));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(2, 0, 'D', 0));
		pilhaDeEstados.empilhar(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(0, 2, 'D', 0));
		textArea.setText("Estado visitado - D:" + estadoInicial.getDireita().getMissionarios() + "M" + estadoInicial.getDireita().getCanibais() + "C"
				+ " E:" + estadoInicial.getEsquerda().getMissionarios() + "M" + estadoInicial.getEsquerda().getCanibais() + "C");
		expandir(pilhaDeEstados.desempilhar(), pilhaDeEstados, movimentosPassados);
	}
}
