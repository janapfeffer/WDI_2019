package identityresolution;

import java.io.File;

import org.slf4j.Logger;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import identityresolution_blocking.PlayerBlockingFirstnameGenerator;
import identityresolution_comparators.DateAPITransferComparator;
import identityresolution_comparators.DateFIFAESDComparator2Year;
import identityresolution_comparators.HeightFIFAESDAbsoluteDifferences;
import identityresolution_comparators.HeightFIFAESDDeviationSimilarity;
import identityresolution_comparators.HeightFIFAESDPercentageSimilarity;
import identityresolution_comparators.PlayerNameAPITransferComparator;
import identityresolution_comparators.PlayerNameAPITransfersComparatorLevenshtein;
import identityresolution_comparators.PlayerNameFIFAESDComparatorJaccard;
import identityresolution_comparators.PlayerNameFIFAESDComparatorJaccardOnNGram;
import identityresolution_comparators.PlayerNameFIFAESDComparatorLevenshtein;
import identityresolution_comparators.PlayerNameFIFAESDComparatorMaximumTokenContainment;
import models.Player;
import models.PlayerXMLReader;
/**
 * @author group3
 * 
 * Identity resolution between the iSports API data set and the transfers data set using machine learning.
 */
public class IR_API_TRANSFERS_machine_learning {

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main( String[] args ) throws Exception
	{
		System.out.println("*\n*\tLoading datasets\n*");
		HashedDataSet<Player, Attribute> dataAPI = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/PlayerAndTransfersAPI target schema.xml"), "/Players/Player", 
				dataAPI);
		HashedDataSet<Player, Attribute> dataTRANSFER = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/transfer_csv.xml"), "/Players/Player", 
				dataTRANSFER);

		// load the training set
		System.out.println("*\n*\tLoading training gold standard\n*");
		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/gs_transfers_api_train.csv"));

		// create a matching rule
		String options[] = new String[] {};
		String modelType = "RandomForest"; // use a logistic regression
		WekaMatchingRule<Player, Attribute> matchingRule = new WekaMatchingRule<>(0.5, modelType, options);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTraining);

		// add comparators
		matchingRule.addComparator(new PlayerNameAPITransfersComparatorLevenshtein());
		matchingRule.addComparator(new PlayerNameAPITransferComparator());
		matchingRule.addComparator(new DateAPITransferComparator());


		// train the matching rule's model
		System.out.println("*\n*\tLearning matching rule\n*");
		RuleLearner<Player, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(dataAPI, dataTRANSFER, null, matchingRule, gsTraining);
		System.out.println(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingFirstnameGenerator());
		//SortedNeighbourhoodBlocker<Player, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new PlayerBlockingFirstnameGenerator(), 1);
		blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<Player, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");
		Processable<Correspondence<Player, Attribute>> correspondences = engine.runIdentityResolution(
				dataAPI, dataTRANSFER, null, matchingRule,
				blocker);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/API_2_Transfer_correspondences.csv"), correspondences);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_transfers_api_test.csv"));

		// evaluate your result
		System.out.println("*\n*\tEvaluating result\n*");
		MatchingEvaluator<Player, Attribute> evaluator = new MatchingEvaluator<Player, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);

		// print the evaluation result
		System.out.println("API <-> Transfers");
		System.out.println(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTest.getF1()));
	}
}

