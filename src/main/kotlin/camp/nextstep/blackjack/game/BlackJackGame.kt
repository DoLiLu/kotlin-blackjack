package camp.nextstep.blackjack.game

import camp.nextstep.blackjack.card.Card
import camp.nextstep.blackjack.card.CardDeck
import camp.nextstep.blackjack.card.CardShuffler
import camp.nextstep.blackjack.player.Player
import camp.nextstep.blackjack.ui.cli.PlayerCardsWriter

class BlackJackGame private constructor(private var _cardDeck: CardDeck, private val _participants: List<Player>) {

    val turns: List<Turn>

    val cardDeck get() = CardDeck.of(_cardDeck.cards)

    val participants get() = _participants.toList()

    init {
        _cardDeck = CardShuffler.shuffle(_cardDeck)

        repeat(INIT_CARD_NUMBER) {
            serve(_participants) { _cardDeck.draw() }
        }

        turns = _participants.map { Turn(it) }
    }

    fun result(): GameResult {
        val isEnded = turns.all { it.isDone }
        check(isEnded) { "게임이 종료되지 않았습니다." }

        return GameResult(
            _participants.map { PlayerScore(it, Score.of(it.cards)) }
        )
    }

    fun doTurn(turn: Turn, readPlayersAction: (Player) -> Action) {
        while (!turn.isDone) {
            val action = readPlayersAction(turn.player)
            turn.applyToGame(action)
            PlayerCardsWriter.write(turn.player)
        }
    }

    private fun play(turn: Turn, action: Action) {
        if (action == Action.HIT) {
            serve(turn.player, _cardDeck.draw())
        }

        val playerScore = Score.of(turn.player.cards)
        if (playerScore.isBust() || action == Action.STAY) {
            turn.isDone = true
        }
    }

    private fun serve(players: List<Player>, cardDrawer: () -> Card) {
        for (player in players) {
            serve(player, cardDrawer())
        }
    }

    private fun serve(player: Player, card: Card) {
        player.receive(card)
    }

    inner class Turn(val player: Player) {

        var isDone = false
            internal set

        fun applyToGame(action: Action) {
            check(!isDone) { "턴이 종료되었습니다." }
            play(this, action)
        }
    }

    companion object {
        const val INIT_CARD_NUMBER = 2

        fun new(participants: List<Player> = listOf()): BlackJackGame {
            return BlackJackGame(CardDeck.new(), participants)
        }
    }
}