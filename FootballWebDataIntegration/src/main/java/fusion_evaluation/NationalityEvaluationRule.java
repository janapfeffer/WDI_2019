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
 * Evaluation of the fusion of the nationality. 
 * The tokens of a nationality can be in a different order, e.g. "DR Congo" vs "Congo DR"
 */
public class NationalityEvaluationRule extends EvaluationRule<Player, Attribute>  {
	private static MaximumOfTokenContainment sim = new MaximumOfTokenContainment();
	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		if (record1.getNationality() != null && record2.getNationality() != null)
			return sim.calculate(record1.getNationality().toLowerCase(),  record2.getNationality().toLowerCase()) == 1;
		else 
			return false;
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// only FIFA19 has the nationality
		return record1.getNationality() == record2.getNationality();
	}

}
