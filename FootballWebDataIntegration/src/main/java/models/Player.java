/* copyright: group 3*/
package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.HashMap;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * @author group3
 * A {@link AbstractRecord} which represents a Player
 */
public class Player extends AbstractRecord<Attribute> implements Serializable {

	/*
	 */

	private static final long serialVersionUID = 1L;
	private String name;
	private LocalDateTime dateOfBirth;
	private String nationality;
	private String photo;
	private String currentClub;
	private String currentPosition;
	private Float wage;
	private int currentNumber;
	private Float height;
	private Float weight;
	private String foot;
	private int speed;
	private List<Transfer> transfers;
	private List<Development> developments;


	public Player(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setNationality(String nationality){
		this.nationality = nationality;
	}

	public String getNationality(){
		return nationality;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setCurrentClub(String currentClub){
		this.currentClub = currentClub;
	}

	public String getCurrentClub(){
		return currentClub;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getCurrentPosition(){
		return currentPosition;
	}

	public void setWage(Float wage){
		this.wage = wage;
	}

	public Float getWage(){
		return wage;
	}

	public void setCurrentNumber(int currentNumber){
		this.currentNumber = currentNumber;
	}

	public int getCurrentNumber(){
		return currentNumber;
	}

	public void setTransfers(List<Transfer> transfers){
		this.transfers = transfers;
	}

	public List<Transfer> getTransfers(){
		return transfers;
	}

	public float getHeight() {
		float helper = 0.0f;
		try {
			helper = height;
		} catch (Exception e) {
			
		}
		return helper;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public List<Development> getDevelopments() {
		return developments;
	}

	public void setDevelopments(List<Development> developments) {
		this.developments = developments;
	}

	public String getFoot() {
		return foot;
	}

	public void setFoot(String foot) {
		this.foot = foot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 31 + ((name == null) ? 0 : name.hashCode());
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
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static final Attribute NAME = new Attribute("Name");
	public static final Attribute DATEOFBIRTH = new Attribute("DateOfBirth");
	public static final Attribute HEIGHT = new Attribute("Height");
	public static final Attribute NATIONALITY = new Attribute("Nationality");
	public static final Attribute PHOTO = new Attribute("Photo");
	public static final Attribute CURRENTCLUB = new Attribute("CurrentClub");
	public static final Attribute CURRENTPOSITION = new Attribute("CurrentPosition");
	public static final Attribute WAGE = new Attribute("WageInEuro");
	public static final Attribute CURRENTNUMBER = new Attribute("CurrentNumber");
	public static final Attribute WEIGHT = new Attribute("Weight");
	public static final Attribute FOOT = new Attribute("Foot");
	public static final Attribute SPEED = new Attribute("Speed");
	public static final Attribute TRANSFERS = new Attribute("Transfers");
	public static final Attribute DEVELOPMENTS = new Attribute("Developments");




	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==NAME)
			return getName()!=null && !getName().isEmpty();
		else if(attribute==DATEOFBIRTH) 
			return getDateOfBirth()!=null;
		else if(attribute==HEIGHT)
			return getHeight()!=0.0f && getHeight()!=0;
		else if(attribute==WEIGHT)
			return getWeight()!=null && getWeight()!=0;
		else if(attribute==CURRENTCLUB)
			return getCurrentClub()!=null;
		else if(attribute==CURRENTNUMBER)
			return getCurrentNumber()!=0;
		else if(attribute==CURRENTPOSITION)
			return getCurrentPosition()!=null;
		else if(attribute==DEVELOPMENTS)
			return getDevelopments()!=null;
		else if(attribute==FOOT)
			return getFoot()!=null;
		else if(attribute==NATIONALITY)
			return getNationality()!=null;
		else if(attribute==PHOTO)
			return getPhoto()!=null && getPhoto() != "";
		else if(attribute==SPEED)
			return getSpeed()!=0;
		else if(attribute==TRANSFERS)
			return getTransfers()!=null;
		else if(attribute==WAGE)
			return getWage()!=null && getWage()!=0;
		return false;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return String.format("[Player: %s]", getName());
	}
	private Map<Attribute, Collection<String>> provenance = new HashMap<>();
	private Collection<String> recordProvenance;

	public void setRecordProvenance(Collection<String> provenance) {
		recordProvenance = provenance;
	}

	public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}


	public void setAttributeProvenance(Attribute attribute,
			Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}
}
