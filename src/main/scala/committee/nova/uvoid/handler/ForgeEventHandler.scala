package committee.nova.uvoid.handler

import committee.nova.uvoid.util.Utilities
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.DamageSource
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.event.entity.player.PlayerEvent

object ForgeEventHandler {
  def init(): Unit = MinecraftForge.EVENT_BUS.register(new ForgeEventHandler)
}

class ForgeEventHandler {
  @SubscribeEvent
  def onLivingHurt(event: LivingHurtEvent): Unit = {
    val source = event.source
    event.entityLiving match {
      case player: EntityPlayerMP => {
        val data = player.getEntityData
        event.source match {
          case i if (i.equals(DamageSource.outOfWorld)) =>
            if (player.posY <= -64.0 && player.dimension != -1) Utilities.teleportPlayerToSafePlace(player)
          case i if (i.equals(DamageSource.fall)) => {
            if (player.dimension != -1 || !data.getBoolean("falling")) return
            event.ammount = event.ammount max (player.getHealth - 1.0F)
            data.setBoolean("falling", false)
          }
          case _ =>
        }

      }
      case _ =>
    }
  }

  @SubscribeEvent
  def onClone(event: PlayerEvent.Clone): Unit =
    event.entityPlayer.getEntityData.setBoolean("falling", event.original.getEntityData.getBoolean("falling"))
}
