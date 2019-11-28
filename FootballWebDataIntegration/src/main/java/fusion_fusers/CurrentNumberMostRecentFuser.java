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
 * Fusion of the current jersey number favouring the most recent source.
 */
public class CurrentNumberMostRecentFuser extends
AttributeValueFuser<Integer, Player, Attribute> {

	public CurrentNumberMostRecentFuser() {
		super(new MostRecent<Integer, Player, Attribute>());
	}

	@Override
	public Integer getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getCurrentNumber();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<Integer, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		if (fused.getValue() != null){
			fusedRecord.setCurrentNumber(fused.getValue());
		}
		fusedRecord.setAttributeProvenance(Player.CURRENTNUMBER, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.CURRENTNUMBER);
	}

}