package br.ufpi.modelo;
import java.util.ArrayList;
import java.util.List;

public class Fila {
	private List<Estado> fila = new ArrayList<Estado>();
	
	public void enfileirar(Estado estado){
		fila.add(estado);
	}
	
	public Estado desenfileirar(){
		Estado estado = fila.get(0);
		fila.remove(0);
		return estado;
	}
	
	public boolean isEmpty(){
		if(fila.size() == 0) return true;
		else return false;
	}
}
