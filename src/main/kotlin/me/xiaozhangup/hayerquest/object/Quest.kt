package me.xiaozhangup.hayerquest.`object`

import me.xiaozhangup.hayerquest.api.QuestAPI
import me.xiaozhangup.hayerquest.util.Items
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.adaptPlayer
import taboolib.common5.Level
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.util.getStringColored
import taboolib.module.configuration.util.getStringListColored
import taboolib.platform.compat.depositBalance

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.`object`.Quest
 *
 * @author xiaomu
 * @since 2023/1/30 1:04 PM
 */
class Quest(
    val id: Int,
    val name: String,
    val require: List<ItemStack>,
    private val rewardExp: Int,
    private val rewardMoney: Double,
    val content: String,
    private val itemReward: List<ItemStack>,
    private val script: List<String> // 完成时运行的 Kether 动作
) {

    fun finish(player: Player) {
        if (rewardExp > 0) {
            Level.setTotalExperience(adaptPlayer(player), Level.getTotalExperience(adaptPlayer(player)) + rewardExp)
        }
        if (rewardMoney > 0) {
            player.depositBalance(rewardMoney)
        }
        QuestAPI.set(player.uniqueId, id + 1)
        itemReward.forEach { Items.give(player, it) }
        QuestAPI.eval(script, player)
    }

    companion object {

        /**
         * 从配置文件中构建
         */
        fun of(section: ConfigurationSection): Quest? {
            val id = section.name.toInt()
            val name = section.getStringColored("Name") ?: return null
            val require = section.getStringList("Items").also {
                if (it.isEmpty()) return null
            }.map {
                ItemStack(
                    Material.matchMaterial(it.split(":", limit = 2)[0]) ?: return null,
                    it.split(":", limit = 2)[1].toInt()
                )
            }
            val rewardExp = section.getInt("Reward.Exp", -1)
            val rewardMoney = section.getDouble("Reward.Money", -1.0)
            val content = section.getStringColored("Content") ?: return null
            val itemReward = section.getStringList("ItemReward").also {
                if (it.isEmpty()) return null
            }.map {
                ItemStack(
                    Material.matchMaterial(it.split(":", limit = 2)[0]) ?: return null,
                    it.split(":", limit = 2)[1].toInt()
                )
            }
            val script = section.getStringListColored("script")
            return Quest(id, name, require, rewardExp, rewardMoney, content, itemReward, script)
        }
    }
}