package me.xiaozhangup.hayerquest.script

import me.xiaozhangup.hayerquest.api.QuestAPI
import me.xiaozhangup.hayerquest.data.QuestLoader
import org.bukkit.Bukkit
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.kether.KetherLoader
import taboolib.module.kether.combinationParser

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.script.ActionQuest
 *
 * @author xiaomu
 * @since 2023/1/30 1:17 PM
 */
object ActionQuest {

    @Awake(LifeCycle.ACTIVE)
    fun init() {
        KetherLoader.registerParser(combinationParser {
            it.group(
                symbol(), // id/name
                symbol(), // player
                symbol().option() // questID
            ).apply(it) { action, player, questID ->
                now {
                    val p = Bukkit.getOfflinePlayer(player)
                    return@now when (action.lowercase()) {
                        "id" -> {
                            if (questID == null) {
                                QuestAPI.get(p.uniqueId).name
                            }
                            QuestAPI.did(p.uniqueId, (questID ?: return@now "").toInt())
                        }
                        "name" -> {
                            if (questID == null) {
                                QuestAPI.get(p.uniqueId).name
                            }
                            QuestAPI.did(p.uniqueId, QuestLoader.loadedQuests.firstOrNull { it.name == questID }?.id ?: return@now "")
                        }
                        else -> ""
                    }
                }
            }
        }, arrayOf("hayerquest", "quest"), "hayerquest", true)
    }
}