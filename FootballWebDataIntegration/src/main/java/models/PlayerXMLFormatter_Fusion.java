package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
/**
 * @author group3
 * 
 * Player XML formatter, used for writing the fusion results.
 */
public class PlayerXMLFormatter_Fusion extends XMLFormatter<Player> {

	TransferXMLFormatter transferFormatter = new TransferXMLFormatter();
	DevelopmentXMLFormatter developmentFormatter = new DevelopmentXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Players");
	}

	@Override
	public Element createElementFromRecord(Player record, Document doc) {
		Element player = doc.createElement("Player");

		player.appendChild(createTextElement("id", record.getIdentifier(), doc));

		player.appendChild(createTextElementWithProvenance("Name",
				record.getName(),
				record.getMergedAttributeProvenance(Player.NAME), doc));
		player.appendChild(createTextElementWithProvenance("dateOfBirth", record
				.getDateOfBirth().toString(), record
				.getMergedAttributeProvenance(Player.DATEOFBIRTH), doc));
		player.appendChild(createTextElementWithProvenance("Nationality",
				record.getNationality(),
				record.getMergedAttributeProvenance(Player.NATIONALITY), doc));
		player.appendChild(createTextElementWithProvenance("Photo",
				record.getPhoto(),
				record.getMergedAttributeProvenance(Player.PHOTO), doc));
		player.appendChild(createTextElementWithProvenance("CurrentClub",
				record.getCurrentClub(),
				record.getMergedAttributeProvenance(Player.CURRENTCLUB), doc));
		player.appendChild(createTextElementWithProvenance("CurrentPosition",
				record.getCurrentPosition(),
				record.getMergedAttributeProvenance(Player.CURRENTPOSITION), doc));
		player.appendChild(createTextElementWithProvenance("WageInEuro",
				record.getWage().toString(),
				record.getMergedAttributeProvenance(Player.WAGE), doc));
		player.appendChild(createTextElementWithProvenance("CurrentNumber",
				String.valueOf(record.getCurrentNumber()),
				record.getMergedAttributeProvenance(Player.CURRENTNUMBER), doc));
		player.appendChild(createTextElementWithProvenance("Height",
				String.valueOf(record.getHeight()),
				record.getMergedAttributeProvenance(Player.HEIGHT), doc));
		player.appendChild(createTextElementWithProvenance("Weight",
				String.valueOf(record.getWeight()),
				record.getMergedAttributeProvenance(Player.WEIGHT), doc));
		player.appendChild(createTextElementWithProvenance("Foot",
				record.getFoot(),
				record.getMergedAttributeProvenance(Player.FOOT), doc));
		player.appendChild(createTextElementWithProvenance("Speed",
				String.valueOf(record.getSpeed()),
				record.getMergedAttributeProvenance(Player.SPEED), doc));

		player.appendChild(createTransfersElement(record, doc));
		player.appendChild(createDevelopmetsElement(record, doc));


		return player;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	protected Element createTransfersElement(Player record, Document doc) {
		Element transferRoot = transferFormatter.createRootElement(doc);
		transferRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(Player.TRANSFERS));

		for (Transfer t : record.getTransfers()) {
			transferRoot.appendChild(transferFormatter
					.createElementFromRecord(t, doc));
		}

		return transferRoot;
	}

	protected Element createDevelopmetsElement(Player record, Document doc) {
		Element developmentRoot = developmentFormatter.createRootElement(doc);
		developmentRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(Player.DEVELOPMENTS));

		for (Development d : record.getDevelopments()) {
			developmentRoot.appendChild(developmentFormatter
					.createElementFromRecord(d, doc));
		}

		return developmentRoot;
	}



}
