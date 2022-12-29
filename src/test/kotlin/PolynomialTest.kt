import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PolynomialTest {
    @Test
    fun derivative() {
        assertTrue( Polynomial(intArrayOf(3, 6, 2)).derivative().polynomialEquals(intArrayOf(6, 4)))
        assertTrue( Polynomial(intArrayOf(0, -7)).derivative().polynomialEquals(intArrayOf(-7)))
        assertTrue( Polynomial(intArrayOf(1, 3)).derivative().polynomialEquals(intArrayOf(3)))
        assertTrue( Polynomial(intArrayOf(2, 3,5,-2,1)).derivative().polynomialEquals(intArrayOf(3,10,-6,4)))
        assertTrue( Polynomial(intArrayOf(3,-2,0,-5,1)).derivative().polynomialEquals(intArrayOf(-2,0,-15,4)))

    }

    @Test
    fun evaluate() {
        assertEquals(Polynomial(intArrayOf(3, 7, 4)).evaluate(1), Rational(14))
        assertEquals(Polynomial(intArrayOf(3, 7, 4)).evaluate(2), Rational(33))
        assertEquals(Polynomial(intArrayOf(3, 7, 4)).evaluate(0), Rational(3))
        assertEquals(Polynomial(intArrayOf(3, 7, 4)).evaluate(-1), Rational(0))
        assertEquals(Polynomial(intArrayOf(15, -23, 0, 3, 1)).evaluate(-2), Rational(53))
        assertEquals(Polynomial(intArrayOf(15, -23, 0, 3, 1)).evaluate(-1), Rational(36))
        assertEquals(Polynomial(intArrayOf(15, -23, 0, 3, 1)).evaluate(0), Rational(15))
        assertEquals(Polynomial(intArrayOf(15, -23, 0, 3, 1)).evaluate(1), Rational(-4))
        assertEquals(Polynomial(intArrayOf(15, -23, 0, 3, 1)).evaluate(2), Rational(9))
    }

    @Test
    fun scalarMultiply() {
        assertTrue(Polynomial(intArrayOf(1, 7, 3)).multiply(2).polynomialEquals(intArrayOf(2,14,6)))
        assertTrue(Polynomial(intArrayOf(1, -7, 3)).multiply(2).polynomialEquals(intArrayOf(2,-14,6)))
        assertTrue(Polynomial(intArrayOf(1, 7, -3,0,8)).multiply(2).polynomialEquals(intArrayOf(2,14,-6,0,16)))
        assertTrue(Polynomial(intArrayOf(1, 7, 3)).multiply(0).polynomialEquals(intArrayOf(0)))
        assertTrue(Polynomial(intArrayOf(1, -7, 3)).multiply(-1).polynomialEquals(intArrayOf(-1,7,-3)))
        assertTrue(Polynomial(intArrayOf(1, -7, -3)).multiply(-3).polynomialEquals(intArrayOf(-3,21,9)))
    }

    @Test
    fun multiply() {
        assertTrue(Polynomial(intArrayOf(-1)).multiply(Polynomial(intArrayOf(3))).polynomialEquals(intArrayOf(-3)))

        assertTrue(Polynomial(intArrayOf(1,1)).multiply(Polynomial(intArrayOf(1,1))).polynomialEquals(intArrayOf(1,2,1)))
        assertTrue(Polynomial(intArrayOf(1,1)).multiply(Polynomial(intArrayOf(-1,1))).polynomialEquals(intArrayOf(-1,0,1)))
        assertTrue(Polynomial(intArrayOf(2,1)).multiply(Polynomial(intArrayOf(-2,1))).polynomialEquals(intArrayOf(-4,0,1)))

        assertTrue(Polynomial(intArrayOf(5,3,1)).multiply(Polynomial(intArrayOf(-7,2))).polynomialEquals(intArrayOf(-35,-11,-1,2)))

        assertTrue(Polynomial(intArrayOf(-17,5)).multiply(Polynomial(intArrayOf(11,-8,3))).polynomialEquals(intArrayOf(-187,191,-91,15)))
        assertTrue(Polynomial(intArrayOf(11,-8,3)).multiply(Polynomial(intArrayOf(-17,5))).polynomialEquals(intArrayOf(-187,191,-91,15)))

        assertTrue(Polynomial(intArrayOf(0)).multiply(Polynomial(intArrayOf(1,0,4,5))).polynomialEquals(intArrayOf(0)))
        assertTrue(Polynomial(intArrayOf(0,1)).multiply(Polynomial(intArrayOf(1,-3,7))).polynomialEquals(intArrayOf(0,1,-3,7)))
        assertTrue(Polynomial(intArrayOf(0,0,1)).multiply(Polynomial(intArrayOf(1,-3,7))).polynomialEquals(intArrayOf(0,0,1,-3,7)))
    }

    @Test
    fun add() {
        assertTrue( Polynomial(intArrayOf(1)).add(Polynomial(intArrayOf(1))).polynomialEquals(intArrayOf(2)))
        assertTrue( Polynomial(intArrayOf(1)).add(Polynomial(intArrayOf(5))).polynomialEquals(intArrayOf(6)))
        assertTrue( Polynomial(intArrayOf(1)).add(Polynomial(intArrayOf(-1))).polynomialEquals(intArrayOf(0)))
        assertTrue( Polynomial(intArrayOf(2,-3,7)).add(Polynomial(intArrayOf(-4,3,2))).polynomialEquals(intArrayOf(-2,0,9)))
        assertTrue( Polynomial(intArrayOf(2)).add(Polynomial(intArrayOf(5,-3,8))).polynomialEquals(intArrayOf(7,-3,8)))
        assertTrue( Polynomial(intArrayOf(2,5,3)).add(Polynomial(intArrayOf(-2,-5,-3))).polynomialEquals(intArrayOf(0)))
        assertTrue( Polynomial(intArrayOf(0)).add(Polynomial(intArrayOf(-2,-5))).polynomialEquals(intArrayOf(-2,-5)))
        assertTrue( Polynomial(intArrayOf(0,1,1,0,1)).add(Polynomial(intArrayOf(1,-2,6,-4,-7,10,3,2,4,5))).polynomialEquals(intArrayOf(1,-1,7,-4,-6,10,3,2,4,5)))
    }

    @Test
    fun subtract() {
        assertTrue( Polynomial(intArrayOf(1)).subtract(Polynomial(intArrayOf(1))).polynomialEquals(intArrayOf(0)))
        assertTrue( Polynomial(intArrayOf(5,7,13)).subtract(Polynomial(intArrayOf(5,7,13))).polynomialEquals(intArrayOf(0)))
        assertTrue( Polynomial(intArrayOf(2,-3,7)).subtract(Polynomial(intArrayOf(-4,3,2))).polynomialEquals(intArrayOf(6,-6,5)))
        assertTrue( Polynomial(intArrayOf(2)).subtract(Polynomial(intArrayOf(5,-3,8))).polynomialEquals(intArrayOf(-3,3,-8)))
        assertTrue( Polynomial(intArrayOf(2)).subtract(Polynomial(intArrayOf(-2))).polynomialEquals(intArrayOf(4)))
        assertTrue( Polynomial(intArrayOf(2,5)).subtract(Polynomial(intArrayOf(-2,-5))).polynomialEquals(intArrayOf(4,10)))

        assertTrue( Polynomial(arrayOf(intArrayOf(5, 1), intArrayOf(430, 17))).subtract(
            Polynomial(arrayOf(intArrayOf(4730, 289), intArrayOf(430, 17))))
            .polynomialEquals(arrayOf(intArrayOf(-3285, 289))))

    }

    @Test
    fun filterLeadingZeroes() {
        assertTrue(
            Polynomial(intArrayOf(1, 2, 3)).polynomialEquals(intArrayOf(1, 2,3))
        )
        assertTrue(
            Polynomial(intArrayOf(0, 2, 3)).polynomialEquals(intArrayOf(0, 2,3))
        )
        assertTrue(
            Polynomial(intArrayOf(1, 2, 3,0)).polynomialEquals(intArrayOf(1,2,3))
        )
        assertTrue(
            Polynomial(intArrayOf(0, 0, 0)).polynomialEquals(intArrayOf(0))
        )

    }

    @Test
    fun divide() {
        val p2 = Polynomial(intArrayOf(3, 8, 2, -6, -10))
        p2.divide(Polynomial(intArrayOf(2, 2, 2)))
        assertTrue(p2.quotient!!.polynomialEquals(intArrayOf(4, 2,-5)))
        assertTrue(p2.remainder!!.polynomialEquals(arrayOf(intArrayOf(-5, 1), intArrayOf(-4, 1))))

        val p3 = Polynomial(intArrayOf(-10, 1, -5, 2))
        p3.divide(Polynomial(intArrayOf(1, -4, 1)))
        assertTrue(p3.quotient!!.polynomialEquals( arrayOf(intArrayOf(3, 1), intArrayOf(2, 1))))
        assertTrue(p3.remainder!!.polynomialEquals( arrayOf(intArrayOf(-13, 1), intArrayOf(11, 1))))

        val p4 = Polynomial(intArrayOf(-42, 0, -12, 1))
        p4.divide(Polynomial(intArrayOf(1, -2, 1)))
        assertTrue(p4.quotient!!.polynomialEquals(arrayOf(intArrayOf(-10, 1), intArrayOf(1, 1))))
        assertTrue(p4.remainder!!.polynomialEquals(arrayOf(intArrayOf(-32, 1), intArrayOf(-21, 1))))

        val p5 = Polynomial(intArrayOf(5, 35, 15))
        p5.divide(Polynomial(arrayOf(intArrayOf(-44, 9), intArrayOf(68, 9))))
        assertTrue(p5.quotient!!.polynomialEquals(arrayOf(intArrayOf(1710, 289), intArrayOf(135, 68))))
        assertTrue(p5.remainder!!.polynomialEquals(arrayOf(intArrayOf(9805, 289))))

        val p6 = Polynomial(intArrayOf(5,7,0,3))
        p6.divide(Polynomial(arrayOf(intArrayOf(-4, 7), intArrayOf(13, 5), intArrayOf(1,1))))
        assertTrue(p6.quotient!!.polynomialEquals(arrayOf(intArrayOf(-39, 5), intArrayOf(3, 1))))
        assertTrue(p6.remainder!!.polynomialEquals(arrayOf(intArrayOf(19, 35), intArrayOf(5074,175))))
    }

    @Test
    fun testToString() {
        assertEquals(Polynomial(intArrayOf(7, 0, 12, 1, -2)).toString(), "-2x4 +x3 +12x2 +7")
         assertEquals(Polynomial(intArrayOf(7, 0, 12, 1, 0)).toString(), "x3 +12x2 +7")
        assertEquals(
            Polynomial(intArrayOf(7, 0, 12, 1, 0)).multiply(Rational(1, 2)).toString(),
            "1/2x3 +6x2 +7/2"
        )

        assertEquals(Polynomial(intArrayOf(1)).toString(), "1")
        assertEquals(Polynomial(intArrayOf(-1)).toString(), "-1")
        assertEquals(Polynomial(intArrayOf(-5)).toString(), "-5")
        assertEquals(Polynomial(intArrayOf(0, 1)).toString(), "x")
        assertEquals(Polynomial(intArrayOf(0, -1)).toString(), "-x")
        assertEquals(Polynomial(intArrayOf(0, 0, 1)).toString(), "x2")
        assertEquals(Polynomial(intArrayOf(0, 0, -1)).toString(), "-x2")


    }
}