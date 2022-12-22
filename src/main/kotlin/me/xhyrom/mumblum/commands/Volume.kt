package me.xhyrom.mumblum.commands

import me.xhyrom.mumblum.Bot
import me.xhyrom.mumblum.api.structs.Command
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class Volume : Command(
    "volume",
    "Change the volume of the current song",
    listOf(
        OptionData(
            OptionType.INTEGER,
            "volume",
            "The volume to set",
            true
        ).setMaxValue(100).setMinValue(1)
    )
) {
    override fun execute(event: SlashCommandInteractionEvent) {
        val voiceChannel = event.member?.voiceState?.channel?.asVoiceChannel()
            ?: return event.reply("You must be in a voice channel to use this command").setEphemeral(true).queue()
        if (event.guild?.selfMember?.voiceState?.channel != null && event.guild?.selfMember?.voiceState?.channel != voiceChannel) {
            return event.reply("You must be in the same voice channel as the bot to use this command").setEphemeral(true).queue()
        }

        val guildMusicManager = Bot.getLavaLinkManager().getGuildMusicManagerUnsafe(voiceChannel.guild)
            ?: return event.reply("The bot is not connected to a voice channel").setEphemeral(true).queue()

        guildMusicManager.getPlayer().volume = event.getOption("volume")!!.asInt

        event.reply("Set the volume to ${event.getOption("volume")!!.asInt}").queue()
    }
}