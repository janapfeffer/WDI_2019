package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import identityresolution_models.Player;

public class PhotoEvaluationRule extends EvaluationRule<Player, Attribute>  {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// only API and FIFA19 have photos, they are never the same
		return record1.getPhoto() == record2.getPhoto();
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// either the player has a picture or not, there is no gold standard value we need to compare
		return true;
	}

}
