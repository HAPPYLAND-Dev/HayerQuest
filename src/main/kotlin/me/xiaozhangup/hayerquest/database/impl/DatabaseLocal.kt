package me.xiaozhangup.hayerquest.database.impl

import me.xiaozhangup.hayerquest.data.QuestLoader
import me.xiaozhangup.hayerquest.database.Database
import me.xiaozhangup.hayerquest.`object`.Quest
import taboolib.common.io.newFile
import taboolib.module.configuration.Configuration
import java.util.UUID

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.database.impl.DatabaseLocal
 *
 * @author xiaomu
 * @since 2023/1/30 1:39 PM
 */
class DatabaseLocal : Database {

    lateinit var config: Configuration

    override fun init() {
        Configuration.loadFromFile(newFile("done.yml"))
    }

    override fun get(player: UUID): Quest {
        return QuestLoader.loadedQuests.first { it.id == config.getInt(player.toString()) }
    }

    override fun set(player: UUID, questID: Int) {
        config[player.toString()] = questID
    }
}