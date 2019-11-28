package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
/**
 * {@link XMLFormatter} for {@link Development}s.
 * 
 * @author Group3
 * 
 */
public class DevelopmentXMLFormatter extends XMLFormatter<Development>{

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Developments");
	}

	@Override
	public Element createElementFromRecord(Development record, Document doc) {
		Element developments = doc.createElement("Developments");

		developments.appendChild(createTextElement("Year", String.valueOf(record.getYear()), doc));
		developments.appendChild(createTextElement("Rating", String.valueOf(record.getRating()), doc));
		developments.appendChild(createTextElement("Potential", String.valueOf(record.getPotential()), doc));
		developments.appendChild(createTextElement("DefensiveWorkRate", String.valueOf(record.getDefensiveWorkRate()), doc));
		developments.appendChild(createTextElement("AttackingWorkRate", String.valueOf(record.getAttackingWorkRate()), doc));

		return developments;
	}

}
