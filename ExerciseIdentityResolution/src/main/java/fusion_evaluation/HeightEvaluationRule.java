package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
import identityresolution_models.Player;

public class HeightEvaluationRule extends EvaluationRule<Player, Attribute>{
	// the height is correct if it does not go out of the range +/- 2cm
	private static AbsoluteDifferenceSimilarity sim = new AbsoluteDifferenceSimilarity(2);
	
	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		//if values are not null, set true if sim measure is not 0 (values are in tolerated range)
		//set false if the sim measure is 0 (values are out of tolerated range)
		if (record1.getWeight() != null && record2.getWeight() != null)
			return sim.calculate(Double.valueOf(record1.getHeight()), Double.valueOf(record2.getHeight())) != 0;
		//if values both null, consider they are equal
		else if (record1.getWeight() == null && record2.getWeight() == null) 
			return true;
		//if one of the value is null - not equal
		else
			return false;
		
		
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
