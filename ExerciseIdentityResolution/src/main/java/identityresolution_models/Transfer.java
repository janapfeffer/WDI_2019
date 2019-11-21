package identityresolution_models;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Transfer extends AbstractRecord<Attribute> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clubInName;
	private String clubOutName;
	private int year;
	
	public Transfer(String identifier, String provenance) {
		super(identifier, provenance);
	}
/*	
	public Transfer(String clubInName, String clubOutName, int year){
		this.setClubInName(clubInName);
		this.setClubOutName(clubOutName);
		this.setYear(year);
	}
*/	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 31 + ((clubInName == null) ? 0 : clubInName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transfer other = (Transfer) obj;
		if (clubInName == null) {
			if (other.clubInName != null)
				return false;
		} else if (!clubInName.equals(other.clubInName))
			return false;
		return true;
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
	
	public static final Attribute CLUBINNAME = new Attribute("clubInName");
	public static final Attribute CLUBOUTNAME = new Attribute("clubOutName");
	public static final Attribute YEAR = new Attribute("year");
	
	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==CLUBINNAME)
			return clubInName!=null;
		else if(attribute==CLUBOUTNAME)
			return clubOutName!=null;
		else if(attribute==YEAR)
			return year!=0;
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("[Transfer: %s]", getClubInName(), getClubOutName(), getYear());
	}


}
