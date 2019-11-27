package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
import identityresolution_models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the weight.
 * We tolerate a difference of 2kg
 */
public class WeightEvaluationRule extends EvaluationRule<Player, Attribute>  {

	// we tolerate a difference of 2kg
	private static AbsoluteDifferenceSimilarity sim = new AbsoluteDifferenceSimilarity(2);

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		//is both values are not null, set true if sim measure is not 0 (values are in tolerated range)
		//set false if the sim measure is 0 (values are out of tolerated range)
		if (record1.getWeight() != null && record2.getWeight() != null)
			return sim.calculate((double) record1.getWeight(), (double) record2.getWeight()) != 0;
		//if values both null, consider they are equal
		else if (record1.getWeight() == null && record2.getWeight() == null)
			return true;
		//if one of the value is null - not equal
		else
			return false;


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