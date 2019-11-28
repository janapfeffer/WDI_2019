package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
/**
 * {@link XMLFormatter} for {@link Transfer}s.
 * 
 * @author Group3
 * 
 */
public class TransferXMLFormatter extends XMLFormatter<Transfer> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Transfers");
	}

	@Override
	public Element createElementFromRecord(Transfer record, Document doc) {
		Element transfer = doc.createElement("Transfer");

		transfer.appendChild(createTextElement("Year", String.valueOf(record.getYear()), doc));
		transfer.appendChild(createTextElement("ClubOut", String.valueOf(record.getClubOutName()), doc));
		transfer.appendChild(createTextElement("ClubIn", String.valueOf(record.getClubInName()), doc));

		return transfer;
	}

}
