import java.util.ArrayList;
import java.util.List;


public class Pilha {
	private List<Estado> pilha = new ArrayList<Estado>();
	
	public void empilhar(Estado estado){
		pilha.add(estado);
	}
	
	public Estado desempilhar(){
		Estado estado = pilha.get(pilha.size() - 1);
		pilha.remove(pilha.size() - 1);
		return estado;
	}
	
	public boolean isEmpty(){
		if(pilha.size() == 0) return true;
		else return false;
	}
}