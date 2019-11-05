package identityresolution_comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
import identityresolution_models.Player;

public class HeightFIFAESDAbsoluteDifferences implements Comparator<Player, Attribute>{
	
	/*similarity measure test
	 * public static void main( String[] args ) throws Exception{
		System.out.println(sim.calculate(170.76, 165.98));
	}*/
	private static AbsoluteDifferenceSimilarity sim = new AbsoluteDifferenceSimilarity(8);
	
	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Player fifaplayer, Player esdplayer, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		// FIFA weight format: XXX.XX
		// ESD weight format: XXX.XX
		// the height difference range in 2 datasets - +/- 4 cm

		// save names and class name to logger
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(Float.toString(fifaplayer.getHeight()));
			this.comparisonLog.setRecord2Value(Float.toString(esdplayer.getHeight()));
		}

		//get height values
		double fifa_height = fifaplayer.getHeight();
		double esd_height = esdplayer.getHeight();
		
	    // calculate similarity
		double similarity = sim.calculate(fifa_height, esd_height);

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
