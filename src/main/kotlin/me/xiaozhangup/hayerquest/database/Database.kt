package me.xiaozhangup.hayerquest.database

import me.xiaozhangup.hayerquest.`object`.Quest
import java.util.UUID

/**
 * HayerQuest
 * me.xiaozhangup.hayerquest.database.Database
 *
 * @author xiaomu
 * @since 2023/1/30 1:33 PM
 */
interface Database {

    fun init()

    fun get(player: UUID): Quest

    fun set(player: UUID, questID: Int)

    fun did(player: UUID, questID: Int): Boolean {
        return get(player).id > questID
    }
}