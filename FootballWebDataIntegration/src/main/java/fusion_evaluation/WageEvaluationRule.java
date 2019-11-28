package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.PercentageSimilarity;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the wage.
 */
public class WageEvaluationRule extends EvaluationRule<Player, Attribute>  {
	private static PercentageSimilarity sim = new PercentageSimilarity(10);

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// since wages fluctuate quite a lot we accept a difference of 10%
		return sim.calculate((double) record1.getWage(), (double) record2.getWage()) == 1.0;
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// only FIFA19 has the wage
		return isEqual(record1, record2, (Attribute)null);
	}

}
