package co.neeve.nae2.client.gui;

import co.neeve.nae2.NAE2;
import co.neeve.nae2.client.gui.buttons.PatternMultiToolButton;
import co.neeve.nae2.client.gui.interfaces.IPatternMultiToolHostGui;
import co.neeve.nae2.common.enums.PatternMultiToolActionTypes;
import co.neeve.nae2.common.enums.PatternMultiToolActions;
import co.neeve.nae2.common.net.messages.PatternMultiToolPacket;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PatternMultiToolButtonHandler {
	@SubscribeEvent
	public void handlePatternMultiToolPress(GuiScreenEvent.ActionPerformedEvent.Post event) {
		if (event.getGui() instanceof IPatternMultiToolHostGui && event.getButton() instanceof PatternMultiToolButton tb) {
			var network = NAE2.net();

			if (tb.getAction() == PatternMultiToolActions.CLEAR) {
				var mc = event.getGui().mc;

				mc.displayGuiScreen(new GuiYesNo((boolean confirm, int i) -> {
					if (confirm) {
						network.sendToServer(new PatternMultiToolPacket(PatternMultiToolActionTypes.BUTTON_PRESS,
							PatternMultiToolActions.CLEAR.ordinal()));
					}
					mc.displayGuiScreen(event.getGui());
				}, I18n.format("nae2.pattern_multiplier.clear.all.patterns"), "", 0));
			} else {
				network.sendToServer(new PatternMultiToolPacket(PatternMultiToolActionTypes.BUTTON_PRESS,
					tb.getAction().ordinal()));
			}
		}
	}
}
