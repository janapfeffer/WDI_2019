package identityresolution_comparators;

import java.text.Normalizer;
/**
 * @author group3
 * 
 * Comparator for the name of the iSports API and transfers data set using levenshtein similarity.
 */
import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.MaximumOfTokenContainment;
import models.Player;

public class PlayerNameAPITransfersComparatorLevenshtein implements Comparator<Player, Attribute>{

	private static LevenshteinSimilarity sim = new LevenshteinSimilarity();
	private ComparatorLogger comparisonLog;

	public static void main( String[] args ) {
		String[] myList;
		myList = Normalizer.normalize("nospace, pls", Normalizer.Form.NFD).
				replaceAll("[^\\p{ASCII}]", "").replace("'", "").replace(".", "").toLowerCase().replaceAll("\\s+", " ").trim().split("\\s");

		MaximumOfTokenContainment sim = new MaximumOfTokenContainment();
		LevenshteinSimilarity sim2 = new LevenshteinSimilarity();

		System.out.println(sim.calculate("Naldo Ronaldo Aparecido Rodrigues", "Naldo Apareceido Rodriges"));
		for(int i = 0; i < myList.length; i++){
			System.out.println(myList[i]);
		}



	}

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
		double similarity = sim.calculate(api_name, transfer_name);

		if(this.comparisonLog != null){
			this.comparisonLog.setRecord1PreprocessedValue(api_name);
			this.comparisonLog.setRecord2PreprocessedValue(transfer_name);

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
