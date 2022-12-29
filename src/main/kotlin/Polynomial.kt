class Polynomial {
    //int[] coefficients;
    var coefficients: Array<Rational?> = arrayOf()
    private var intCoef: Array<LongArray?>?=null
    var quotient: Polynomial? = null
    var remainder: Polynomial? = null
    var divisionDetails: MutableList<Polynomial> = mutableListOf()

    constructor(coefficients: IntArray) {
        val r = arrayOfNulls<Rational>(coefficients.size)
        for (i in coefficients.indices) {
            r[i] = Rational(coefficients[i], 1)
        }
        setPolynomial(r)
    }

    fun getDegree(): Int {
        return coefficients?.size ?: 0
    }

    constructor(coefficients: Array<IntArray>) {
        val r = arrayOfNulls<Rational>(coefficients.size)
        for (i in coefficients.indices) {
            r[i] = Rational(coefficients[i][0], coefficients[i][1])
        }
        setPolynomial(r)
    }

    constructor(coefficients: Array<Rational?>) {
        setPolynomial(coefficients)
    }
    fun polynomialEquals(coeff: Array<IntArray>):Boolean{
        if (coeff.size!= this.coefficients.size) return false
        coeff.forEachIndexed{ i, ci ->
            val comp = this.coefficients[i]?.equals(Rational(ci[0],ci[1])) ?: false
            if(!comp) return false
        }
        return true
    }
    fun polynomialEquals(coeff: IntArray):Boolean{
        if (coeff.size!= this.coefficients.size) return false
        coeff.forEachIndexed{ i, ci ->
            val comp = this.coefficients[i]?.rationalEquals(ci.toLong()) ?: false
            if(!comp) return false
        }
        return true
    }

    private fun setPolynomial(coefficients: Array<Rational?>) {
        if (coefficients[coefficients.size - 1]!!.num == 0L) {
            this.coefficients = filterLeadingZeroes(coefficients)
        } else {
            this.coefficients = coefficients
        }
    }

    fun getCoefficients(): Array<LongArray?> {
        if (intCoef == null) {
            intCoef = arrayOfNulls(coefficients.size)
        }
        for (i in coefficients.indices) {
            intCoef!![i] = longArrayOf(coefficients[i]!!.num, coefficients[i]!!.den)
        }
        return intCoef!!
    }

    fun getLeading(): Rational? {
        return coefficients[coefficients.size - 1]
    }

    fun derivative(): Polynomial {
        val nc = arrayOfNulls<Rational>(coefficients.size - 1)
        for (i in 1 until coefficients.size) {
            nc[i - 1] = coefficients[i]!!.multiply(i)
        }
        return Polynomial(nc)
    }

    fun divide(divisor: Polynomial) {
        divisionDetails = mutableListOf()
        if (coefficients.size <= divisor.coefficients.size) {
            throw RuntimeException()
        }
        var pQuotient = Polynomial(intArrayOf(0))
        var pDividend = this
        var deg = pDividend.coefficients.size - divisor.coefficients.size + 1
        //sb.append("-------------");

        //sb.append(divisor+" | "+ this);
        while (deg > 0) {
            val cf = arrayOfNulls<Rational>(deg)
            val rd = pDividend.getLeading()!!.divide(divisor.getLeading()!!)
            for (i in 0 until deg - 1) {
                cf[i] = Rational(0)
            }
            cf[deg - 1] = rd
            val pTerms = Polynomial(cf)
            pQuotient = pQuotient.add(pTerms)
            val pSil = divisor.multiply(pTerms)
            val px = pDividend.subtract(pSil)
            divisionDetails.add(pSil)
            divisionDetails.add(px)
            pDividend = px
            deg = pDividend.coefficients.size - divisor.coefficients.size + 1
        }
        var d: Int
        remainder = pDividend
        quotient = pQuotient
    }

    fun equals(p2: Polynomial?): Boolean {
        if (p2!!.coefficients.size != coefficients.size) return false
        for (i in coefficients.indices) {
            println(this.toString())
            println("comparing" + coefficients[i] + " " + p2.coefficients[i])
            if (coefficients[i] !== p2.coefficients[i]) return false
        }
        return true
    }

    override fun equals(obj: Any?): Boolean {
        val p2 = obj as Polynomial?
        return equals(p2)
    }

    fun subtract(p2: Polynomial): Polynomial {
        return addSubtract(p2, -1)
    }

    private fun addSubtract(p2: Polynomial, sign: Int): Polynomial {
        val maxDegree = Math.max(coefficients.size, p2.coefficients.size)
        val ca = arrayOfNulls<Rational>(maxDegree)
        for (i in 0 until maxDegree) {
            ca[i] = getCoefficientOfDegree(i)!!
                .add(p2.getCoefficientOfDegree(i)!!.multiply(sign)) //   +sign *p2.getCoefficientOfDegree(i);
        }
        return Polynomial(ca)
    }

    fun add(p2: Polynomial): Polynomial {
        return addSubtract(p2, 1)
    }

    fun getCoefficientOfDegree(degree: Int): Rational? {
        return if (degree >= 0 && degree < coefficients.size) {
            coefficients[degree]
        } else Rational(0, 1)
    }

    fun multiply(r: Rational?): Polynomial {
        val cp = arrayOfNulls<Rational>(coefficients.size)
        for (i in coefficients.indices) {
            cp[i] = coefficients[i]!!.multiply(r!!)
        }
        return Polynomial(cp)
    }

    fun multiply(s: Int): Polynomial {
        val cp = arrayOfNulls<Rational>(coefficients.size)
        for (i in coefficients.indices) {
            cp[i] = coefficients[i]!!.multiply(s)
        }
        return Polynomial(cp)
    }

    fun multiply(p2: Polynomial): Polynomial {
        val cp = arrayOfNulls<Rational>(coefficients.size + p2.coefficients.size - 1)
        for (i in cp.indices) {
            cp[i] = Rational(0, 1)
        }
        for (i in coefficients.indices) {
            for (j in p2.coefficients.indices) {
                val mp = i + j
                cp[mp] = cp[mp]!!.add(coefficients[i]!!.multiply(p2.coefficients[j]!!))
            }
        }
        return Polynomial(cp)
    }

    fun evaluate(r: Rational?): Rational {
        var deg = coefficients.size - 1
        var tot = Rational(0)
        while (deg > 0) {
            tot = tot.add(coefficients[deg]!!).multiply(r!!)
            deg--
        }
        tot = tot.add(coefficients[0]!!) // +coefficients[0];
        return tot
    }

    fun evaluate(x: Int): Rational {
        return evaluate(Rational(x, 1))
    }

    fun toLatex(): String {
        val sb = StringBuilder()
        var isFirstTerm = true
        for (i in getDegree() - 1 downTo 0) {
            if (coefficients[i]!!.num != 0L) {
                sb.append(termToString(coefficients[i], i, isFirstTerm, true))
                isFirstTerm = false
            }
        }
        return sb.toString()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        var isFirstTerm = true
        for (i in getDegree() - 1 downTo 0) {
            if (coefficients[i]!!.num != 0L) {
                sb.append(termToString(coefficients[i], i, isFirstTerm, false))
                isFirstTerm = false
            }
        }
        return sb.toString()
    }

    private fun termToString(coefficient: Rational?, degree: Int, isFirstTerm: Boolean, latex: Boolean): String {
        if (coefficient!!.num == 0L) return ""
        val blanksBetweenTerms = if (isFirstTerm) "" else " "
        val sign = if (coefficient.num < 0) "-" else if (isFirstTerm) "" else "+"
        val absCoefficient = Math.abs(coefficient.num)
        val rDummy = Rational(absCoefficient, coefficient.den)
        val ratPart =
            if (coefficient.den != 1L && latex) "\\u000crac{" + absCoefficient + "}{" + coefficient.den + "}" else rDummy.toString()
        val sCoefficient = if (absCoefficient == 1L && coefficient.den == 1L && degree != 0) "" else ratPart
        val xPart = if (degree == 0) "" else `var`
        val caret = if (latex) "^" else ""
        val degreePart = if (degree <= 1) "" else caret + degree + ""
        return blanksBetweenTerms + sign + sCoefficient + xPart + degreePart
    }

    companion object {
        const val `var` = "x"
        private fun filterLeadingZeroes(cf: Array<Rational?>): Array<Rational?> {
            var pt = cf.size - 1
            while (pt > 0 && cf[pt]!!.num == 0L) {
                pt--
            }
            if (pt < 0) return arrayOf(Rational(0))
            val filt = arrayOfNulls<Rational>(pt + 1)
            for (i in filt.indices) {
                filt[i] = cf[i]
            }
            return filt
        }

        fun getPolynomialWithRoots(roots: IntArray): Polynomial {
            var p = Polynomial(intArrayOf(-roots[0], 1))
            for (i in 1 until roots.size) {
                p = p.multiply(Polynomial(intArrayOf(-roots[i], 1)))
            }
            return p
        }
    }
}