package me.xiaozhangup.hayerquest.hook

import me.xiaozhangup.hayerquest.api.QuestAPI
import org.bukkit.OfflinePlayer
import taboolib.platform.compat.PlaceholderExpansion

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.hook.QuestExpansion
 *
 * @author xiaomu
 * @since 2023/1/30 4:01 PM
 */
object QuestExpansion : PlaceholderExpansion {

    override val identifier: String
        get() = "hayerquest"

    override fun onPlaceholderRequest(player: OfflinePlayer?, args: String): String {
        return when (args.lowercase()) {
            "current" -> QuestAPI.get(player?.uniqueId ?: return "").id.toString()
            "name" -> QuestAPI.get(player?.uniqueId ?: return "").name
            else -> ""
        }
    }
}