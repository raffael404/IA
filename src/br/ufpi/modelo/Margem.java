package br.ufpi.modelo;

public class Margem implements Cloneable{
	
	private int missionarios;
	private int canibais;
	
	public int getCanibais() {
		return canibais;
	}
	public int getMissionarios() {
		return missionarios;
	}
	
	public void colocar(int missionarios, int canibais){
		this.missionarios = this.missionarios + missionarios;
		this.canibais = this.canibais + canibais;
	}
	public void retirar(int missionarios, int canibais){
		this.missionarios = this.missionarios - missionarios;
		this.canibais = this.canibais - canibais;
	}
	public Margem(int missionarios, int canibais) {
		this.missionarios = missionarios;
		this.canibais = canibais;
	}
	
	public Margem clone(){
		try {
			return (Margem) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Impossível clonar!!!");
			return this;
		}
	}
}
