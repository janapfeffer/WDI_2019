package identityresolution_blocking;


import java.text.Normalizer;

import org.apache.commons.lang3.StringEscapeUtils;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import models.Player;
/**
 * @author group3
 * 
 * Blocking based on the first character of the first name of a player.
 */
public class PlayerBlockingFirstnameGenerator extends RecordBlockingKeyGenerator<Player, Attribute> {

	@Override
	public void generateBlockingKeys(Player record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Player>> resultCollector) {

		// normalize name
		String name = Normalizer.normalize(StringEscapeUtils.unescapeHtml4(record.getName()), Normalizer.Form.NFD).
				replaceAll("[^\\p{ASCII}]", "").replace("'", "").replace(".", "").toLowerCase().replaceAll("\\s+", " ").trim();
		// extract first letter of first name
		String firstname = "";
		try {
			firstname = name.substring(0, 1);
		} catch (Exception e) {
			
		}


		// create pair
		resultCollector.next(new Pair<>(firstname, record));

	}

}
