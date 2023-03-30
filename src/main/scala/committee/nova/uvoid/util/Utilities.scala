package committee.nova.uvoid.util

import committee.nova.uvoid.teleporter.UVoidTeleporter
import net.minecraft.block.BlockPortal
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.world.World

object Utilities {
  def teleportPlayerToSafePlace(player: EntityPlayerMP): Unit = {
    player.getEntityData.setBoolean("falling", true)
    val server = player.mcServer
    server.getConfigurationManager.transferPlayerToDimension(player, -1, new UVoidTeleporter(server.worldServerForDimension(-1)))
    val world = player.worldObj
    val spawnPos = generateSpawnPos(world, player.posX.toInt, player.posZ.toInt)
    val x1 = spawnPos._1
    val y1 = spawnPos._2
    val z1 = spawnPos._3
    player.setPositionAndRotation(spawnPos._1, spawnPos._2, spawnPos._3, player.rotationYaw, player.rotationPitch)
    player.motionX = 0
    player.motionY = 0
    player.motionZ = 0
    for (x <- x1 - 2 to x1 + 2) for (y <- y1 - 2 to y1 + 2) for (z <- z1 - 2 to z1 + 2)
      if (world.getBlock(x, y, z).isInstanceOf[BlockPortal]) world.setBlockToAir(x, y, z)
  }

  private def generateSpawnPos(world: World, x: Int, z: Int): (Int, Int, Int) = {
    val rand = world.rand
    val newX = x / 8 + rand.nextInt(100)
    val newY = 30 + rand.nextInt(50)
    val newZ = z / 8 + rand.nextInt(100)
    if (world.getBlock(newX, newY, newZ).isNormalCube) generateSpawnPos(world, newX, newZ)
    else (newX, newY, newZ)
  }
}
