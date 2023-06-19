package blackjack.event

import blackjack.participant.Player

private typealias HitOrNot = (String) -> Boolean
private typealias ResultEvent = (Player) -> Unit

class PlayerEvent(
    val hitOrNot: HitOrNot,
    val resultEvent: ResultEvent,
)