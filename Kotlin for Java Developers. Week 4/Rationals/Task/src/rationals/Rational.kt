package rationals

import rationals.Rational.Companion.divBy
import rationals.Rational.Companion.toRational
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

@Suppress("DataClassPrivateConstructor")
data class Rational
    private constructor(val n: BigInteger, val d: BigInteger) : Comparable<Rational> {
    companion object {
        fun create(n: BigInteger, d: BigInteger): Rational = normalize(n, d)
        fun normalize(n: BigInteger, d: BigInteger) : Rational {
            require(d != ZERO) { "Знаменатель не может равняться нулю"}
            val g = n.gcd(d)
            val s = d.signum().toBigInteger()
            return Rational(n/g*s, d/g*s)
        }
        infix fun BigInteger.divBy(d: BigInteger) =
            Rational.create(this, d)
        infix fun Int.divBy(d: Int) =
            Rational.create(this.toBigInteger(), d.toBigInteger())
        infix fun Long.divBy(d: Long) =
            Rational.create(this.toBigInteger(), d.toBigInteger())

        fun String.toRational(): Rational {
            if (!contains("/")) {
                return Rational.create(toBigInteger(), ONE)
            }
            val (n, d) = split("/")
            return Rational.create(n.toBigInteger(), d.toBigInteger())
        }
    }
//    private fun normalize(n: BigInteger, d: BigInteger) : Rational {
//        require(d != ZERO) { "Знаменатель не может равняться нулю"}
//        val g = n.gcd(d)
//        val s = d.signum().toBigInteger()
//        return Rational(n/g*s, d/g*s)
//    }

    operator fun unaryMinus(): Rational = create(-n, d)
    operator fun times(other: Rational): Rational =
        create(n * other.n, d * other.d)
    operator fun div(other: Rational): Rational =
        create(n * other.d, d * other.n)
    operator fun plus(other: Rational): Rational =
        (n * other.d + other.n * d).divBy(other.d * d)
    operator fun minus(other: Rational): Rational =
        (n * other.d - other.n * d).divBy(other.d * d)

//    override fun toString(): String {
//
//    }



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational
        if (n != other.n) return false
        if (d != other.d) return false

        return true
    }

    override fun hashCode(): Int {
        var res = n.hashCode()
        res = 31 * res + d.hashCode()
        return res
    }

    override fun compareTo(other: Rational): Int {
        return (n * other.d - other.n * d).signum()
    }
}

interface ClosedRange<T: Comparable<T>> {
    val start: T
    val endInclusive: T
    operator fun contains(value: T): Boolean =
        value >= start && value <= endInclusive
}
//operator fun <T : Comparable<T>> T.rangeTo(that: T): ClosedRange<T> =
//    ComparableRange(this, that)

fun main() {
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