package fusion_evaluation;

import java.text.Normalizer;
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
public class TransferEvaluationRule extends EvaluationRule<Player, Attribute> {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		Set<Transfer> transfers1 = new HashSet<>();

		for (Transfer t : record1.getTransfers()) {
			t.setClubInName(Normalizer.normalize(t.getClubInName(), Normalizer.Form.NFD));
			t.setClubOutName(Normalizer.normalize(t.getClubOutName(), Normalizer.Form.NFD));

			transfers1.add(t);
		}

		Set<Transfer> transfers2 = new HashSet<>();
		for (Transfer t : record2.getTransfers()) {
			t.setClubInName(Normalizer.normalize(t.getClubInName(), Normalizer.Form.NFD));
			t.setClubOutName(Normalizer.normalize(t.getClubOutName(), Normalizer.Form.NFD));

			transfers2.add(t);
		}
		
		return transfers1.equals(transfers2);
	}

	@Override
	public boolean isEqual(Player record1, Player record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}


}
