package identityresolution_models;

import java.util.List;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class CharacteristicsXMLReader extends XMLMatchableReader<Characteristic, Attribute>{

	
	@Override
	public Characteristic createModelFromElement(Node node, String provenanceInfo) {
		String foot = getValueFromChildElement(node, "Foot");
		Float weight = Float.valueOf(getValueFromChildElement(node, "Weight"));
		Float height = Float.valueOf(getValueFromChildElement(node, "Height"));
		int speed = Integer.valueOf(getValueFromChildElement(node, "Speed"));
		//List<Development> developments = getValueFromChildElement(node, "Developments");
		List<Development> developments = null;
		
		Characteristic characteristic = new Characteristic(weight, height, foot, speed, developments);
		
		return characteristic;
	}
}
