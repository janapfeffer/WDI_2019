package models;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
/**
 * @author group3
 * 
 * Player XML reader, reads the player files.
 */
public class PlayerXMLReader_Fusion extends XMLMatchableReader<Player, Attribute> implements FusibleFactory<Player, Attribute>{

	@Override
	protected void initialiseDataset(DataSet<Player, Attribute> dataset) {
		super.initialiseDataset(dataset);

		dataset.addAttribute(Player.CURRENTCLUB);
		dataset.addAttribute(Player.CURRENTNUMBER);
		dataset.addAttribute(Player.CURRENTPOSITION);
		dataset.addAttribute(Player.DATEOFBIRTH);
		dataset.addAttribute(Player.DEVELOPMENTS);
		dataset.addAttribute(Player.FOOT);
		dataset.addAttribute(Player.HEIGHT);
		dataset.addAttribute(Player.NAME);
		dataset.addAttribute(Player.NATIONALITY);
		dataset.addAttribute(Player.PHOTO);
		dataset.addAttribute(Player.SPEED);
		dataset.addAttribute(Player.TRANSFERS);
		dataset.addAttribute(Player.WAGE);
		dataset.addAttribute(Player.WEIGHT);

	}

	@Override
	public Player createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		Player player = new Player(id, provenanceInfo);
		// fill the attributes, if it cannot be filled it is null 
		// and nothing has to be done, it is left empty
		player.setName(getValueFromChildElement(node, "Name"));
		try {
			player.setCurrentClub(getValueFromChildElement(node, "CurrentClub"));
		} catch (Exception e) {

		}
		try {
			player.setCurrentNumber(Integer.valueOf(getValueFromChildElement(node, "CurrentNumber")));
		} catch (Exception e) {

		}

		try {
			player.setCurrentPosition(getValueFromChildElement(node, "CurrentPosition"));
		} catch (Exception e) {

		}
		try {
			player.setHeight(Float.valueOf(getValueFromChildElement(node, "Height")));
		} catch (Exception e) {

		}
		try {
			player.setWage(Float.valueOf(getValueFromChildElement(node, "WageInEuro")));
		} catch (Exception e) {

		}
		try {
			player.setWeight(Float.valueOf(getValueFromChildElement(node, "Weight")));
		} catch (Exception e) {

		}
		try {
			player.setSpeed(Integer.valueOf(getValueFromChildElement(node, "Speed")));
		} catch (Exception e) {

		}
		try {
			player.setFoot(getValueFromChildElement(node, "Foot"));
		} catch (Exception e) {

		}
		try {
			player.setPhoto(getValueFromChildElement(node, "Photo"));
		} catch (Exception e) {

		}
		try {
			player.setNationality(getValueFromChildElement(node, "Nationality"));

		} catch (Exception e) {
			;
		}

		List<Transfer> transfers = getObjectListFromChildElement(node, "Transfers",
				"Transfer", new TransferXMLReader(), provenanceInfo);
		player.setTransfers(transfers);

		List<Development> developments = getObjectListFromChildElement(node, "Developments",
				"Development", new DevelopmentXMLReader(), provenanceInfo);
		player.setDevelopments(developments);

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "dateOfBirth").split("T")[0];
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

	@Override
	public Player createInstanceForFusion(RecordGroup<Player, Attribute> cluster) {
		List<String> ids = new LinkedList<>();

		for (Player p : cluster.getRecords()) {
			ids.add(p.getIdentifier());
		}

		Collections.sort(ids);

		String mergedId = StringUtils.join(ids, '+');

		return new Player(mergedId, "fused");
	}

}
