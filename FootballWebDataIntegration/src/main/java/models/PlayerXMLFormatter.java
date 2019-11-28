package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Player}s.
 * 
 * @author Group3
 * 
 */
public class PlayerXMLFormatter extends XMLFormatter<Player> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Players");
	}

	@Override
	public Element createElementFromRecord(Player record, Document doc) {
		Element Player = doc.createElement("Player");

		Player.appendChild(createTextElement("id", record.getIdentifier(), doc));
		Player.appendChild(createTextElement("Name", record.getName(), doc));
		Player.appendChild(createTextElement("dateOfBirth", String.valueOf(record.getDateOfBirth()), doc));
		Player.appendChild(createTextElement("Nationality", record.getNationality(), doc));
		Player.appendChild(createTextElement("Photo", record.getPhoto(), doc));
		Player.appendChild(createTextElement("CurrentClub", record.getCurrentClub(), doc));
		Player.appendChild(createTextElement("CurrentPosition", record.getCurrentPosition(), doc));
		Player.appendChild(createTextElement("CurrentNumber", String.valueOf(record.getCurrentNumber()), doc));
		Player.appendChild(createTextElement("WageInEuro", String.valueOf(record.getWage()), doc));
		Player.appendChild(createTextElement("Weight", String.valueOf(record.getWeight()), doc));
		Player.appendChild(createTextElement("Height", String.valueOf(record.getHeight()), doc));
		Player.appendChild(createTextElement("Foot", String.valueOf(record.getFoot()), doc));
		Player.appendChild(createTextElement("Speed", String.valueOf(record.getSpeed()), doc));

		return Player;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

}
