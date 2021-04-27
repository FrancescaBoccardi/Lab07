package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class PowerOutage {
	
	private int id;
	private int numCustomer;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;	
	private long durata;
	
	public PowerOutage(int id, int numCustomer, LocalDateTime dataInizio, LocalDateTime dataFine) {
		this.id = id;
		this.numCustomer = numCustomer;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		
		this.durata = Duration.between(dataInizio, dataFine).getSeconds();
	}
	
	public int getId() {
		return id;
	}
	
	public int getNumCustomer() {
		return numCustomer;
	}
	
	public LocalDateTime getDataInizio() {
		return dataInizio;
	}
	
	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public long getDurata() {
		return durata;
	}

	public void getDurata(long durata) {
		this.durata = durata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.dataInizio.getYear()+" " + this.dataInizio + " " + this.dataFine + " " + this.durata/3600 + " "
				+ this.numCustomer;
	}

	
	
	
	
	
	
	

}
