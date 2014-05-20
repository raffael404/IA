package br.ufpi.modelo;

public class Movimento {
	
	private int missionarios, canibais;
	private char direcao;
	
	public Movimento(int missionarios, int canibais, char direcao) {
		this.missionarios = missionarios;
		this.canibais = canibais;
		this.direcao = direcao;
	}

	public int getMissionarios() {
		return missionarios;
	}
	public int getCanibais() {
		return canibais;
	}
	public char getDirecao() {
		return direcao;
	}
}
