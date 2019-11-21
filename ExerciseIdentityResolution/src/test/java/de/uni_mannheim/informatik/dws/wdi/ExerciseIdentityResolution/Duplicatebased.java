/*package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution;

import java.io.File;
import java.io.FileReader;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;

import au.com.bytecode.opencsv.CSVReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.aggregators.VotingAggregator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoSchemaBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.VotingMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.CSVRecordReader;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Record;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class Duplicatebased {
	
	private static final Logger logger = (Logger) WinterLogManager.activateLogger("default");
	
	public static void main(String[] args) throws Exception {
		CSVReader reader= new CSVReader(new FileReader("/input/hechen.csv"));
		reader.readNext();
		String [] line;
		while((line = reader.readNext()) != null){
			String cluster_id=line[0];
			String 
		}
		
	DataSet<Record, Attribute> data1 = new HashedDataSet<>();
	new CSVRecordReader(0).loadFromCSV(new File("/input/scifi1.csv"), data1);
	DataSet<Record, Attribute> data2 = new HashedDataSet<>();
	new CSVRecordReader(0).loadFromCSV(new File(""),data2);
	
	Processable<Correspondence<Record, Attribute>> duplicates
	  = Correspondence.loadFromCsv(
	    new File("scifi1_2_scifi2_instance_correspondences.csv"),
	    data1,
	    data2);
	for(Correspondence<Record, Attribute> cor : duplicates.get()) {
		logger.info(String.format("'%s' <-> '%s'", cor.getFirstRecord(), cor.getSecondRecord()));
	}
	VotingMatchingRule<Attribute, Record> rule = new VotingMatchingRule<Attribute, Record>(1.0){
		private static final long serialVersionUID = 1L;

		@Override
		public double compare(Attribute a1, Attribute a2,
				Correspondence<Record, Matchable> c) {
			String value1 = c.getFirstRecord().getValue(a1);
			String value2 = c.getSecondRecord().getValue(a2);
			
			if(value1!=null && value2!=null && !value1.equals("0.0") && value1.equals(value2)) {
				return 1.0;
			} else {
				return 0.0;
			}
		}
	};
	MatchingEngine<Record, Attribute> engine = new MatchingEngine<>();
	Processable<Correspondence<Attribute, Record>> correspondences = engine.runDuplicateBasedSchemaMatching(data1.getSchema(), data2.getSchema(), duplicates, rule, null, new VotingAggregator<>(true, 1.0), new NoSchemaBlocker<>());
	
	for(Correspondence<Attribute, Record> cor : correspondences.get()) {
		logger.info(String.format("'%s' <-> '%s' (%.4f)", cor.getFirstRecord().getName(), cor.getSecondRecord().getName(), cor.getSimilarityScore()));
	}
	}
	}
	*/