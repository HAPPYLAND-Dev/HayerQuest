package me.xiaozhangup.hayerquest.api

import me.xiaozhangup.hayerquest.database.PluginDatabase
import me.xiaozhangup.hayerquest.`object`.Quest
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.library.kether.LocalizedException
import taboolib.module.kether.KetherShell
import taboolib.module.kether.printKetherErrorMessage
import taboolib.platform.compat.replacePlaceholder
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.api.QuestAPI
 *
 * @author xiaomu
 * @since 2023/1/30 1:09 PM
 */
object QuestAPI {

    @JvmStatic
    fun eval(scripts: List<String>, sender: Player): CompletableFuture<Any?> {
        return try {
            KetherShell.eval(source = scripts.map { it.replacePlaceholder(sender) }, sender = adaptPlayer(sender), namespace = listOf("hayerquest"))
        } catch (ex: LocalizedException) {
            ex.printKetherErrorMessage()
            CompletableFuture.completedFuture(null)
        }
    }

    fun get(player: UUID): Quest {
        return PluginDatabase.get().get(player)
    }

    fun set(player: UUID, questID: Int) {
        PluginDatabase.get().set(player, questID)
    }

    fun did(player: UUID, questID: Int): Boolean {
        return PluginDatabase.get().did(player, questID)
    }
}