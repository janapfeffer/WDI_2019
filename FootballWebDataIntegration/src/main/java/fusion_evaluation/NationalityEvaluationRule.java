package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the nationality. 
 */
public class NationalityEvaluationRule extends EvaluationRule<Player, Attribute>  {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// only FIFA19 has the nationality
		return record1.getNationality() == record2.getNationality();
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return record1.getNationality() ==  record2.getNationality();
	}

}
