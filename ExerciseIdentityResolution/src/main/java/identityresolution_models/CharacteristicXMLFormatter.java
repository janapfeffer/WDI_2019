package identityresolution_models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class CharacteristicXMLFormatter extends XMLFormatter<Characteristic> {
	
	DevelopmentXMLFormatter developmentFormatter = new DevelopmentXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("PlayerCharacteristics");
	}

	@Override
	public Element createElementFromRecord(Characteristic record, Document doc) {
		Element characteristics = doc.createElement("PlayerCharacteristics");

		characteristics.appendChild(createTextElement("Weight", String.valueOf(record.getWeight()), doc));
		characteristics.appendChild(createTextElement("Height", String.valueOf(record.getWeight()), doc));
		characteristics.appendChild(createTextElement("Foot", String.valueOf(record.getWeight()), doc));
		characteristics.appendChild(createTextElement("Speed", String.valueOf(record.getWeight()), doc));
		characteristics.appendChild(createDevelopmentsElement(record, doc));
		
		return characteristics;
	}
	
	protected Element createDevelopmentsElement(Characteristic record, Document doc) {
		Element developmentRoot = developmentFormatter.createRootElement(doc);

		for (Development a : record.getDevelopments()) {
			developmentRoot.appendChild(developmentFormatter
					.createElementFromRecord(a, doc));
		}

		return developmentRoot;
	}

}
