package fusion;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import fusion_evaluation.CurrentClubEvaluationRule;
import fusion_evaluation.CurrentNumberEvaluationRule;
import fusion_evaluation.CurrentPositionEvaluationRule;
import fusion_evaluation.DateOfBirthEvaluationRule;
import fusion_evaluation.DevelopmentsEvaluationRule;
import fusion_evaluation.FootEvaluationRule;
import fusion_evaluation.HeightEvaluationRule;
import fusion_evaluation.NameEvaluationRule;
import fusion_evaluation.NationalityEvaluationRule;
import fusion_evaluation.PhotoEvaluationRule;
import fusion_evaluation.SpeedEvaluationRule;
import fusion_evaluation.TransferEvaluationRule;
import fusion_evaluation.WageEvaluationRule;
import fusion_evaluation.WeightEvaluationRule;
import fusion_fusers.CurrentClubFavourSourceFuser;
import fusion_fusers.CurrentClubMostRecentFuser;
import fusion_fusers.CurrentNumberFavourSource;
import fusion_fusers.CurrentNumberMostRecentFuser;
import fusion_fusers.CurrentPositionLongestString;
import fusion_fusers.DateOfBirthFuserFavourSource;
import fusion_fusers.DateOfBirthVotingFuser;
import fusion_fusers.DevelopmentsFuserUnion;
import fusion_fusers.FootFuserMostRecent;
import fusion_fusers.HeightFuserAverage;
import fusion_fusers.HeightMedianFuser;
import fusion_fusers.NameFavourSourceFuser;
import fusion_fusers.NameFuserByVoting;
import fusion_fusers.NameLongestString;
import fusion_fusers.NationalityFavourSource;
import fusion_fusers.NationalityLongestString;
import fusion_fusers.PhotoFuserFavourSource;
import fusion_fusers.SpeedFavourSourceFuser;
import fusion_fusers.TransfersFuserUnion;
import fusion_fusers.WageInEuroFavourSourceFuser;
import fusion_fusers.WeightFuserMostRecent;
import fusion_fusers.WeightMedianFuser;
import models.Player;
import models.PlayerXMLFormatter;
import models.PlayerXMLFormatter_Fusion;
import models.PlayerXMLReader_Fusion;

public class DataFusion_Main {

	private static final Logger logger = WinterLogManager.activateLogger("traceFile");

