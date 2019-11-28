package fusion_fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.MostRecent;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import models.Player;
/**
 * @author group3
 * 
 * Fusion of the height using the most recent source.
 */
public class HeightFuserMostRecent extends AttributeValueFuser<Double, Player, Attribute>{

	public HeightFuserMostRecent() {
		super(new MostRecent<Double, Player, Attribute>());
	}

	@Override
	public Double getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return Double.valueOf((record.getHeight()));
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {

		FusedValue<Double, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		if (fused.getValue() != null) {
			fusedRecord.setHeight(Float.valueOf(fused.getValue().toString()));
		}

		fusedRecord.setAttributeProvenance(Player.HEIGHT, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.HEIGHT);
	}

}
