import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Game() {
    private val CARD_LIST: MutableList<Card> = getCardList()

    private var player1: Player = Player("Player1", 0, false, false);
    private var player2: Player = Player("Player2", 0, false, false);
    private lateinit var winningPlayer: Player
    private lateinit var losingPlayer: Player

    private lateinit var player1Card: Card
    private lateinit var player2Card: Card
    private lateinit var winningCard: Card
    private lateinit var losingCard: Card
    private var noStrongerCard: Boolean = false

    private var shuffledCards = CARD_LIST.shuffled().toMutableList()


    private fun attributeCard(){
        if(shuffledCards.isEmpty()) {
            val randomNumber: Int = (0..<player1.cardList.size).random()
            val randomNumber2: Int = (0..<player2.cardList.size).random()
            player1Card = player1.cardList[randomNumber]
            player2Card = player2.cardList[randomNumber2]
        }
        if(shuffledCards.size > 1) {
            player1Card = shuffledCards[0]
            player2Card = shuffledCards[1]
            return
        }
        if(shuffledCards.size == 1){
            val randomNumber2: Int = (0..<player2.cardList.size).random()
            player1Card = shuffledCards[0];
            player2Card = player2.cardList[randomNumber2]
            return
        }

    }

    private fun addCardToPlayer(){
        if(noStrongerCard) {
            noStrongerCard = false
            if(shuffledCards.isNotEmpty()) {
                shuffledCards.remove(player1Card);
                shuffledCards.remove(player2Card)
            } else {
                player1.cardList.remove(player1Card)
                player2.cardList.remove(player2Card)
            }

            return
        }


        winningPlayer.cardList.add(losingCard)
        winningPlayer.score = winningPlayer.score.toInt() + player1Card.value.toInt() + player2Card.value.toInt()

        if(losingPlayer.cardList.contains(losingCard)) losingPlayer.cardList.remove(losingCard)
        else {
            shuffledCards.removeAt(0)
            if(shuffledCards.isNotEmpty()) shuffledCards.removeAt(0)
        }


    }

    private fun checkStrongerCard() {
        if(player1Card.value == 0 ){
            winningPlayer = player1
            losingPlayer = player2
            winningCard = player1Card
            losingCard = player2Card
        }
        if(player2Card.value == 0 ){
            winningPlayer = player2
            losingPlayer = player1
            winningCard = player2Card
            losingCard = player1Card
        }

        if(player1Card.value.toInt() > player2Card.value.toInt()) {
            winningPlayer = player1
            losingPlayer = player2
            winningCard = player1Card
            losingCard = player2Card
        }
        if(player2Card.value.toInt() > player1Card.value.toInt()) {
            winningPlayer = player2
            losingPlayer = player1
            winningCard = player2Card
            losingCard = player1Card
        }

        if(player1Card.value.toInt() == player2Card.value.toInt()) {
            noStrongerCard = true
            println("Cards are equal : No winner !")
            return
        }
        println("Winning player is : ${winningPlayer.name} with card : ${winningCard.sign} ${winningCard.value}")
    }

    fun play() {
        while(!player1.hasWon or !player2.hasWon ) {
                attributeCard()
                checkStrongerCard()
                addCardToPlayer()
                checkPlayerWon()
            if((player1.cardList.size > 0) or (player2.cardList.size > 0) and (shuffledCards.size == 0)) break
        }

        val winner: Player = if(player1.hasWon) {
            player1
        } else {
            player2
        }
        println("Winner is : ${winner.name} with a score of ${winner.score}")
    return
    }

    private fun checkPlayerWon() {
        // println("p1 size: ${player1.cardList.size} p2 size: ${player2.cardList.size}")
        if (shuffledCards.isNotEmpty()) return
        val playerWon = if(player1.cardList.size == 0) {
            player2.hasWon = true
        } else if(player2.cardList.size == 0){
            player1.hasWon = true
        } else return
    }

    private fun getCardList(): MutableList<Card> {
        val cardList: MutableList<Card> = ArrayList();
        var sign: String = "";
        for (i in 0..4) {
            for (j in 0..13) {
                sign = when (j) {
                    0 -> "As"
                    11 -> "Valet"
                    12 -> "Dame"
                    13 -> "Roi"
                    else -> ""
                }
                cardList.add(Card(j, sign, j.toInt()))
            }
        }
        return cardList
    }
}