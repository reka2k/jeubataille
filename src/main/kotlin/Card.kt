class Card(var number: Number, var sign: String, var value: Number) {
    override fun toString(): String {
        return "Card(number=$number, sign=$sign, value=$value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (number != other.number) return false
        if (sign != other.sign) return false
        if (value != other.value) return false

        return true
    }


}
