package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.MaximumOfTokenContainment;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the current club. 
 * It can be spelled slightly differently.
 */
public class CurrentClubEvaluationRule extends EvaluationRule<Player, Attribute>  {
	private static MaximumOfTokenContainment sim = new MaximumOfTokenContainment();
	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// the current club can be written slightly differently
		if (record1.hasValue(Player.CURRENTCLUB) && record2.hasValue(Player.CURRENTCLUB))
			return sim.calculate(record1.getCurrentClub(), record2.getCurrentClub()) == 1;
		else
			return false;
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// only  FIFA19 has the current club
		return sim.calculate(record1.getCurrentClub(), record2.getCurrentClub()) == 1;
	}

}
