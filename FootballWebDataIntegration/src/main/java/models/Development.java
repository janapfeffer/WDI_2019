package models;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
/**
 * @author group3
 * 
 * A {@link AbstractRecord} which represents a development.
 */
public class Development extends AbstractRecord<Attribute> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int year;
	private int rating;
	private int potential;
	private String defensiveWorkRate;
	private String attackingWorkRate;

	public Development(String identifier, String provenance) {
		super();
		//super(identifier, provenance)
	}

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

	public static final Attribute RATING = new Attribute("rating");
	public static final Attribute POTENTIAL = new Attribute("potential");
	public static final Attribute DEFENSIVEWORKRATE = new Attribute("defensiveWorkRate");
	public static final Attribute ATTACKINGWORKRATE = new Attribute("attackingWorkRate");
	public static final Attribute YEAR = new Attribute("year");


	public boolean hasValue(Attribute attribute) {
		if(attribute==RATING)
			return rating!=0;
		else if(attribute==POTENTIAL)
			return potential!=0;
		else if(attribute==DEFENSIVEWORKRATE)
			return defensiveWorkRate!=null;
		else if(attribute==ATTACKINGWORKRATE)
			return attackingWorkRate!=null;
		else if(attribute==YEAR)
			return year!=0;
		return false;
	}

	@Override
	public String toString() {
		return String.format("[Development: %s]", getYear(), getRating(), getPotential(), getDefensiveWorkRate(), getAttackingWorkRate() );
	}

}
