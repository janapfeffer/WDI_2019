package models;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * @author group3
 * A {@link XMLMatchableReader} for {@link Player}s.
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
		try {
			player.setCurrentClub(getValueFromChildElement(node, "CurrentClub"));
		} catch (Exception e) {
			// TODO: nothing
		}
		try {
			player.setCurrentNumber(Integer.valueOf(getValueFromChildElement(node, "CurrentNumber")));
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			player.setCurrentPosition(getValueFromChildElement(node, "CurrentPosition"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			player.setHeight(Float.valueOf(getValueFromChildElement(node, "Height")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			player.setWage(Float.valueOf(getValueFromChildElement(node, "WageInEuro")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			player.setWeight(Float.valueOf(getValueFromChildElement(node, "Weight")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			player.setSpeed(Integer.valueOf(getValueFromChildElement(node, "Speed")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			player.setFoot(getValueFromChildElement(node, "Foot"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			player.setPhoto(getValueFromChildElement(node, "Photo"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			player.setNationality(getValueFromChildElement(node, "Nationality"));

		} catch (Exception e) {
			// TODO: handle exception
		}

		// TODO add list attributes: transfers and developments (see MovieXMLReader)

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "dateOfBirth");
			if (date != null && date != "") {
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

