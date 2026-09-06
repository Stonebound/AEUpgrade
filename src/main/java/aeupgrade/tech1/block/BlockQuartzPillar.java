package aeupgrade.tech1.block;

import net.minecraft.item.ItemStack;
import aeupgrade.common.base.AppEngMultiBlock;
import aeupgrade.common.base.AppEngSubBlock;
import appeng.api.AEApi;

public class BlockQuartzPillar extends AppEngSubBlock
{

	public BlockQuartzPillar(AppEngMultiBlock mb) {
		super( mb, true );
		unlocalizedName = "QuartzPillar";
	}

	@Override
	public ItemStack getNewVersion() {
		// rv0 API spelled this "blockQuartzPiller"; rv3 fixed the typo to "blockQuartzPillar".
		return AEApi.instance().blocks().blockQuartzPillar.stack(1);
	}
}
