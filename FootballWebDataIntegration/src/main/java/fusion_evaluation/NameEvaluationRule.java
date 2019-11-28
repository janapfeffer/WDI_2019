package fusion_evaluation;

import java.text.Normalizer;

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
 * Evaluation of the fusion of the name. 
 */
public class NameEvaluationRule extends EvaluationRule<Player, Attribute> {
	private static SimilarityMeasure<String> sim = new TokenizingJaccardSimilarity();

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// the name is correct if all tokens are there
		String s1 = Normalizer.normalize(record1.getName(), Normalizer.Form.NFD);
		String s2 = Normalizer.normalize(record1.getName(), Normalizer.Form.NFD);
		return sim.calculate(s1, s2) == 1.0;	
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
