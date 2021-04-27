package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> pws;
	private int x;
	private int y;
	private int massimo;
	private List<PowerOutage> ottimo;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> getNercPowerOutage(Nerc n){
		return podao.getNercPowerOutage(n);
	}
	
	public List<PowerOutage> trovaSequenzaOttima(Nerc n, int x, int y){
		
		this.x=x;
		this.y=y;
		pws = podao.getNercPowerOutage(n);
		ottimo = new ArrayList<PowerOutage>();
		massimo = 0;
		
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		
		this.calcolaSequenzaOttima(parziale,0,0);
		
		return ottimo;
		
	}
	
	private void calcolaSequenzaOttima(List<PowerOutage> parziale, int livello, int start){
		
		//caso terminale
		
		int totCostumer = this.calcolaTotCostumer(parziale);
		
		if(totCostumer>massimo) {
			massimo = totCostumer;
			ottimo = new ArrayList<PowerOutage>(parziale);
		}
		
		
		//ricorsione
		
		for(int i=start;i<pws.size();i++) {
			
			//aggiungo e controllo
			parziale.add(pws.get(i));
			
			if(this.controllaOre(parziale) && this.controllaAnno(parziale)) {
				this.calcolaSequenzaOttima(parziale, livello+1,i+1);
			}
			
			parziale.remove(parziale.size()-1); //backtracking
		}
		
		return;
	}
	
	private int calcolaTotCostumer(List<PowerOutage> parziale) {
		
		int tot = 0;
		
		for(PowerOutage pw : parziale) {
			tot += pw.getNumCustomer();
		}
		
		return tot;
	}

	private boolean controllaOre(List<PowerOutage> parziale) {
		
		long tot = 0;
		
		for(PowerOutage pw : parziale) {
			tot += pw.getDurata();
		}
		
		if(tot/3600>y) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean controllaAnno(List<PowerOutage> parziale) {
		
		int min = 0;
		int max = 0;
		
		for(int i=0;i<parziale.size();i++) {
			
			if(i==0) {
				max = parziale.get(i).getDataInizio().getYear();
				min = parziale.get(i).getDataInizio().getYear();
			} else {
				
				if(parziale.get(i).getDataInizio().getYear()>max) {
					max = parziale.get(i).getDataInizio().getYear();
				}
				
				if(parziale.get(i).getDataInizio().getYear()<min) {
					min = parziale.get(i).getDataInizio().getYear();
				}
			}
			

		}
		
		if(max-min<=x) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getMassimo() {
		return this.massimo;
	}
	
	public int calcolaOreTotali(List<PowerOutage> pws) {
		
		int tot = 0;
		
		for(PowerOutage pw : pws) {
			tot += pw.getDurata()/3600;
		}
		
		return tot;
	}
	
	
}
