package identityresolution_comparators;

import java.text.Normalizer;

import org.apache.commons.lang3.StringEscapeUtils;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import models.Player;
/**
 * @author group3
 * 
 * Comparator for the name of the iSports API and FIFA19 data set using Jaccard.
 */
public class PlayerNameFIFAAPIComparatorJaccard implements Comparator<Player, Attribute>{

	private static final long serialVersionUID = 1L;
	/*similarity measure test*/
	public static void main( String[] args ) throws Exception{
		System.out.println(sim.calculate("", ""));
	}

	private static TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Player fifaplayer, Player apiplayer, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// FIFA name format: F. Lastname
		// API name format: &#193;lvaro Garc&#237;a Zudaire

		// save names and class name to logger
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(fifaplayer.getName());
			this.comparisonLog.setRecord2Value(apiplayer.getName());
		}

		/* preprocessing
		 * normalize names: 
		 * 		convert html4symbols in apiplayer name to string, 
		 * 		normalize special letters, ', lower case
		 */
		String fifa_name = Normalizer.normalize(fifaplayer.getName(), Normalizer.Form.NFD).
				replaceAll("[^\\p{ASCII}]", "").replace("'", "").replace(".", "").toLowerCase().replaceAll("\\s+", " ").trim();
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
		double similarity = sim.calculate(fifa_name, api_name);

		if(this.comparisonLog != null){
			this.comparisonLog.setRecord1PreprocessedValue(fifa_name);
			this.comparisonLog.setRecord2PreprocessedValue(api_name);

			this.comparisonLog.setSimilarity(Double.toString(similarity));
			//this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
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
