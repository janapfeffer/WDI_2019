package identityresolution_comparators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import models.Player;

/**
 * @author group3
 * 
 * Comparator for the date of birth of the FIFA19 and the European Soccer Database data set.
 * Uses a two year difference since FIFA19 does only contain the derived date of birth that can be inaccurate by 2 years.
 */
public class DateFIFAESDComparator2Year implements Comparator<Player, Attribute> {

	private static final long serialVersionUID = 1L;
	public static YearSimilarity sim = new YearSimilarity(4);

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(
			Player fifa_player,
			Player esd_player,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {

		LocalDateTime fifa_birth = fifa_player.getDateOfBirth();
		LocalDateTime esd_birth =  esd_player.getDateOfBirth();

		double similarity; 

		if(esd_birth == null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			esd_birth =  LocalDateTime.parse("2090-01-01 00:00", formatter);
		}
		if (fifa_birth.getYear() >= esd_birth.getYear()) {
			try {
				similarity = sim.calculate(fifa_birth, esd_birth);
			} catch (Exception e) {
				System.out.println("fifabirth: " + Integer.valueOf(fifa_birth.getYear()) + " esdbirth: " + Integer.valueOf(esd_birth.getYear()));
				similarity = 0;
			}

		} else {
			similarity = 0.0;
		}

		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());

			this.comparisonLog.setRecord1Value(fifa_birth.toString());
			this.comparisonLog.setRecord2Value(esd_birth.toString());

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
