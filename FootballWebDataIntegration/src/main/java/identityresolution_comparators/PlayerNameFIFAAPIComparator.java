package identityresolution_comparators;

import java.text.Normalizer;

import org.apache.commons.lang3.StringEscapeUtils;

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
 * Comparator for the name of the iSports API and FIFA19 data set using the maximum of
 * levenshtein similarity and maximum of token containment.
 */
public class PlayerNameFIFAAPIComparator implements Comparator<Player, Attribute>{

	private static LevenshteinSimilarity levenshtein_sim = new LevenshteinSimilarity();
	private static MaximumOfTokenContainment maxtoken_sim = new MaximumOfTokenContainment();
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Player fifaplayer, Player apiplayer, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// API name format: Firstname Lastname
		// Transfer name format: Firstname Lastname

		// save names and class name to logger
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(fifaplayer.getName());
			this.comparisonLog.setRecord2Value(apiplayer.getName());
		}

		// preprocessing:
		// normalize names: normalize special letters, ', lower case
		String fifa_name = Normalizer.normalize(fifaplayer.getName(), Normalizer.Form.NFD).
				replaceAll("[^\\p{ASCII}]", "").replace(",", "").replace("'", "").replace(".", "").toLowerCase().replaceAll("\\s+", " ").trim();

		String api_name = Normalizer.normalize(StringEscapeUtils.unescapeHtml4(apiplayer.getName()), Normalizer.Form.NFD).
				replaceAll("[^\\p{ASCII}]", "").replace("'", "").replace(".", "").toLowerCase().replaceAll("\\s+", " ").trim();

		// tokenize names
		String[] fifa_name_list = fifa_name.split("\\s");
		String[] api_name_list = api_name.split("\\s");

		// convert first name in ESD to the same format as in FIFA (abbreviate)
		if(fifa_name_list[0].length() == 1){
			if(api_name_list.length > 1){
				api_name_list[0] = api_name_list[0].substring(0, 1);
			}
		}

		// turn back to single string
		fifa_name = String.join(" ", fifa_name_list);
		api_name = String.join(" ", api_name_list);


		// calculate similarity
		double levenshtein_similarity = levenshtein_sim.calculate(fifa_name, api_name);
		double maxtoken_similarity = maxtoken_sim.calculate(fifa_name, api_name);

		double similarity = Math.max(levenshtein_similarity, maxtoken_similarity);
		double postSimilarity;
		if(similarity <= 0.3){
			postSimilarity = 0;
		} else {
			postSimilarity = similarity;
		}

		if(this.comparisonLog != null){
			this.comparisonLog.setRecord1PreprocessedValue(fifa_name);
			this.comparisonLog.setRecord2PreprocessedValue(api_name);

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
