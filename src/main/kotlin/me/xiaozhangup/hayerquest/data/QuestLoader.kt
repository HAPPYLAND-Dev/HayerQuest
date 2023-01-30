package me.xiaozhangup.hayerquest.data

import me.xiaozhangup.hayerquest.`object`.Quest
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.data.QuestLoader
 *
 * @author xiaomu
 * @since 2023/1/30 1:05 PM
 */
object QuestLoader {

    @Config("quests.yml")
    lateinit var config: Configuration

    val loadedQuests = arrayListOf<Quest>()

    @Awake(LifeCycle.LOAD)
    fun init() {
        load()
        config.onReload {
            load() // 自动重载
            info("监听到配置文件保存, 已自动重载.")
        }
    }

    private fun load() {
        loadedQuests.clear()
        loadedQuests.addAll(config.getConfigurationSection("Once")?.getKeys(false)?.mapNotNull {
            Quest.of(config.getConfigurationSection("Once.$it")!!)
        } ?: emptySet())
        info("加载了 ${loadedQuests.size} 个任务.")
    }
}