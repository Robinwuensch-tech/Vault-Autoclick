package dev.tresor.vaultautoclick;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Klickt automatisch (Rechtsklick) auf den Tresor, wenn
 *  - du auf einen aktiven Tresor schaust,
 *  - du einen (Ominous) Trial Key in der Hand haelst und
 *  - der Tresor gerade einen Heavy Core oder Dreizack anzeigt.
 */
public class VaultAutoClick implements ClientModInitializer {

    /** Wartezeit nach einem Klick in Ticks (20 Ticks = 1 Sekunde). */
    private static final int COOLDOWN_TICKS = 40;

    private int cooldown = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void onTick(Minecraft mc) {
        if (cooldown > 0) {
            cooldown--;
            return;
        }
        if (mc.player == null || mc.level == null || mc.gameMode == null) return;
        if (mc.screen != null) return; // nicht in Menues klicken

        // Nur mit Schluessel in der Hand
        ItemStack held = mc.player.getMainHandItem();
        if (!held.is(Items.TRIAL_KEY) && !held.is(Items.OMINOUS_TRIAL_KEY)) return;

        // Nur wenn ein Block anvisiert wird
        if (!(mc.hitResult instanceof BlockHitResult hit) || hit.getType() != HitResult.Type.BLOCK) return;

        BlockPos pos = hit.getBlockPos();
        BlockState state = mc.level.getBlockState(pos);
        if (!(state.getBlock() instanceof VaultBlock)) return;
        if (state.getValue(VaultBlock.STATE) != VaultState.ACTIVE) return;

        BlockEntity be = mc.level.getBlockEntity(pos);
        if (!(be instanceof VaultBlockEntity vault)) return;

        ItemStack shown = vault.getSharedData().getDisplayItem();
        if (shown.is(Items.HEAVY_CORE) || shown.is(Items.TRIDENT)) {
            mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hit);
            mc.player.swing(InteractionHand.MAIN_HAND);
            cooldown = COOLDOWN_TICKS;
        }
    }
}
