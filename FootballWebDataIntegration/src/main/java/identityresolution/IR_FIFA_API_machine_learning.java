package identityresolution;

import java.io.File;
import java.io.PrintWriter;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
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
import identityresolution_comparators.DateFIFAAPIComparator;
import identityresolution_comparators.HeightFIFAAPIAbsoluteDifferences;
import identityresolution_comparators.HeightFIFAAPIDeviationSimilarity;
import identityresolution_comparators.HeightFIFAAPIPercentageSimilarity;
import identityresolution_comparators.PlayerNameFIFAAPIComparator;
import identityresolution_comparators.PlayerNameFIFAAPIComparatorJaccard;
import identityresolution_comparators.PlayerNameFIFAAPIComparatorLevenshtein;
import models.Player;
import models.PlayerXMLReader;
/**
 * @author group3
 * 
 * Identity resolution between the iSports API data set and the FIFA19 data set using machine learning.
 */
public class IR_FIFA_API_machine_learning {

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main( String[] args ) throws Exception
	{
		System.out.println("*\n*\tLoading datasets\n*");
		HashedDataSet<Player, Attribute> dataFIFA = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/FIFA19 target schema.xml"), "/Players/Player", 
				dataFIFA);
		HashedDataSet<Player, Attribute> dataAPI = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/PlayerAndTransfersAPI target schema.xml"), "/Players/Player", 
				dataAPI);

		// load the training set
		System.out.println("*\n*\tLoading training gold standard\n*");
		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/gs_fifa_api_train.csv"));

		// create a matching rule
		String options[] = new String[] {};
		String modelType = "SimpleLogistic"; // use a logistic regression
		WekaMatchingRule<Player, Attribute> matchingRule = new WekaMatchingRule<>(0.8, modelType, options);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRuleFIFA_API.csv", 1000, gsTraining);

		// add comparators
		matchingRule.addComparator(new PlayerNameFIFAAPIComparator());
		matchingRule.addComparator(new PlayerNameFIFAAPIComparatorJaccard());
		matchingRule.addComparator(new PlayerNameFIFAAPIComparatorLevenshtein());
		matchingRule.addComparator(new HeightFIFAAPIAbsoluteDifferences());
		matchingRule.addComparator(new HeightFIFAAPIDeviationSimilarity());
		matchingRule.addComparator(new HeightFIFAAPIPercentageSimilarity());
		matchingRule.addComparator(new DateFIFAAPIComparator());


		// train the matching rule's model
		System.out.println("*\n*\tLearning matching rule\n*");
		RuleLearner<Player, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(dataFIFA, dataAPI, null, matchingRule, gsTraining);
		//System.out.println(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));
		PrintWriter writer = new PrintWriter("data/output/MatchingRuleFIFA_API.txt", "UTF-8");
		writer.println(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));
		writer.close();

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingFirstnameGenerator());
		//SortedNeighbourhoodBlocker<Player, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new PlayerBlockingFirstnameGenerator(), 1);
		blocker.collectBlockSizeData("data/output/debugResultsBlockingFIFA_API_ML.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<Player, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");
		Processable<Correspondence<Player, Attribute>> correspondences = engine.runIdentityResolution(
				dataFIFA, dataAPI, null, matchingRule,
				blocker);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/FIFA19_2_API_correspondences.csv"), correspondences);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_fifa_api_test.csv"));

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
