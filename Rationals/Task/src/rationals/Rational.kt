package rationals

import java.math.BigInteger

data class Rational(val x: BigInteger, val y: BigInteger) : Comparable<Rational> {
    operator fun plus(r: Rational): Rational = (x.times(r.y).plus(r.x.times(y))).divBy(r.y.times(y))

    operator fun minus(r: Rational): Rational = (x.times(r.y).minus(r.x.times(y))).divBy(r.y.times(y))

    operator fun times(r: Rational): Rational = x.times(r.x).divBy(r.y.times(y))

    operator fun div(r: Rational): Rational = x.times(r.y).divBy(y.times(r.x))

    operator fun unaryMinus(): Rational = Rational(x.negate(), y)

    override fun compareTo(r: Rational): Int = x.times(r.y).compareTo(r.x.times(y))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as Rational

        val thisN = s1(this)
        val otherN = s1(other)

        return thisN.x.toDouble().div(thisN.y.toDouble()) == (otherN.x.toDouble().div(otherN.y.toDouble()))
    }

    override fun toString(): String {
        return when {
            y == 1.toBigInteger() || x.rem(y) == 0.toBigInteger() -> x.div(y).toString()
            else -> {
                val r = s1(this)

                if (r.y < 0.toBigInteger() || (r.x < 0.toBigInteger() && r.y < 0.toBigInteger())) {
                    formatString(Rational(r.x.negate(), r.y.negate()))
                } else {
                    formatString(Rational(r.x, r.y))
                }
            }
        }
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

}

infix fun Int.divBy(rational2: Int): Rational = Rational(toBigInteger(), rational2.toBigInteger())

infix fun Long.divBy(rational2: Long): Rational = Rational(toBigInteger(), rational2.toBigInteger())

infix fun BigInteger.divBy(rational2: BigInteger): Rational = Rational(this, rational2)

fun formatString(rational: Rational): String = rational.x.toString() + "/" + rational.y.toString()

fun String.toRational(): Rational {
    val res = split("/")

    when {
        res.size == 1 -> return Rational(res[0].toBigInteger(), 1.toBigInteger())
        else -> return Rational(res[0].toBigInteger(), res[1].toBigInteger())
    }
}

fun aux(n1: BigInteger, n2: BigInteger): BigInteger =
        if (n2 != 0.toBigInteger()) aux(n2, n1 % n2) else n1

fun s1(r1: Rational): Rational {
    val result = aux(r1.x, r1.y).abs()
    return Rational(r1.x.div(result), r1.y.div(result))
}

fun main(args: Array<String>) {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}