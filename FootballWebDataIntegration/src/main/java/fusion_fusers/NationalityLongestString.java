package fusion_fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
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
 * Fusion of the Nationality using the longest string.
 */
public class NationalityLongestString extends AttributeValueFuser<String, Player, Attribute>{

	public NationalityLongestString() {
		super(new LongestString<Player, Attribute>());
	}

	@Override
	public String getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getNationality();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		// get the fused value
		FusedValue<String, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);

		// set the value for the fused record
		fusedRecord.setNationality(fused.getValue());

		// add provenance info
		fusedRecord.setAttributeProvenance(Player.NATIONALITY, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.NATIONALITY);
	}

}
