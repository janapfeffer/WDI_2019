package identityresolution_blocking;

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
 * Blocking based on a players birthday in five year intervals.
 */
public class PlayerBlockingKeyByYearGenerator extends
RecordBlockingKeyGenerator<Player, Attribute>{

	@Override
	public void generateBlockingKeys(Player record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Player>> resultCollector) {

		resultCollector.next(new Pair<>(Integer.toString(record.getDateOfBirth().getYear() / 5), record));
	}

}
