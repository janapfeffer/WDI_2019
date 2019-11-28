package models;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
/**
 * @author group3
 * A {@link XMLMatchableReader} for {@link Development}s.
 */
public class DevelopmentXMLReader extends XMLMatchableReader<Development, Attribute>{

	@Override
	public Development createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Development development = new Development(id, provenanceInfo);

		// fill the attributes
		development.setPotential(Integer.valueOf(getValueFromChildElement(node, "Potential")));
		development.setRating(Integer.valueOf(getValueFromChildElement(node, "Rating")));
		development.setDefensiveWorkRate(getValueFromChildElement(node,"DefensiveWorkRate"));
		development.setAttackingWorkRate(getValueFromChildElement(node,"AttackingWorkRate"));
		try {
			development.setYear(Integer.valueOf(getValueFromChildElement(node, "Year")));
		} catch (Exception e) {
		}



		return development;
	}

}
