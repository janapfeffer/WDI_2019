package identityresolution_models;

public class Development {
	
	private int year;
	private int rating;
	private int potential;
	private String defensiveWorkRate;
	private String attackingWorkRate;
	
	public Development(String identifier, String provenance) {
		super();
		//super(identifier, provenance)
	}
	
	/*	public Development(int year, int rating, int potential, String dwr, String awr){
		this.year = year;
		this.rating = rating;
		this.potential = potential;
		defensiveWorkRate = dwr;
		attackingWorkRate = awr;
	}
	*/
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public int getPotential() {
		return potential;
	}
	public void setPotential(int potential) {
		this.potential = potential;
	}
	
	public String getDefensiveWorkRate(){
		return defensiveWorkRate;
	}
	public void setDefensiveWorkRate(String defensiveWorkRate) {
		this.defensiveWorkRate = defensiveWorkRate;
	}
	
	public String getAttackingWorkRate(){
		return attackingWorkRate;
	}
	public void setAttackingWorkRate(String attackingWorkRate) {
		this.attackingWorkRate = attackingWorkRate;
		
	}

}
