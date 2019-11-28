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
 * Fusion of the nationality using the source scores.
 */
public class NationalityFavourSource extends AttributeValueFuser<String, Player, Attribute>{

	public NationalityFavourSource() {
		super(new FavourSources<String, Player, Attribute>());
	}

	@Override
	public String getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getNationality();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<String, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setNationality(fused.getValue());
		fusedRecord.setAttributeProvenance(Player.NATIONALITY, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.NATIONALITY);
	}

}
