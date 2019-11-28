package identityresolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import identityresolution_blocking.PlayerBlockingFirstnameGenerator;
import identityresolution_blocking.PlayerBlockingKeyByYearGenerator;
import identityresolution_comparators.DateAPITransferComparator;
import identityresolution_comparators.PlayerNameAPITransferComparator;
import identityresolution_comparators.PlayerNameAPITransfersComparatorLevenshtein;
import identityresolution_comparators.PlayerNameDateAPITransferComparator;
import models.Player;
import models.PlayerXMLReader;
/**
 * @author group3
 * 
 * Identity resolution between the iSports API data set and the transfers data set.
 */
public class IR_API_TRANSFERS {

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main( String[] args ) throws Exception{
		// loading data
		System.out.println("*\n*\tLoading datasets\n*");
		HashedDataSet<Player, Attribute> dataTransfer = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/transfer_csv.xml"), "/Players/Player", 
				dataTransfer);
		HashedDataSet<Player, Attribute> dataAPI = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/PlayerAndTransfersAPI target schema.xml"), "/Players/Player", 
				dataAPI);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_api_transfers.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<Player, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.85);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTest);

		// add comparators
		//matchingRule.addComparator(new PlayerNameAPITransferComparator(), 0.7);
		//matchingRule.addComparator(new PlayerNameAPITransfersComparatorLevenshtein(), 0.7);
		//matchingRule.addComparator(new DateAPITransferComparator(), 0.3);
		matchingRule.addComparator(new PlayerNameDateAPITransferComparator(), 1);

		// create a blocker
		//NoBlocker<Player, Attribute> blocker = new NoBlocker<>(); // noBlocker should not be used, it raises a java.lang.OutOfMemoryError: Java heap space
		//StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingFirstnameGenerator());
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingFirstnameGenerator());
		blocker.setMeasureBlockSizes(true);
		blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<Player, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");
		Processable<Correspondence<Player, Attribute>> correspondences = engine.runIdentityResolution(
				dataAPI, dataTransfer, null, matchingRule,
				blocker);

		System.out.println("*\n*\tSaving correspondences to output\n*");
		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/API_2_Transfer_correspondences.csv"), correspondences);		

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<Player, Attribute> evaluator = new MatchingEvaluator<Player, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);

		// print the evaluation result
		System.out.println("API <-> Transfer");
		System.out.println(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTest.getF1()));


	}

}
