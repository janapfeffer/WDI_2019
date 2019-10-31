/* copyright: group 3*/
package identityresolution_models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} which represents an Player
 * 
 * 
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
	
	public Float getHeight() {
		return height;
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
	
	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==NAME)
			return name!=null;
		else if(attribute==DATEOFBIRTH) 
			return DATEOFBIRTH!=null;
		else if(attribute==HEIGHT)
			return HEIGHT!=null;
		return false;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
