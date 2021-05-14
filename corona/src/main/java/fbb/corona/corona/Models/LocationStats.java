package fbb.corona.corona.Models;

public class LocationStats {

    private String state;
    private int totalCases;
    private int deaths;
    private int newCases;
    private int newDeaths;
    private int recovered;
    
	public int getRecovered() {
		return recovered;
	}
	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getNewCases() {
		return newCases;
	}
	public void setNewCases(int newCases) {
		this.newCases = newCases;
	}
	public int getNewDeaths() {
		return newDeaths;
	}
	public void setNewDeaths(int newDeaths) {
		this.newDeaths = newDeaths;
	}
	
    
}