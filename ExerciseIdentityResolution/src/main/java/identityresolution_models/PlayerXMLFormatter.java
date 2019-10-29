package identityresolution_models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Actor;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Movie;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Player}s.
 * 
 * @author Group 3
 * 
 */
public class PlayerXMLFormatter extends XMLFormatter<Player> {
	
	CharacteristicXMLFormatter characteristicsFormatter = new CharacteristicXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Players");
	}

	@Override
	public Element createElementFromRecord(Player record, Document doc) {
		Element Player = doc.createElement("Player");

		Player.appendChild(createTextElement("Name", record.getName(), doc));
		Player.appendChild(createTextElement("dateOfBirth", String.valueOf(record.getDateOfBirth()), doc));
		Player.appendChild(createTextElement("Nationality", record.getNationality(), doc));
		Player.appendChild(createTextElement("Photo", record.getPhoto(), doc));
		Player.appendChild(createTextElement("CurrentClub", record.getCurrentClub(), doc));
		Player.appendChild(createTextElement("CurrentPosition", record.getCurrentPosition(), doc));
		Player.appendChild(createTextElement("CurrentNumber", String.valueOf(record.getCurrentNumber()), doc));
		Player.appendChild(createTextElement("WageInEuro", String.valueOf(record.getWage()), doc));

		return Player;
	}
	
	protected Element createCharacteristicsElement(Player record, Document doc) {
		Element characteristicRoot = characteristicsFormatter.createRootElement(doc);

		for (Characteristic a : record.getCharacteristics()) {
			characteristicRoot.appendChild(characteristicsFormatter
					.createElementFromRecord(a, doc));
		}

		return characteristicRoot;
	}

}
