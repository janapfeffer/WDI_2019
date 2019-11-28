package fusion_fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
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
 * Fusion of the speed using the source scores.
 */
public class SpeedFavourSourceFuser extends AttributeValueFuser<Integer, Player, Attribute>{

	public SpeedFavourSourceFuser() {
		super(new FavourSources<Integer, Player, Attribute>());
	}

	@Override
	public Integer getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getSpeed();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<Integer, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		if(fused.getValue() != null)
			fusedRecord.setSpeed(fused.getValue());
		fusedRecord.setAttributeProvenance(Player.SPEED, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.SPEED);
	}

}

