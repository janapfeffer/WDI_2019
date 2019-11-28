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
public class TransfersEvaluationRule extends EvaluationRule<Player, Attribute> {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		Set<String> transfers1 = new HashSet<>();

		for (Transfer t : record1.getTransfers()) {
			String s1 = Normalizer.normalize(t.getClubInName(), Normalizer.Form.NFD);
			String s2 = Normalizer.normalize(t.getClubOutName(), Normalizer.Form.NFD);

			transfers1.add(t.getYear()+" "+ s1 +" "+ s2);
		}

		Set<String> transfers2 = new HashSet<>();
		for (Transfer t : record2.getTransfers()) {
			String s1 = Normalizer.normalize(t.getClubInName(), Normalizer.Form.NFD);
			String s2 = Normalizer.normalize(t.getClubOutName(), Normalizer.Form.NFD);
			transfers2.add(t.getYear()+" "+ s1 +" "+ s2 );
		}
		
		return transfers1.containsAll(transfers2) && transfers2.containsAll(transfers1);
	}

	@Override
	public boolean isEqual(Player record1, Player record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}


}
