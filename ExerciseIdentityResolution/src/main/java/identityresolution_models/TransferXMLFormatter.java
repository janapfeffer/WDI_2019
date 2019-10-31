package identityresolution_models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class TransferXMLFormatter extends XMLFormatter<Transfer> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Transfers");
	}

	@Override
	public Element createElementFromRecord(Transfer record, Document doc) {
		Element transfers = doc.createElement("Transfers");
		
		transfers.appendChild(createTextElement("Year", String.valueOf(record.getYear()), doc));
		transfers.appendChild(createTextElement("ClubOut", String.valueOf(record.getClubOutName()), doc));
		transfers.appendChild(createTextElement("ClubIn", String.valueOf(record.getClubInName()), doc));
		
		return transfers;
	}

}
