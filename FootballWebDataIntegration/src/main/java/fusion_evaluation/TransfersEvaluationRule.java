package fusion_evaluation;

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import models.Player;
import models.Transfer;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the transfers.
 */
public class TransfersEvaluationRule extends EvaluationRule<Player, Attribute> {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		Set<String> transfers1 = new HashSet<>();

		for (Transfer t : record1.getTransfers()) {

			transfers1.add(t.getYear()+" "+t.getClubInName()+" "+t.getClubOutName());
		}

		Set<String> transfers2 = new HashSet<>();
		for (Transfer t : record2.getTransfers()) {
			transfers2.add(t.getYear()+" "+t.getClubInName()+" "+t.getClubOutName());
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
