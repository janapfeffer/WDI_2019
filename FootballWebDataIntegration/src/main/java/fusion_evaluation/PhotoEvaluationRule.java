package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the photo.
 * The photo url is never the same in different datasets, it is therefore also not included in the goldstandard. 
 */
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
