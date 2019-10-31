package identityresolution_models;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Transfer {//extends AbstractRecord<Attribute> implements Serializable{
	
	private String clubInName;
	private String clubOutName;
	private int year;
	
	public Transfer(String clubInName, String clubOutName, int year){
		this.setClubInName(clubInName);
		this.setClubOutName(clubOutName);
		this.setYear(year);
	}

	public String getClubInName() {
		return clubInName;
	}

	public void setClubInName(String clubInName) {
		this.clubInName = clubInName;
	}

	public String getClubOutName() {
		return clubOutName;
	}

	public void setClubOutName(String clubOutName) {
		this.clubOutName = clubOutName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}


}
