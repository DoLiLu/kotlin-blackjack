package blackjack.model

import blackjack.domain.Cards

data class UserCards(val name: String, var cards: Cards) {
    fun cardValue(): Int {
        return cards.value()
    }
}