	public static void main( String[] args ) throws Exception{
		// Load the Data into FusibleDataSet
		System.out.println("*\n*\tLoading datasets\n*");
		FusibleDataSet<Player, Attribute> dataTransfer = new FusibleHashedDataSet<>();
		new PlayerXMLReader_Fusion().loadFromXML(new File("data/input/transfer_csv.xml"), "/Players/Player", dataTransfer);
		dataTransfer.printDataSetDensityReport();

		FusibleDataSet<Player, Attribute> dataAPI = new FusibleHashedDataSet<>();
		new PlayerXMLReader_Fusion().loadFromXML(new File("data/input/PlayerAndTransfersAPI target schema.xml"), "/Players/Player", dataAPI);
		dataAPI.printDataSetDensityReport();

		FusibleDataSet<Player, Attribute> dataESD = new FusibleHashedDataSet<>();
		new PlayerXMLReader_Fusion().loadFromXML(new File("data/input/EuropeanSoccerDB target schema.xml"), "/Players/Player", dataESD);
		dataESD.printDataSetDensityReport();

		FusibleDataSet<Player, Attribute> dataFIFA = new FusibleHashedDataSet<>();
		new PlayerXMLReader_Fusion().loadFromXML(new File("data/input/FIFA19 target schema.xml"), "/Players/Player", dataFIFA);
		dataFIFA.printDataSetDensityReport();

		// Maintain Provenance
		// Scores (are later on sometimes adapted since the data quality varies for different attributes)
		dataAPI.setScore(4.0);
		dataESD.setScore(3.0);
		dataFIFA.setScore(2.0);
		dataTransfer.setScore(1.0);

		// Date (last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
				.appendPattern("yyyy-MM-dd")
				.parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
				.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				.toFormatter(Locale.ENGLISH);

		dataAPI.setDate(LocalDateTime.parse("2019-09-29", formatter)); // date of last download since it has live data
		dataESD.setDate(LocalDateTime.parse("2016-10-24", formatter));
		dataFIFA.setDate(LocalDateTime.parse("2018-12-21", formatter));
		dataTransfer.setDate(LocalDateTime.parse("2019-01-01", formatter));

		// load correspondences
		System.out.println("*\n*\tLoading correspondences\n*");
		CorrespondenceSet<Player, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/output/API_2_Transfer_correspondences.csv"),dataAPI, dataTransfer);
		correspondences.loadCorrespondences(new File("data/output/FIFA19_2_API_correspondences.csv"),dataFIFA, dataAPI);
		correspondences.loadCorrespondences(new File("data/output/FIFA19_2_ESD_correspondences.csv"),dataFIFA, dataESD);

		// write group size distribution
		correspondences.printGroupSizeDistribution();

		// load the gold standard
		System.out.println("*\n*\tEvaluating results\n*");
		DataSet<Player, Attribute> gs = new FusibleHashedDataSet<>();
		new PlayerXMLReader_Fusion().loadFromXML(new File("data/goldstandard/gs_datafusion.xml"), "/Players/Player", gs);

		for(Player m : gs.get()) {
			System.out.println(String.format("gs: %s", m.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<Player, Attribute> strategy = new DataFusionStrategy<>(new PlayerXMLReader_Fusion());
		// write debug results to file
		strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", -1, gs);

		// add attribute fusers
		// fuse date of birth, we prefer data sources that contain the accurate date of birth (API and ESD)
		strategy.addAttributeFuser(Player.DATEOFBIRTH, new DateOfBirthFuserFavourSource(), new DateOfBirthEvaluationRule());
		// fuse name
		strategy.addAttributeFuser(Player.NAME, new NameFavourSourceFuser(), new NameEvaluationRule());
		//strategy.addAttributeFuser(Player.NAME, new NameLongestString(), new NameEvaluationRule());
		// fuse photos, we prefer the API photos because they have a higher resolution
		strategy.addAttributeFuser(Player.PHOTO, new PhotoFuserFavourSource(), new PhotoEvaluationRule());
		// fuse nationality, it is only contained in FIFA19
		strategy.addAttributeFuser(Player.NATIONALITY, new NationalityLongestString(), new NationalityEvaluationRule());
		// only FIFAS contains the current club & wages
		strategy.addAttributeFuser(Player.CURRENTCLUB, new CurrentClubMostRecentFuser(), new CurrentClubEvaluationRule());
		strategy.addAttributeFuser(Player.WAGE, new WageInEuroFavourSourceFuser(), new WageEvaluationRule());
		// for weight we prefer the median values
		strategy.addAttributeFuser(Player.WEIGHT, new WeightMedianFuser(), new WeightEvaluationRule());
		//fuse transfers which are only in API and transfers
		strategy.addAttributeFuser(Player.TRANSFERS, new TransfersFuserUnion(), new TransferEvaluationRule());
		//fuse foot
		strategy.addAttributeFuser(Player.FOOT, new FootFuserMostRecent(), new FootEvaluationRule());
		//fuse current number (in FIFA19 and API)
		strategy.addAttributeFuser(Player.CURRENTNUMBER, new CurrentNumberMostRecentFuser(), new CurrentNumberEvaluationRule());
		//fuse height (in ESD, FIFA and API)
		strategy.addAttributeFuser(Player.HEIGHT, new HeightMedianFuser(), new HeightEvaluationRule());
		//fuse developments
		strategy.addAttributeFuser(Player.DEVELOPMENTS, new DevelopmentsFuserUnion(), new DevelopmentsEvaluationRule());
		//fuse speed
		strategy.addAttributeFuser(Player.SPEED, new SpeedFavourSourceFuser(), new SpeedEvaluationRule());
		// fuse current position
		strategy.addAttributeFuser(Player.CURRENTPOSITION, new CurrentPositionLongestString(), new CurrentPositionEvaluationRule());

		// create the fusion engine
		DataFusionEngine<Player, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);

		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);

		// run the fusion
		System.out.println("*\n*\tRunning data fusion\n*");
		FusibleDataSet<Player, Attribute> fusedDataSet = engine.run(correspondences, null);

		// write the result
		new PlayerXMLFormatter_Fusion().writeXML(new File("data/output/fused.xml"), fusedDataSet);

		// evaluate
		DataFusionEvaluator<Player, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Player, Attribute>());

		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		System.out.println(String.format("Accuracy: %.2f", accuracy));
		
		// print density of final fused data set
		FusibleDataSet<Player, Attribute> dataFused = new FusibleHashedDataSet<>();
		new PlayerXMLReader_Fusion().loadFromXML(new File("data/output/fused.xml"), "/Players/Player", dataFused);
		dataFused.printDataSetDensityReport();
	}

}
