package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		Nerc n = model.getNercList().get(1);
		
		PowerOutage pw = model.getNercPowerOutage(n).get(1);
		
		System.out.println(pw.getDurata());

	}
	
	

}
