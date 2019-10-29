package identityresolution_models;

public class Development {
	
	private int year;
	private int rating;
	private int potential;
	private String defensiveWorkRate;
	private String attackingWorkRate;
	
	public Development(int year, int rating, int potential, String dwr, String awr){
		this.year = year;
		this.rating = rating;
		this.potential = potential;
		defensiveWorkRate = dwr;
		attackingWorkRate = awr;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getRating() {
		return rating;
	}
	
	public int getPotential() {
		return potential;
	}
	
	public String getDefensiveWorkRate(){
		return defensiveWorkRate;
	}
	
	public String getAttackingWorkRate(){
		return attackingWorkRate;
	}

}
