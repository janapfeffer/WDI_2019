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
 * Evaluation of the fusion of the current Position. 
 * It can be spelled slightly differently.
 */
public class CurrentPositionEvaluationRule extends EvaluationRule<Player, Attribute>  {
	private static MaximumOfTokenContainment sim = new MaximumOfTokenContainment();
	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// the current Position can be written slightly differently
		return sim.calculate(record1.getCurrentPosition(), record2.getCurrentPosition()) == 1;
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// only  FIFA19 has the current Position
		return isEqual(record1, record2, (Attribute)null);
	}

}
