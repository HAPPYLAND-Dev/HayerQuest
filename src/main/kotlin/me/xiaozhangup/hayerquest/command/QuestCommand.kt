package me.xiaozhangup.hayerquest.command

import me.xiaozhangup.hayerquest.data.QuestLoader
import me.xiaozhangup.hayerquest.database.PluginDatabase
import me.xiaozhangup.hayerquest.ui.QuestBook
import me.xiaozhangup.hayerquest.util.Items
import org.bukkit.entity.Player
import taboolib.common.platform.command.*
import taboolib.platform.util.sendError
import taboolib.platform.util.sendInfo

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.command.QuestCommand
 *
 * @author xiaomu
 * @since 2023/1/30 1:05 PM
 */
@CommandHeader(name = "quest", permission = "quest.use")
object QuestCommand {

    @CommandBody(permission = "quest.use")
    val main = mainCommand {
        execute<Player> { sender, _, _ ->
            QuestBook.open(sender)
        }
    }

    @CommandBody(permission = "quest.pull")
    val pull = subCommand {
        int("questID") {
            execute<Player> { sender, context, _ ->
                val quest = QuestLoader.loadedQuests.first { it.id == context["questID"].toInt() }
                if (quest.id != PluginDatabase.get().get(sender.uniqueId).id) return@execute
                if (!Items.check(sender, quest)) {
                    sender.sendError("pull-fail")
                }
                Items.remove(sender, quest)
                quest.finish(sender)
                sender.sendInfo("pull")
            }
        }
    }
}