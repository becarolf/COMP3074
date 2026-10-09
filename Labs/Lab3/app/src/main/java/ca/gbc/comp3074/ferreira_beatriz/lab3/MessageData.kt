package ca.gbc.comp3074.ferreira_beatriz.lab3

import android.content.Context

fun loadMessages(context: Context): List<Message> {
    val senders = context.resources.getStringArray(
        R.array.message_senders
    )

    val texts = context.resources.getStringArray(
        R.array.message_texts
    )

    val messages = mutableListOf<Message>()
    for (i in senders.indices) {
        val message = Message(senders[i], texts[i])
        messages.add(message)
    }

    return messages
}