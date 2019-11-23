package fusion_evaluation;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import identityresolution_models.Player;
import identityresolution_models.Development;

public class DevelopmentsEvaluationRule extends EvaluationRule<Player, Attribute>{

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {

		Set<String> developments1 = new HashSet<>();
		if (record1.getDevelopments() != null){
			for (Development d : record1.getDevelopments()) {

				developments1.add(d.getYear()+" "
						+d.getPotential()+" "
						+d.getRating()+" "
						+d.getAttackingWorkRate()+" "
						+d.getDefensiveWorkRate());
			}
		}


		Set<String> developments2 = new HashSet<>();
		if (record2.getDevelopments() != null) {
			for (Development d : record2.getDevelopments()) {

				developments2.add(d.getYear()+" "
						+d.getPotential()+" "
						+d.getRating()+" "
						+d.getAttackingWorkRate()+" "
						+d.getDefensiveWorkRate());
			}
		}


		return developments1.containsAll(developments2) && developments2.containsAll(developments1);
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		//the developments in the gold standard are dumy values because there is no other proper data soure containing them
		return true;
	}

}
