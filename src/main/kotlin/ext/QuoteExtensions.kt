package ext

import commands.BaseCommand
import database.collections.Quote
import database.quoteRoles
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.TextChannel

fun EmbedBuilder.sendQuote(quote: Quote, channel: MessageChannel, baseCommand: BaseCommand) {
    channel.sendMessage(
        getQuote(quote)
    ).queue {
        baseCommand.messageId = it.id
    }
}

fun EmbedBuilder.getQuote(quote: Quote): MessageEmbed {
    return apply {
        setTitle(quote.authorName)
        setDescription(quote.messageContent + "\n\n[Jump to message](${quote.messageUrl})")
        setThumbnail(quote.authorAvatar)
        setImage(quote.attachment)
        setFooter("⭐${quote.stars.size} | ID: ${quote.quoteId} • ${quote.messageTimestamp}")
    }.build()
}

fun TextChannel.sendIncorrectQuote(baseCommand: BaseCommand) {
    sendMessage("That's not a valid quote bro").queue {
        baseCommand.messageId = it.id
    }
}

fun Member.hasQuotePerms(guildId: String): Boolean {
    val quoteRoles = guildId.quoteRoles
    return roles.any { quoteRoles.contains(it.id) }
}