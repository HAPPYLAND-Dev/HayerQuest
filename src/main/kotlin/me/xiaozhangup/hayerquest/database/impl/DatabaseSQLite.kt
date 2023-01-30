package me.xiaozhangup.hayerquest.database.impl

import me.xiaozhangup.hayerquest.data.QuestLoader
import me.xiaozhangup.hayerquest.database.Database
import me.xiaozhangup.hayerquest.`object`.Quest
import taboolib.common.io.newFile
import taboolib.module.database.ColumnOptionSQLite
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost
import java.util.UUID

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.database.impl.DatabaseSQLite
 *
 * @author xiaomu
 * @since 2023/1/30 1:40 PM
 */
class DatabaseSQLite : Database {

    private val host = newFile("done.db").getHost()

    private val table = Table("quest_data", host) {
        add("user") {
            type(ColumnTypeSQLite.TEXT) {
                options(ColumnOptionSQLite.PRIMARY_KEY)
            }
        }
        add("quest") {
            type(ColumnTypeSQLite.INTEGER)
        }
    }

    private val dataSource = host.createDataSource()

    override fun init() {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    override fun get(player: UUID): Quest {
        return QuestLoader.loadedQuests.first {
            it.id == (table.select(dataSource) {
                where {
                    "user" eq player
                }
            }.firstOrNull { getInt("quest") } ?: 1)
        }
    }

    override fun set(player: UUID, questID: Int) {
        table.delete(dataSource) {
            where { "user" eq player }
        }
        table.insert(dataSource, "user", "quest") {
            value(player, questID)
        }
    }
}