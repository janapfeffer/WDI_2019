package identityresolution_models;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class DevelopmentXMLReader extends XMLMatchableReader<Development, Attribute>{
	
	@Override
	public Development createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Development developments = new Development(id, provenanceInfo);

		// fill the attributes
		developments.setPotential(Integer.valueOf(getValueFromChildElement(node, "Potential")));
		developments.setRating(Integer.valueOf(getValueFromChildElement(node, "Rating")));
		developments.setDefensiveWorkRate(getValueFromChildElement(node,"Defensive Work Rate"));
		developments.setAttackingWorkRate(getValueFromChildElement(node,"Attacking Work Rate"));
		try {
			developments.setYear(Integer.valueOf(getValueFromChildElement(node, "Year")));
		} catch (Exception e) {
		}
		
		

		return developments;
	}

}
