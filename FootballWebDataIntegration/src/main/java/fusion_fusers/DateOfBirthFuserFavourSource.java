package fusion_fusers;

import java.time.LocalDateTime;

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
 * Fusion of the date of birth based on source scores.
 */
public class DateOfBirthFuserFavourSource extends AttributeValueFuser<LocalDateTime, Player, Attribute>{

	public DateOfBirthFuserFavourSource() {
		super(new FavourSources<LocalDateTime, Player, Attribute>());
	}

	@Override
	public LocalDateTime getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getDateOfBirth();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<LocalDateTime, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setDateOfBirth(fused.getValue());
		fusedRecord.setAttributeProvenance(Player.DATEOFBIRTH, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.DATEOFBIRTH);
	}

}
