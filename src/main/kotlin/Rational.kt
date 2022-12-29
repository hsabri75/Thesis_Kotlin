class Rational {
    var num: Long = 0
    var den: Long = 0

    constructor(num: Int, den: Int) {
        if (den == 0) throw RuntimeException()
        setNumDen(num.toLong(), den.toLong())
    }

    constructor(num: Int) : this(num.toLong(), 1)

    @JvmOverloads
    constructor(num: Long, den: Long = 1) {
        if (den == 0L) throw RuntimeException("Zero denominator")
        setNumDen(num, den)
    }

    fun rationalEquals(i: Long):Boolean{
        return (this.den==1L && i==this.num)
    }

    private fun setNumDen(n: Long, d: Long) {
        if (n == 0L) {
            num = 0
            den = 1
            return
        }
        val sn = if (n > 0) 1 else -1
        val sd = if (d > 0) 1 else -1
        val sign = if (sn * sd > 0) 1 else -1
        val nm = Math.abs(n)
        val dn = Math.abs(d)
        val gcd = gcd(nm, dn)
        num = sign * nm / gcd
        den = dn / gcd
    }

    fun add(r2: Rational): Rational {
        return Rational(num * r2.den + r2.num * den, den * r2.den)
    }

    fun subtract(r2: Rational): Rational {
        return Rational(num * r2.den - r2.num * den, den * r2.den)
    }

    fun multiply(r2: Rational): Rational {
        return Rational(num * r2.num, den * r2.den)
    }

    fun divide(r2: Rational): Rational {
        var numResult = num * r2.den
        var denResult = den * r2.num
        if (numResult == 0L) return Rational(0)
        val sign = if (numResult * denResult > 0) 1 else -1
        numResult = sign * Math.abs(numResult)
        denResult = Math.abs(denResult)
        return Rational(numResult, denResult)
    }

    fun absolute(): Rational {
        return Rational(Math.abs(num), den)
    }

    fun multiply(n: Int): Rational {
        return Rational(num * n, den)
    }

    override fun equals(obj: Any?): Boolean {
        val r2 = obj as Rational?
        return num == r2!!.num && den == r2.den
    }

    override fun toString(): String {
        return if (den == 1L) {
            num.toString() + ""
        } else {
            "$num/$den"
        }
    }

    companion object {
        fun gcd(n1: Long, n2: Long): Long {
            if (n2 == 0L) throw RuntimeException()
            //if(n1==n2) return n1;
            if (n1 == 1L) return 1
            //if(n2==1) return n2;
            var num1 = Math.max(n1, n2)
            var num2 = Math.min(n1, n2)
            //int div= num1/num2;
            var rem = num1 % num2
            do {
                rem = num1 % num2
                num1 = num2
                num2 = rem
            } while (rem > 0)
            return num1
        }
    }
}