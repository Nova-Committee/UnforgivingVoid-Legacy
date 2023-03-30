package committee.nova.uvoid.handler

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent

object FMLEventHandler {
  def init(): Unit = FMLCommonHandler.instance().bus().register(new FMLEventHandler)
}

class FMLEventHandler {
  @SubscribeEvent
  def changeDim(e: PlayerChangedDimensionEvent): Unit = {
    if (e.toDim == -1) {
      val world = e.player.worldObj
      val y = 32 + e.player.getRNG.nextInt(90)
      for (k <- y to 16 if !world.getBlock(e.player.posX.toInt, k + 1, e.player.posZ.toInt).isNormalCube) {
        e.player.setPositionAndUpdate(e.player.posX + .5, k + .5, e.player.posZ + .5)
        return
      }
      e.player.setPositionAndUpdate(e.player.posX + .5, y + .5, e.player.posZ + .5)
    }
  }
}
