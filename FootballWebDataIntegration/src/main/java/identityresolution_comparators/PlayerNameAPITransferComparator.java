package identityresolution_comparators;

import java.text.Normalizer;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.MaximumOfTokenContainment;
import models.Player;
/**
 * @author group3
 * 
 * Comparator for the players name of the iSports API and transfer data set 
 * using the maximum between levenshtein similarity and max token similarity..
 */
public class PlayerNameAPITransferComparator implements Comparator<Player, Attribute>{

	private static LevenshteinSimilarity levenshtein_sim = new LevenshteinSimilarity();
	private static MaximumOfTokenContainment maxtoken_sim = new MaximumOfTokenContainment();
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Player apiplayer, Player transferplayer, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// API name format: Firstname Lastname
		// Transfer name format: Firstname Lastname

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

		// calculate similarity
		double levenshtein_similarity = levenshtein_sim.calculate(api_name, transfer_name);
		double maxtoken_similarity = maxtoken_sim.calculate(api_name, transfer_name);

		double similarity = Math.max(levenshtein_similarity, maxtoken_similarity);
		double postSimilarity;
		if(similarity <= 0.3){
			postSimilarity = 0;
		} else {
			postSimilarity = similarity;
		}

		if(this.comparisonLog != null){
			this.comparisonLog.setRecord1PreprocessedValue(api_name);
			this.comparisonLog.setRecord2PreprocessedValue(transfer_name);

			this.comparisonLog.setSimilarity(Double.toString(similarity));
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
