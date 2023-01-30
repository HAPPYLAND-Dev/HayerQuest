package me.xiaozhangup.hayerquest.database

import me.xiaozhangup.hayerquest.HayerQuest
import me.xiaozhangup.hayerquest.database.impl.DatabaseLocal
import me.xiaozhangup.hayerquest.database.impl.DatabaseMySQL
import me.xiaozhangup.hayerquest.database.impl.DatabaseSQLite
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.info
import taboolib.common.platform.function.severe

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.database.PluginDatabase
 *
 * @author xiaomu
 * @since 2023/1/30 1:34 PM
 */
object PluginDatabase {

    private lateinit var database: Database

    @Awake(LifeCycle.ENABLE)
    fun init() {
        try {
            database = when (HayerQuest.type.uppercase()) {
                "LOCAL" -> DatabaseLocal()
                "MYSQL" -> DatabaseMySQL()
                "SQLITE" -> DatabaseSQLite()
                else -> DatabaseLocal()
            }
            database.init()
            info("已初始化数据库.")
        } catch (ex: Throwable) {
            severe("加载 数据库 时遇到错误(${ex.message}).")
            ex.printStackTrace()
        }
    }

    fun get(): Database = database
}