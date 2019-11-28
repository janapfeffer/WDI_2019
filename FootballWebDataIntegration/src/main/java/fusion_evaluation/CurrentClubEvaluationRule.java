package fusion_evaluation;

import java.text.Normalizer;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the current club. 
 * It has to be spelled identically.
 * Sheffield United -> Sheffield United U18
 */
public class CurrentClubEvaluationRule extends EvaluationRule<Player, Attribute>  {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// the current club can be written slightly differently with special characters
		if (record1.getCurrentClub() != null && record2.getCurrentClub() != null) {
			String s1 = Normalizer.normalize(record1.getCurrentClub(), Normalizer.Form.NFD);
			String s2 = Normalizer.normalize(record1.getCurrentClub(), Normalizer.Form.NFD);
			return s1 == s2;
		}
		else
			return false;
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// only FIFA19 has the current club
		return isEqual(record1, record2, (Attribute)null);
	}

}
