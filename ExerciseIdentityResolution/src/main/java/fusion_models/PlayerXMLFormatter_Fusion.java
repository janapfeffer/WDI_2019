package fusion_models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import identityresolution_models.Player;
import identityresolution_models.Transfer;
import identityresolution_models.TransferXMLFormatter;

public class PlayerXMLFormatter_Fusion extends XMLFormatter<Player> {

	TransferXMLFormatter transferFormatter = new TransferXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("players");
	}

	@Override
	public Element createElementFromRecord(Player record, Document doc) {
		Element player = doc.createElement("player");

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

		for (Transfer a : record.getTransfers()) {
			transferRoot.appendChild(transferFormatter
					.createElementFromRecord(a, doc));
		}

		return transferRoot;
	}



}
