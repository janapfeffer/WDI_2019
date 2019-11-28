package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.MaximumOfTokenContainment;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the name. 
 */
public class NameEvaluationRule extends EvaluationRule<Player, Attribute> {
	private static MaximumOfTokenContainment sim = new MaximumOfTokenContainment();

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// the name is correct all tokens are there, but the order does not matter
		return sim.calculate(record1.getName(), record2.getName()) == 1.0;
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
