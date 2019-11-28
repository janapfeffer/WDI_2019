package fusion_fusers;

import java.util.List;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Union;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import models.Development;
import models.Player;
/**
 * @author group3
 * 
 * Fusion of the developments through union.
 */
public class DevelopmentsFuserUnion extends AttributeValueFuser<List<Development>, Player, Attribute>{


	public DevelopmentsFuserUnion() {
		super(new Union<Development, Player, Attribute>());
	}

	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.DEVELOPMENTS);
	}

	public List<Development> getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getDevelopments();
	}

	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, 
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) 
	{
		FusedValue<List<Development>, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setDevelopments(fused.getValue());
		fusedRecord.setAttributeProvenance(Player.DEVELOPMENTS, fused.getOriginalIds());
	}

}
