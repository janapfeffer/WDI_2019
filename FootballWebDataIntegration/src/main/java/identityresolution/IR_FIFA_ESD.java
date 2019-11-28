package identityresolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
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
import identityresolution_blocking.PlayerBlockingKeyByHeightGenerator;
import identityresolution_blocking.PlayerBlockingKeyByYearGenerator;
import identityresolution_comparators.DateFIFAESDComparator2Year;
import identityresolution_comparators.HeightFIFAESDAbsoluteDifferences;
import identityresolution_comparators.PlayerNameFIFAESDComparatorJaccard;
import identityresolution_comparators.PlayerNameFIFAESDComparatorJaccardOnNGram;
import identityresolution_comparators.PlayerNameFIFAESDComparatorLevenshtein;
import identityresolution_comparators.PlayerNameFIFAESDComparatorMaximumTokenContainment;
import models.Player;
import models.PlayerXMLReader;
/**
 * @author group3
 * 
 * Identity resolution between the FIFA19 data set and the European Soccer Database data set.
 */
public class IR_FIFA_ESD {

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main( String[] args ) throws Exception{
		// loading data
		System.out.println("*\n*\tLoading datasets\n*");
		HashedDataSet<Player, Attribute> dataFIFA = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/FIFA19 target schema.xml"), "/Players/Player", 
				dataFIFA);
		HashedDataSet<Player, Attribute> dataESD = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/EuropeanSoccerDB target schema.xml"), "/Players/Player", 
				dataESD);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
				"data/goldstandard/gs_fifa_eu.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<Player, Attribute> matchingRule = new LinearCombinationMatchingRule<>(
				0.79);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRuleFIFA_ESD.csv", 1000, gsTest);

		// add comparators
		//matchingRule.addComparator(new PlayerNameFIFAESDComparatorLevenshtein(), 0.25);
		matchingRule.addComparator(new PlayerNameFIFAESDComparatorJaccard(), 0.5);
		//matchingRule.addComparator(new PlayerNameFIFAESDComparatorMaximumTokenContainment(), 0.25);
		matchingRule.addComparator(new HeightFIFAESDAbsoluteDifferences(), 0.25);
		matchingRule.addComparator(new DateFIFAESDComparator2Year(), 0.25);


		// create a blocker
		//NoBlocker<Player, Attribute> blocker = new NoBlocker<>(); // noBlocker should not be used, it raises a java.lang.OutOfMemoryError: Java heap space
		//StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingKeyByYearGenerator());
		//StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingKeyByHeightGenerator());
		//SortedNeighbourhoodBlocker<Player, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new PlayerBlockingKeyByYearGenerator(), 50);
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingFirstnameGenerator());
		blocker.setMeasureBlockSizes(true);
		blocker.collectBlockSizeData("data/output/debugResultsBlockingFIFA_ESD.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<Player, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");
		Processable<Correspondence<Player, Attribute>> correspondences = engine.runIdentityResolution(
				dataFIFA, dataESD, null, matchingRule,
				blocker);

		System.out.println("*\n*\tSaving correspondences to output\n*");
		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/FIFA19_2_ESD_correspondences.csv"), correspondences);		

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<Player, Attribute> evaluator = new MatchingEvaluator<Player, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);

		// print the evaluation result
		System.out.println("FIFA 19 <-> ESD");
		System.out.println(String.format(
				"Precision: %.4f",perfTest.getPrecision()));
		System.out.println(String.format(
				"Recall: %.4f",	perfTest.getRecall()));
		System.out.println(String.format(
				"F1: %.4f",perfTest.getF1()));
	}

}
