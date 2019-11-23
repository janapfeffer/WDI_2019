package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
import identityresolution_models.Player;

public class SpeedEvaluationRule extends EvaluationRule<Player, Attribute>  {

	private static AbsoluteDifferenceSimilarity sim = new AbsoluteDifferenceSimilarity(3);

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// only FIFA19 has the speed
		return record1.getSpeed() == record2.getSpeed();
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// the speed values from the other data source are scaled differently
		return sim.calculate( Double.valueOf(record1.getSpeed()), Double.valueOf(record2.getSpeed())) != 0;
	}

}