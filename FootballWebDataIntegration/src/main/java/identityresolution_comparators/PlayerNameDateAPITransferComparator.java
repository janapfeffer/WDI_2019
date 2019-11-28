package identityresolution_comparators;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import models.Player;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
/**
 * @author group3
 * 
 * Comparator for the name of the iSports API and transfer data set using levenshtein similarity
 * and the date of birth using year similarity with an accepted deviation of 2 years.
 */
public class PlayerNameDateAPITransferComparator implements Comparator<Player, Attribute>{

	private static final long serialVersionUID = 1L;
	public static YearSimilarity year_sim = new YearSimilarity(2);
	private static LevenshteinSimilarity levenshtein_sim = new LevenshteinSimilarity();
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Player apiplayer, Player transferplayer, Correspondence<Attribute, Matchable> schemaCorrespondence) {

		// save names and class name to logger
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(apiplayer.getName());
			this.comparisonLog.setRecord2Value(transferplayer.getName());
		}

		// preprocessing:
		// normalize names: normalize special letters, ', lower case
		String api_name = Normalizer.normalize(apiplayer.getName(), Normalizer.Form.NFD).
				replaceAll("[^\\p{ASCII}]", "").replace(",", "").replace("'", "").replace(".", "").toLowerCase().replaceAll("\\s+", " ").trim();
		String transfer_name = Normalizer.normalize(transferplayer.getName(), Normalizer.Form.NFD).
				replaceAll("[^\\p{ASCII}]", "").replace(",", "").replace("'", "").replace(".", "").toLowerCase().replaceAll("\\s+", " ").trim();

		// calculate Levenshtein similarity
		double levenshtein_similarity = levenshtein_sim.calculate(api_name, transfer_name);


		// calculate absolute birth year similarity
		LocalDateTime transfer_birth = transferplayer.getDateOfBirth();
		LocalDateTime api_birth =  apiplayer.getDateOfBirth();

		double year_similarity=0.0; 

		if(api_birth == null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			// use dummy year if no date of birth is given
			api_birth =  LocalDateTime.parse("2090-01-01 00:00", formatter);
		}
		if (api_birth.getYear() >= transfer_birth.getYear()) {
			try {
				year_similarity = year_sim.calculate(transfer_birth, api_birth);
			} catch (Exception e) {
				//System.out.println("transferbirth: " + Integer.valueOf(transfer_birth.getYear()) + " apibirth: " + Integer.valueOf(api_birth.getYear()));
				//year_similarity = 0;
			}

		} else {
			year_similarity = 0.0;
		}


		double postSimilarity;
		if ((levenshtein_similarity == 1) & (year_similarity > 0)) {
			postSimilarity = 1;
		} else {
			postSimilarity = 0.85 * levenshtein_similarity + 0.15 * year_similarity;
		}

		if(this.comparisonLog != null){
			this.comparisonLog.setRecord1PreprocessedValue(api_name);
			this.comparisonLog.setRecord2PreprocessedValue(transfer_name);

			this.comparisonLog.setSimilarity(Double.toString(levenshtein_similarity));
			this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
		}

		return postSimilarity;
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
