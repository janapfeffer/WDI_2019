package identityresolution_models;

public class Transfer {
	
	private String clubInName;
	private String clubOutName;
	private int year;
	
	public Transfer(String clubInName, String clubOutName, int year){
		this.clubInName = clubInName;
		this.clubOutName = clubOutName;
		this.year= year;
	}
	
	public String getClubInName() {
		return clubInName;
	}
	
	public String getClubOutName() {
		return clubOutName;
	}
	
	public int getYear() {
		return year;
	}

}
