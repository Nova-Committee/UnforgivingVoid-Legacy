package committee.nova.uvoid

import committee.nova.uvoid.UnforgivingVoid.MODID
import committee.nova.uvoid.handler.{FMLEventHandler, ForgeEventHandler}
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.FMLPreInitializationEvent

@Mod(modid = MODID, useMetadata = true, modLanguage = "scala")
object UnforgivingVoid {
  final val MODID = "uvoid"

  @EventHandler def preInit(e: FMLPreInitializationEvent): Unit = {
    ForgeEventHandler.init()
    FMLEventHandler.init()
  }
}
