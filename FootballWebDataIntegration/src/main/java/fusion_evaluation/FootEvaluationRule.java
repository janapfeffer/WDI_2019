package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the preferred foot. 
 */
public class FootEvaluationRule extends EvaluationRule<Player, Attribute>{


	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// check whether the foot attribute is given or not
		if(record1.getFoot()== null && record2.getFoot()==null)
			return true;
		else if(record1.getFoot()== null ^ record2.getFoot()==null)
			return false;
		else 
			return record1.getFoot().toLowerCase().equals(record2.getFoot().toLowerCase());
	}

	@Override
	public boolean isEqual(Player record1, Player record2, 
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
