package fusion_evaluation;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import identityresolution_models.Player;
import identityresolution_models.Transfer;

public class TransfersEvaluationRule extends EvaluationRule<Player, Attribute> {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		Set<String> transfers1 = new HashSet<>();

		for (Transfer t : record1.getTransfers()) {
			// note: evaluating using the transfers's name only suffices for simple
			// lists
			// in your project, you should have actor ids which you use here
			// (and in the identity resolution)
			transfers1.add(t.getClubInName()+" "+t.getClubOutName());
		}

		Set<String> transfers2 = new HashSet<>();
		for (Transfer t : record2.getTransfers()) {
			transfers2.add(t.getClubInName()+" "+t.getClubOutName());
		}

		return transfers1.containsAll(transfers2) && transfers2.containsAll(transfers1);
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Player record1, Player record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}


}
