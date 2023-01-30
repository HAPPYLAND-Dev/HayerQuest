package me.xiaozhangup.hayerquest

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.info
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.ConfigReader
 *
 * @author xiaomu
 * @since 2023/1/30 1:35 PM
 */
object HayerQuest {

    @Config
    lateinit var config: Configuration

    @ConfigNode
    var type = "LOCAL"

    val database: ConfigurationSection
        get() = config.getConfigurationSection("database")!!

    @Awake(LifeCycle.ACTIVE)
    fun lifeCycle() {
        info("插件加载完毕, 作者 小张up.")
    }
}