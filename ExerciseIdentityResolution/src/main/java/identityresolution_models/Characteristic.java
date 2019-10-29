package identityresolution_models;

import java.io.Serializable;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Characteristic extends AbstractRecord<Attribute> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Float weight;
	private Float height;
	private String foot;
	private int speed;
	private List<Development> developments;
	
	public Characteristic(Float weight, Float height, String foot, int speed, List<Development> developments){
		this.weight = weight;
		this.height = height;
		this.foot = foot;
		this.speed = speed;
		this.developments = developments;
	}
	
	public Float getWeight() {
		return weight;
	}
	
	public Float getHeight() {
		return height;
	}
	
	public String getFoot(){
		return foot;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public List<Development> getDevelopments(){
		return developments;
	}
	
	public static final Attribute WEIGHT = new Attribute("Weight");
	public static final Attribute HEIGHT = new Attribute("Height");
	public static final Attribute FOOT = new Attribute("Foot");
	public static final Attribute SPEED = new Attribute("Speed");
	
	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==WEIGHT)
			return weight!=null;
		else if(attribute==HEIGHT) 
			return height!=null;
		else if(attribute==FOOT)
			return foot!=null;
		else if(attribute==SPEED)
			return String.valueOf(speed)!=null;
		return false;
	}

}
