package fusion_evaluation;

import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import models.Player;
/**
 * @author group3
 * 
 * Evaluation of the fusion of the date of birth (dob). 
 */
public class DateOfBirthEvaluationRule extends EvaluationRule<Player, Attribute> {
	public YearSimilarity year_sim = new YearSimilarity(2);
	@Override
	public boolean isEqual(Player record1, Player record2, Attribute schemaElement) {
		// both dobs are set and equal
		if (record1.getDateOfBirth() != null && record2.getDateOfBirth() != null){
			if (record1.getDateOfBirth() == record2.getDateOfBirth()){
				return true;
			}
			//check whether one dob contains 01.01 
			else if (
					(record1.getDateOfBirth().getMonthValue() == 1 && record1.getDateOfBirth().getDayOfMonth() == 1)
					|| (record2.getDateOfBirth().getMonthValue() == 1 && record2.getDateOfBirth().getDayOfMonth() == 1)
					) {
				return year_sim.calculate(record1.getDateOfBirth(), record2.getDateOfBirth()) != 0;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean isEqual(Player record1, Player record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}