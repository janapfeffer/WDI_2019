package identityresolution_models;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class TransferXMLReader extends XMLMatchableReader<Transfer, Attribute>{

	//@Override
	public Transfer createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Transfer transfer = new Transfer(id, provenanceInfo);

		// fill the attributes
		transfer.setClubInName(getValueFromChildElement(node, "ClubInName"));
		transfer.setClubOutName(getValueFromChildElement(node, "ClubOutName"));
		transfer.setYear(Integer.valueOf(getValueFromChildElement(node, "Year")));
		

		return transfer;
	}

}
