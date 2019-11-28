package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the date of birth (dob). 
 */
public class DateOfBirthEvaluationRule extends EvaluationRule<Player, Attribute> {

	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		return record1.getDateOfBirth().getYear() == record2.getDateOfBirth().getYear()
				&& record1.getDateOfBirth().getMonth() == record2.getDateOfBirth().getMonth()
				&& record1.getDateOfBirth().getDayOfMonth() == record2.getDateOfBirth().getDayOfMonth();
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}