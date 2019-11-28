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
import models.Player;
import models.Transfer;
/**
 * @author group3
 * 
 * Fusion of the transfers using union.
 */
public class TransfersFuserUnion extends AttributeValueFuser<List<Transfer>, Player, Attribute> {

	public TransfersFuserUnion() {
		super(new Union<Transfer, Player, Attribute>());
	}

	public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Player.TRANSFERS);
	}

	public List<Transfer> getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getTransfers();
	}

	public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<List<Transfer>, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setTransfers(fused.getValue());
		fusedRecord.setAttributeProvenance(Player.TRANSFERS, fused.getOriginalIds());
	}


}
