package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import identityresolution_models.Player;

public class FootEvaluationRule extends EvaluationRule<Player, Attribute>{

	
	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		if(record1.getFoot()== null && record2.getFoot()==null)
			return true;
		else if(record1.getFoot()== null ^ record2.getFoot()==null)
			return false;
		else 
			return record1.getFoot().equals(record2.getFoot());
	}

	@Override
	public boolean isEqual(Player record1, Player record2, 
				Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
