package space.npstr.redrule.bot

import com.mewna.catnip.Catnip
import com.mewna.catnip.entity.message.Message
import com.mewna.catnip.shard.DiscordEvent
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by napster on 06.02.19.
 */
class Redrule {

    private val CATNIP_GUILD = 486311607400529931L
    private val OPHIS_ID = 199217346911404032L
    private val RED_ROLE_ID = 0L

    private val TARGET_GUILD_ID = System.getProperty("target.guild") ?: CATNIP_GUILD
    private val TARGET_USER_ID = System.getProperty("target.guild") ?: OPHIS_ID
    private val ROLE_ID: String = System.getProperty("role") ?: RED_ROLE_ID.toString()
    private val TRIGGER = System.getProperty("trigger") ?: "kotlin"

    private val catnip: Catnip
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    constructor(token: String) {
        this.catnip = Catnip.catnipAsync(token)
            .join()
            .connect()

        catnip.on(DiscordEvent.MESSAGE_CREATE) { messageReceived(it) }
    }

    private fun messageReceived(message: Message) {
        val guild = message.guild()
        if (guild == null || guild.idAsLong() != TARGET_GUILD_ID) {
            return
        }
        if (message.author().idAsLong() != TARGET_USER_ID) {
            return
        }
        if (!message.content().contains(TRIGGER, true)) {
            return
        }

        val redrole = guild.roles().getById(ROLE_ID) ?: return
        val ophis = guild.members().getById(TARGET_USER_ID) ?: return

        catnip.rest().guild().addGuildMemberRole(guild.id(), ophis.id(), redrole.id())
        scheduler.schedule(
            { catnip.rest().guild().removeGuildMemberRole(guild.id(), ophis.id(), redrole.id()) },
            1, TimeUnit.HOURS
        )
    }

}