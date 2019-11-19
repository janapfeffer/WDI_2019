package fusion;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.apache.logging.log4j.Logger;

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
import fusion_models.PlayerXMLReader_Fusion;
import identityresolution_models.Player;
import identityresolution_models.PlayerXMLReader;

public class DataFusion_Main {

	//private static final Logger logger = WinterLogManager.activateLogger("traceFile");

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
		// Scores (e.g. from rating)
		dataAPI.setScore(1.0);
		dataESD.setScore(2.0);
		dataFIFA.setScore(3.0);
		dataTransfer.setScore(4.0);

	}

}
