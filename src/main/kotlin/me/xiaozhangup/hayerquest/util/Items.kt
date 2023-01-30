package me.xiaozhangup.hayerquest.util

import me.xiaozhangup.hayerquest.`object`.Quest
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.util.ItemChecker
 *
 * @author xiaomu
 * @since 2023/1/30 2:30 PM
 */
object Items {

    fun check(p: Player, quest: Quest): Boolean {
        for (item in quest.require) {
            if (getMaterialAmount(p.inventory, item.type) < item.amount) {
                return false
            }
        }
        return true
    }

    fun remove(p: Player, quest: Quest) {
        for (item in quest.require) {
            removeAmount(p.inventory, item.type, item.amount)
        }
    }

    fun give(p: Player, item: ItemStack) {
        val out = p.inventory.addItem(item)
        out.values.forEach { p.world.dropItem(p.location, it) }
    }

    private fun getMaterialAmount(inventory: Inventory, material: Material): Int {
        var total = 0
        val var3 = inventory.contents
        val var4 = var3.size
        for (var5 in 0 until var4) {
            val item = var3[var5]
            if (material === item.type && !item.hasItemMeta()) {
                total += item.amount
            }
        }
        return total
    }

   private fun removeAmount(inventory: Inventory, material: Material, amount: Int) {
        var removed = 0
        var index = 0
        val var5 = inventory.contents
        val var6 = var5.size
        for (var7 in 0 until var6) {
            val itemStack = var5[var7]
            if (itemStack == null) {
                ++index
            } else {
                if (removed >= amount) {
                    break
                }
                if (material === itemStack.type) {
                    if (removed + itemStack.amount <= amount) {
                        removed += itemStack.amount
                        inventory.setItem(index, null)
                    } else {
                        itemStack.amount -= (amount - removed)
                        removed += amount
                    }
                }
                ++index
            }
        }
    }
}