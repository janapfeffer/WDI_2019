package fusion_fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.string.LongestString;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import models.Player;

	public class CurrentPositionLongestString  extends AttributeValueFuser<String, Player, Attribute>{ 

		public CurrentPositionLongestString() {
			super(new LongestString<Player, Attribute>());
		}

		@Override
		public String getValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
			return record.getCurrentPosition();
		}

		@Override
		public void fuse(RecordGroup<Player, Attribute> group, Player fusedRecord,
				Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
			FusedValue<String, Player, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
			if (fused.getValue() != null){
				fusedRecord.setCurrentPosition(fused.getValue());
			}
			fusedRecord.setAttributeProvenance(Player.CURRENTPOSITION, fused.getOriginalIds());

		}

		@Override
		public boolean hasValue(Player record, Correspondence<Attribute, Matchable> correspondence) {
			return record.hasValue(Player.CURRENTPOSITION);
		}
	}
