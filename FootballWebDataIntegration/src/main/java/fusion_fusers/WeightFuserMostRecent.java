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
 * Fusion of the weight using the most recent source.
 */
public class WeightFuserMostRecent extends AttributeValueFuser<Float, Player, Attribute>{

	public WeightFuserMostRecent() {
		super(new MostRecent<Float, Player, Attribute>());
	}

	@Override
	public Float getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getWeight();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<Float, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setWeight(fused.getValue());
		fusedRecord.setAttributeProvenance(Player.WEIGHT, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.WEIGHT);
	}

}
