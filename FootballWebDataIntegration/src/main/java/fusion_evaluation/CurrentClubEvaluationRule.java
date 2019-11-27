package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import identityresolution_models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the current club. 
 * It can be spelled slightly differently.
 */
public class CurrentClubEvaluationRule extends EvaluationRule<Player, Attribute>  {
	SimilarityMeasure<String> sim = new TokenizingJaccardSimilarity();

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// only  FIFA19 has the current club
		return record1.getCurrentClub() == record2.getCurrentClub();
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// the current club can be written slightly differently
		return sim.calculate(record1.getCurrentClub(), record2.getCurrentClub()) > 0.8;
	}

}
