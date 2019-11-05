package identityresolution_blocking;


import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import identityresolution_models.Player;

public class PlayerBlockingKeyByHeightGenerator extends
RecordBlockingKeyGenerator<Player, Attribute>{

	@Override
	public void generateBlockingKeys(Player record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Player>> resultCollector) {
		try {
			if(record.getHeight() > 0){
				resultCollector.next(new Pair<>(Integer.toString(Math.round(record.getHeight()) / 10), record));
			} 
		} catch (Exception e) {
			// the player doesn't have a height and therefore will not be put into a block
		}

	}



}
