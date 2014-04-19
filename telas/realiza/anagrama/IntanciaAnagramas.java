package telas.realiza.anagrama;

import java.util.ArrayList;

import lib.beans.Anagrama;
import lib.beans.ItemRealizaAnagrama;

public class IntanciaAnagramas {
	
	Anagrama anagrama = null;
	ArrayList<ItemRealizaAnagrama> itens = new ArrayList<ItemRealizaAnagrama>();
	
	public Anagrama getAnagrama() {
		return anagrama;
	}
	
	public void setAnagrama(Anagrama anagrama) {
		this.anagrama = anagrama;
	}
	public void addItem(ItemRealizaAnagrama itens) {
		this.itens.add( itens );
	}
	
	public boolean atingiuMeta(){
	   int acertos = 0;
	   
	   for (ItemRealizaAnagrama i : itens) {
         if(i.acertou()){
            acertos++;
         }
      }
	   
	   double porc = (acertos*100) / itens.size();
	   
		return porc >= anagrama.getPorcAcerto();
	}
	
}
