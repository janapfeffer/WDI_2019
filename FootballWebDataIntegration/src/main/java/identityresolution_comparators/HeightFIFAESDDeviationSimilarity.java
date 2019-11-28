package identityresolution_comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.DeviationSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.PercentageSimilarity;
import models.Player;
/**
 * @author group3
 * 
 * Comparator for the height of the European Soccer Database and FIFA19 data set using deviation similarity.
 */
public class HeightFIFAESDDeviationSimilarity implements Comparator<Player, Attribute>{

	//similarity measure test
	public static void main( String[] args ) throws Exception{
		System.out.println(sim.calculate(170.76, 165.98));
	}
	private static DeviationSimilarity sim = new DeviationSimilarity();

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Player fifaplayer, Player esdplayer, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// FIFA weight format: XXX.XX
		// ESD weight format: XXX.XX
		// the height difference range in 2 datasets - +/- 4 cm

		//get height values
		double fifa_height;
		double esd_height;
		try {
			fifa_height = fifaplayer.getHeight();
		} catch (Exception e) {
			fifa_height = 0;
		}
		try {
			esd_height = esdplayer.getHeight();
		} catch (Exception e) {
			esd_height = 0;
		}


		// save names and class name to logger
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(String.valueOf(fifa_height));
			this.comparisonLog.setRecord2Value(String.valueOf(esd_height));
		}


		// calculate similarity
		double similarity;
		try {
			similarity = sim.calculate(fifa_height, esd_height);
		} catch (Exception e) { // set similarity to zero in case it is missing in one of the datasets
			similarity = 0;
		}

		if(this.comparisonLog != null){
			this.comparisonLog.setRecord1PreprocessedValue(Double.toString(fifa_height));
			this.comparisonLog.setRecord2PreprocessedValue(Double.toString(esd_height));

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
