package br.ufpi.controle;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import br.ufpi.modelo.Estado;
import br.ufpi.modelo.Margem;
import br.ufpi.modelo.Movimento;

public class buscaAEstrela {
	private List<Estado> fronteiraDeEstados;
	private List<Movimento> movimentosPassados;
	private JTextArea textArea;
	
	public buscaAEstrela(JTextArea textArea){
		this.fronteiraDeEstados = new ArrayList<Estado>();
		this.movimentosPassados = new ArrayList<Movimento>();
		this.textArea = textArea;
	}
	
	private Estado funcaoHeuristica(List<Estado> fronteiraDeEstados){
		Estado melhor = new Estado(new Margem(100, 100), new Margem(-1, -1), textArea);
		for (int i = 0; i < fronteiraDeEstados.size(); i++) {
			if(fronteiraDeEstados.get(i).getEsquerda().getMissionarios()
			+ fronteiraDeEstados.get(i).getEsquerda().getCanibais() 
			+ fronteiraDeEstados.get(i).getDistancia()
			< melhor.getEsquerda().getMissionarios()
			+ melhor.getEsquerda().getCanibais()
			+ melhor.getDistancia())
				melhor = fronteiraDeEstados.get(i);
			else if(fronteiraDeEstados.get(i).getEsquerda().getMissionarios()
					+ fronteiraDeEstados.get(i).getEsquerda().getCanibais() 
					+ fronteiraDeEstados.get(i).getDistancia()
					== melhor.getEsquerda().getMissionarios()
					+ melhor.getEsquerda().getCanibais()
					+ melhor.getDistancia()){
						if(melhor.getUltimoMovimento().getDirecao() == 'D' 
						&& fronteiraDeEstados.get(i).getUltimoMovimento().getDirecao() == 'E') melhor = fronteiraDeEstados.get(i);
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
		
		textArea.setText(textArea.getText() + "\nEstado(s) filho(s):");
		if(estadoAtual.getUltimoMovimento().getDirecao() == 'D'){
			movimentosPassados.add(new Movimento(estadoAtual.getDireita().getMissionarios(), estadoAtual.getDireita().getCanibais(), 'D'));
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if(i + j >= 1 && i + j <= 2){
						if(estadoAtual.getDireita().getMissionarios() >= i && estadoAtual.getDireita().getCanibais() >=j){
							if (estadoAtual.getUltimoMovimento().getMissionarios() != i || estadoAtual.getUltimoMovimento().getCanibais() != j){
								textArea.setText(textArea.getText() + "\nE:" + (estadoAtual.getEsquerda().getMissionarios() + i) + "M" + (estadoAtual.getEsquerda().getCanibais() + j) + "C"
										+ " D:" + (estadoAtual.getDireita().getMissionarios() - i) + "M" + (estadoAtual.getDireita().getCanibais() - j) + "C");
								fronteiraDeEstados.add(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone(), textArea).mover(i, j, 'E', estadoAtual.getDistancia()));
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
								textArea.setText(textArea.getText() + "\nE:" + (estadoAtual.getEsquerda().getMissionarios() - i) + "M" + (estadoAtual.getEsquerda().getCanibais() - j) + "C"
										+ " D:" + (estadoAtual.getDireita().getMissionarios() + i) + "M" + (estadoAtual.getDireita().getCanibais() + j) + "C");
								fronteiraDeEstados.add(new Estado(estadoAtual.getEsquerda().clone(), estadoAtual.getDireita().clone(), textArea).mover(i, j, 'D', estadoAtual.getDistancia()));
							}//else System.out.println("Movimento repetido!!!");
						}
					}
				}
			}
		}
		textArea.setText(textArea.getText() + "\n");
		if(!fronteiraDeEstados.isEmpty()) expandir(fronteiraDeEstados, movimentosPassados);
	}

	public void iniciar(Estado estadoInicial){
		movimentosPassados.add(new Movimento(estadoInicial.getEsquerda().getMissionarios(), estadoInicial.getEsquerda().getCanibais(), 'E'));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(1, 0, 'D', 0));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(0, 1, 'D', 0));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(1, 1, 'D', 0));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(2, 0, 'D', 0));
		fronteiraDeEstados.add(new Estado(estadoInicial.getEsquerda().clone(), estadoInicial.getDireita().clone(), textArea).mover(0, 2, 'D', 0));
		textArea.setText("Estado inicial - E:" + estadoInicial.getEsquerda().getMissionarios() + "M" + estadoInicial.getEsquerda().getCanibais()
				+ " <-- D:" + estadoInicial.getDireita().getMissionarios() + "M" + estadoInicial.getDireita().getCanibais() + "C"
				+ "\nEstados filhos: " + "\nE:" + (estadoInicial.getEsquerda().getMissionarios()-1) + "M" + estadoInicial.getEsquerda().getCanibais() + "C"
				+ " D:" + (estadoInicial.getDireita().getMissionarios()+1) + "M" + estadoInicial.getDireita().getCanibais() + "C"
				+ "\nE:" + estadoInicial.getEsquerda().getMissionarios() + "M" + (estadoInicial.getEsquerda().getCanibais()-1) + "C"
				+ " D:" + estadoInicial.getDireita().getMissionarios() + "M" + (estadoInicial.getDireita().getCanibais()+1) + "C"
				+ "\nE:" + (estadoInicial.getEsquerda().getMissionarios()-1) + "M" + (estadoInicial.getEsquerda().getCanibais()-1) + "C"
				+ " D:" + (estadoInicial.getDireita().getMissionarios()+1) + "M" + (estadoInicial.getDireita().getCanibais()+1) + "C"
				+ "\nE:" + (estadoInicial.getEsquerda().getMissionarios()-2) + "M" + estadoInicial.getEsquerda().getCanibais() + "C"
				+ " D:" + (estadoInicial.getDireita().getMissionarios()+2) + "M" + estadoInicial.getDireita().getCanibais() + "C"
				+ "\nE:" + estadoInicial.getEsquerda().getMissionarios() + "M" + (estadoInicial.getEsquerda().getCanibais()-2) + "C"
				+ " D:" + estadoInicial.getDireita().getMissionarios() + "M" + (estadoInicial.getDireita().getCanibais()+2) + "C\n");
		expandir(fronteiraDeEstados, movimentosPassados);
	}
}
