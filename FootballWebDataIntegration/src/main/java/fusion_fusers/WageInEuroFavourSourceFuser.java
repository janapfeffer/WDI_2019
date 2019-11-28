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
 * Fusion of the wage using the source scores.
 */
public class WageInEuroFavourSourceFuser extends AttributeValueFuser<Float, Player, Attribute>{

	public WageInEuroFavourSourceFuser() {
		super(new FavourSources<Float, Player, Attribute>());
	}

	@Override
	public Float getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getWage();
	}

	@Override
	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<Float, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		if (fused.getValue() != null)
			fusedRecord.setWage(fused.getValue());
		else
			fusedRecord.setWage(0.0f);
		fusedRecord.setAttributeProvenance(Player.WAGE, fused.getOriginalIds());

	}

	@Override
	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.WAGE);
	}

}
