package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the current jersey number.
 * It is only the same if it is identical. 
 */
public class CurrentNumberEvaluationRule extends EvaluationRule<Player, Attribute>  {

	// the current number is equal if it is exactly the same
	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		return record1.getCurrentNumber() == record2.getCurrentNumber();
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
