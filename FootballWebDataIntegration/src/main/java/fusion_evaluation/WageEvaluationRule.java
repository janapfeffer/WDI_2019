package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import identityresolution_models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the wage.
 */
public class WageEvaluationRule extends EvaluationRule<Player, Attribute>  {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// only FIFA19 has the wage
		return record1.getWage() == record2.getWage();
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// the wage information in FIFA19 is not the same as the most recent wages
		return record1.getWage() == record2.getWage();
	}

}
