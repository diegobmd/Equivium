package telas.realiza.sessao;

import lib.beans.SubFase;

public class InstanciasSubFase {
	
	private final SubFase sub;
	private int acertos = 0;
	
	public InstanciasSubFase(SubFase sub){
		this.sub = sub;
	}
	
	public void addAcerto(){
		acertos++;
	}
	
	public boolean atingiuMeta(){
		double percAcerto = ((double)acertos/sub.getQuantidade())*100;
		return percAcerto >= sub.getAcerto();
	}
	
	public SubFase getSubFase(){
		return sub;
	}
}
