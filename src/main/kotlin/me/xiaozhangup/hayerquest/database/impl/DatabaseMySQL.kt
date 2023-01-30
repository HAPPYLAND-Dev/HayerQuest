package me.xiaozhangup.hayerquest.database.impl

import me.xiaozhangup.hayerquest.HayerQuest
import me.xiaozhangup.hayerquest.data.QuestLoader
import me.xiaozhangup.hayerquest.database.Database
import me.xiaozhangup.hayerquest.`object`.Quest
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.HostSQL
import taboolib.module.database.Table
import java.util.*

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.database.impl.DatabaseMySQL
 *
 * @author xiaomu
 * @since 2023/1/30 1:41 PM
 */
class DatabaseMySQL : Database {

    private val host = HostSQL(HayerQuest.database)

    private val table = Table("quest_data", host) {
        add("user") {
            type(ColumnTypeSQL.VARCHAR, 255) {
                options(ColumnOptionSQL.PRIMARY_KEY)
            }
        }
        add("quest") {
            type(ColumnTypeSQL.INT)
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