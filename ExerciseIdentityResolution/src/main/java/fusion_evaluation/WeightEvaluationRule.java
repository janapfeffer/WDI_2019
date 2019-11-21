package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
import identityresolution_models.Player;

public class WeightEvaluationRule extends EvaluationRule<Player, Attribute>  {
	private static AbsoluteDifferenceSimilarity sim = new AbsoluteDifferenceSimilarity(2);

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// we tolerate a difference of 2kg
		if (record1.getWeight() != null && record2.getWeight() != null){
			return sim.calculate((double) record1.getWeight(), (double) record2.getWeight()) == 1;
		} else {
			return false;
		}

	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// we tolerate a difference of 2kg
		if (record1.getWeight() != null && record2.getWeight() != null){
			return sim.calculate((double) record1.getWeight(), (double) record2.getWeight()) == 1;
		} else {
			return false;
		}
	}

}