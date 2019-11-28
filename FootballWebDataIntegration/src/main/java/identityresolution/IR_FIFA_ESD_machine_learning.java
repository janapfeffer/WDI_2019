package identityresolution;

import java.io.File;
import java.io.PrintWriter;

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
import identityresolution_comparators.DateFIFAESDComparator2Year;
import identityresolution_comparators.HeightFIFAESDAbsoluteDifferences;
import identityresolution_comparators.HeightFIFAESDDeviationSimilarity;
import identityresolution_comparators.HeightFIFAESDPercentageSimilarity;
import identityresolution_comparators.PlayerNameFIFAESDComparatorJaccard;
import identityresolution_comparators.PlayerNameFIFAESDComparatorJaccardOnNGram;
import identityresolution_comparators.PlayerNameFIFAESDComparatorLevenshtein;
import identityresolution_comparators.PlayerNameFIFAESDComparatorLevenshteinEditDistance;
import identityresolution_comparators.PlayerNameFIFAESDComparatorMaximumTokenContainment;
import models.Player;
import models.PlayerXMLReader;
/**
 * @author group3
 * 
 * Identity resolution between the FIFA19 data set and the European Soccer Database data set using machine learning.
 */
public class IR_FIFA_ESD_machine_learning {

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main( String[] args ) throws Exception
	{
		System.out.println("*\n*\tLoading datasets\n*");
		HashedDataSet<Player, Attribute> dataFIFA = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/FIFA19 target schema.xml"), "/Players/Player", 
				dataFIFA);
		HashedDataSet<Player, Attribute> dataESD = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/EuropeanSoccerDB target schema.xml"), "/Players/Player", 
				dataESD);

		// load the training set
		System.out.println("*\n*\tLoading training gold standard\n*");
		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/gs_fifa_eu_train.csv"));

		// create a matching rule
		String options[] = new String[] { };
		String modelType = "RandomForest"; // use a random forest
		WekaMatchingRule<Player, Attribute> matchingRule = new WekaMatchingRule<>(0.9, modelType, options);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRuleFIFA_ESD_ML.csv", 1000, gsTraining);

		// add comparators
		matchingRule.addComparator(new PlayerNameFIFAESDComparatorLevenshtein());
		matchingRule.addComparator(new PlayerNameFIFAESDComparatorJaccard());
		//matchingRule.addComparator(new PlayerNameFIFAESDComparatorJaccardOnNGram());
		matchingRule.addComparator(new PlayerNameFIFAESDComparatorLevenshteinEditDistance());
		matchingRule.addComparator(new PlayerNameFIFAESDComparatorMaximumTokenContainment());
		matchingRule.addComparator(new DateFIFAESDComparator2Year());
		matchingRule.addComparator(new HeightFIFAESDAbsoluteDifferences());
		//matchingRule.addComparator(new HeightFIFAESDDeviationSimilarity());
		//matchingRule.addComparator(new HeightFIFAESDPercentageSimilarity());


		// train the matching rule's model
		System.out.println("*\n*\tLearning matching rule\n*");
		RuleLearner<Player, Attribute> learner = new RuleLearner<>();
		learner.learnMatchingRule(dataFIFA, dataESD, null, matchingRule, gsTraining);
		//System.out.println(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));
		PrintWriter writer = new PrintWriter("data/output/MatchingRuleFIFA_ESD.txt", "UTF-8");
		writer.println(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));
		writer.close();

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingFirstnameGenerator());
		//		SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByDecadeGenerator(), 1);
		blocker.collectBlockSizeData("data/output/debugResultsBlockingFIFA_ESD_ML.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<Player, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");
		Processable<Correspondence<Player, Attribute>> correspondences = engine.runIdentityResolution(
				dataFIFA, dataESD, null, matchingRule,
				blocker);

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/ML_FIFA19_2_ESD_correspondences.csv"), correspondences);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_fifa_eu_test.csv"));

		// evaluate your result
		System.out.println("*\n*\tEvaluating result\n*");
		MatchingEvaluator<Player, Attribute> evaluator = new MatchingEvaluator<Player, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);

		// print the evaluation result
		System.out.println("FIFA <-> EU");
		System.out.println(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTest.getF1()));
	}
}
