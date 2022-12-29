import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RationalTest {
    @Test
    fun gcd() {
        assertEquals(Rational.gcd(78, 66), 6)
        assertEquals(Rational.gcd(270, 192), 6)
        assertEquals(Rational.gcd(37, 37), 37)
        assertEquals(Rational.gcd(53, 1), 1)
        assertEquals(Rational.gcd(1220, 516), 4)
        assertEquals(Rational.gcd(173, 100), 1)
    }

    @Test
    fun add() {
        assertTrue(Rational(1, 2).add(Rational(1, 2)).equals(Rational(1, 1)))
        assertTrue(Rational(1, 3).add(Rational(1, 3)).equals(Rational(2, 3)))
        assertTrue(Rational(4, 7).add(Rational(11, 3)).equals(Rational(89, 21)))
        assertTrue(Rational(4, 7).add(Rational(-11, 3)).equals(Rational(-65, 21)))
        assertTrue(Rational(3, 4).add(Rational(-6, 8)).equals(Rational(0, 1)))
    }

    @Test
    fun subtract() {
        assertTrue(Rational(1, 2).subtract(Rational(1, 2)).equals(Rational(0, 1)))
        assertTrue(Rational(1, 3).subtract(Rational(2, 3)).equals(Rational(-1, 3)))
        assertTrue(Rational(4, 7).subtract(Rational(11, 3)).equals(Rational(-65, 21)))
        assertTrue(Rational(4, 7).subtract(Rational(-11, 3)).equals(Rational(89, 21)))
        assertTrue(Rational(3, 4).subtract(Rational(6, 8)).equals(Rational(0, 1)))
        assertTrue(Rational(3, 4).subtract(Rational(-6, 8)).equals(Rational(3, 2)))
    }

    @Test
    fun multiply() {
        assertTrue(Rational(1, 2).multiply(Rational(1, 2)).equals(Rational(1, 4)))
        assertTrue(Rational(1, 2).multiply(Rational(0, 2)).equals(Rational(0, 1)))
        assertTrue(Rational(1, -7).multiply(Rational(1, 1)).equals(Rational(-1, 7)))
        assertTrue(Rational(1, 7).multiply(Rational(-1, 1)).equals(Rational(-1, 7)))
        assertTrue(Rational(3, 5).multiply(Rational(4, 7)).equals(Rational(12, 35)))
    }

    @Test
    fun divide() {
        assertTrue(Rational(1, 2).divide(Rational(1, 2)).equals(Rational(1, 1)))
        assertTrue(Rational(3, -2).divide(Rational(1, 2)).equals(Rational(-3, 1)))
        assertTrue(Rational(1, -7).divide(Rational(1, 1)).equals(Rational(-1, 7)))
        assertTrue(Rational(4, 7).divide(Rational(3, 5)).equals(Rational(20, 21)))
        assertTrue(Rational(224, 25).divide(Rational(-282475, 12544)).equals(Rational(-2809856, 7061875)))
    }

    @Test
    fun scalarMultiply() {
        assertTrue(Rational(1, 2).multiply(-1).equals(Rational(-1, 2)))
        assertTrue(Rational(1, 2).multiply(2).equals(Rational(1, 1)))
        assertTrue(Rational(3, -7).multiply(7).equals(Rational(-3, 1)))
        assertTrue(Rational(9, 13).multiply(Rational(3)).equals(Rational(27, 13)))
    }
}