package identityresolution_models;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Actor;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.ActorXMLReader;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Player}s.
 * 
 */
public class PlayerXMLReader extends XMLMatchableReader<Player, Attribute> {
	
	protected Element createFloatElement(String name, Float value, Document doc) {
		Element elem = doc.createElement(name);
		if (value != null) {
			elem.appendChild(doc.createTextNode(String.valueOf(value)));
		}
		return elem;
	}

	@Override
	public Player createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Player player = new Player(id, provenanceInfo);

		// fill the attributes
		player.setName(getValueFromChildElement(node, "Name"));
		player.setCurrentClub(getValueFromChildElement(node, "CurrentClub"));
		//List<Characteristic> characteristics = getListFromChildElement(node, "PlayerCharacteristics");
		//(node, "PlayerCharacteristics",
			//	"Height", new CharacteristicXMLFormatter(), provenanceInfo);
		//player.setCharacteristics(characteristics);

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "dateofBirth");
			if (date != null) {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				        .appendPattern("yyyy-MM-dd")
				        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				        .toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse(date, formatter);
				player.setDateOfBirth(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return player;
	}

}

