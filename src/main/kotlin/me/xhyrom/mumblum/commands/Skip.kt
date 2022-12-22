package me.xhyrom.mumblum.commands

import me.xhyrom.mumblum.Bot
import me.xhyrom.mumblum.api.structs.Command
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class Skip : Command("skip", "Skip the current song") {
    override fun execute(event: SlashCommandInteractionEvent) {
        val voiceChannel = event.member?.voiceState?.channel?.asVoiceChannel()
            ?: return event.reply("You must be in a voice channel to use this command").setEphemeral(true).queue()
        if (event.guild?.selfMember?.voiceState?.channel != null && event.guild?.selfMember?.voiceState?.channel != voiceChannel) {
            return event.reply("You must be in the same voice channel as the bot to use this command").setEphemeral(true).queue()
        }

        val guildMusicManager = Bot.getLavaLinkManager().getGuildMusicManagerUnsafe(voiceChannel.guild)
            ?: return event.reply("The bot is not connected to a voice channel").setEphemeral(true).queue()

        guildMusicManager.getQueue().nextTrack()

        event.reply("Skipped").queue()
    }
}