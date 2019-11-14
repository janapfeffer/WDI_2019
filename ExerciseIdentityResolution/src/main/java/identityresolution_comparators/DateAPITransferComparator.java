package identityresolution_comparators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import identityresolution_models.Player;


public class DateAPITransferComparator implements Comparator<Player, Attribute> {

	/*similarity measure test
	public static void main( String[] args ) throws Exception{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime date1 = LocalDateTime.parse("1998-05-16 00:00", formatter);
		LocalDateTime date2 = LocalDateTime.parse("1997-01-01 00:00", formatter);
		double sim_test;
		if (date2.isAfter(date1)) {
			sim_test = sim.calculate(date2, date1);
		} else {
			sim_test = 0.0;
		}
		System.out.println(sim_test);
	}*/

	private static final long serialVersionUID = 1L;
	public static YearSimilarity sim = new YearSimilarity(2);

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
			Player api_player,
			Player transfer_player,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {

		LocalDateTime transfer_birth = transfer_player.getDateOfBirth();
		LocalDateTime api_birth =  api_player.getDateOfBirth();

		double similarity; 
		
		if(api_birth == null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			// use dummy year if no date of birth is given
			api_birth =  LocalDateTime.parse("2090-01-01 00:00", formatter);
		}
		if(transfer_birth == null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			// use dummy year if no date of birth is given
			transfer_birth =  LocalDateTime.parse("2090-01-01 00:00", formatter);
		}
		if (api_birth.getYear() >= transfer_birth.getYear()) {
			try {
				similarity = sim.calculate(transfer_birth, api_birth);
			} catch (Exception e) {
				System.out.println("transferbirth: " + Integer.valueOf(transfer_birth.getYear()) + " apibirth: " + Integer.valueOf(api_birth.getYear()));
				similarity = 0;
			}

		} else {
			similarity = 0.0;
		}

		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());

			this.comparisonLog.setRecord1Value(api_birth.toString());
			this.comparisonLog.setRecord2Value(transfer_birth.toString());

			this.comparisonLog.setSimilarity(Double.toString(similarity));
		}

		return similarity;
	}

	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}
	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}
}
