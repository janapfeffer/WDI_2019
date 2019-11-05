package identityresolution;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import identityresolution_models.Player;
import identityresolution_models.PlayerXMLReader;

public class IR_FIFA_API {
	
	private static final Logger logger = WinterLogManager.activateLogger("default");
	
	 public static void main( String[] args ) throws Exception{
		// loading data
			System.out.println("*\n*\tLoading datasets\n*");
			HashedDataSet<Player, Attribute> dataFIFA = new HashedDataSet<>();
			new PlayerXMLReader().loadFromXML(new File("data/input/FIFA19 target schema.xml"), "/Players/Player", 
					dataFIFA);
			HashedDataSet<Player, Attribute> dataAPI = new HashedDataSet<>();
			new PlayerXMLReader().loadFromXML(new File("data/input/PlayerAndTransfersAPI target schema.xml"), "/Players/Player", 
					dataAPI);

			// load the gold standard (test set)
			System.out.println("*\n*\tLoading gold standard\n*");
			MatchingGoldStandard gsTest = new MatchingGoldStandard();
			gsTest.loadFromCSVFile(new File(
					"data/goldstandard/gs-fifa-api_tabea.csv"));
			
			
			
	 }

}
