import java.util.*

class Sturm(var p: Polynomial, var intervals: Array<Rational>) {
    var pSturm: Array<Polynomial?> = arrayOf()


    constructor(p: Polynomial, intervals: IntArray?) : this(p, Arrays.stream(intervals).mapToObj<Rational> { it: Int ->
        Rational(   it    ) }.toArray { size -> arrayOfNulls<Rational>(size) } )


    fun findSeries() {
        val dg = p.getDegree()
        pSturm = arrayOfNulls(dg)
        pSturm[0] = p
        pSturm[1] = p.derivative()
        var pt = 2
        while (pt < p.getDegree()) {
            pSturm[pt - 2]!!.divide(pSturm[pt - 1]!!)
            pSturm[pt] = pSturm[pt - 2]!!.remainder!!.multiply(-1)
            pt++
        }
        val q = 0
    }

    override fun toString(): String {
        return _toString(true)
    }

    val signMinusInf: IntArray
        get() {
            val res = IntArray(pSturm.size)
            for (i in res.indices) {
                val p = pSturm[i]
                val r = p!!.getCoefficientOfDegree(p.getDegree() - 1)
                val deg = p.getDegree() - 1
                val ss = if (deg % 2 == 0) 1 else -1
                val v = r!!.num * ss
                res[i] = if (v > 0) 1 else -1
            }
            return res
        }
    val signInf: IntArray
        get() {
            val res = IntArray(pSturm.size)
            for (i in res.indices) {
                val p = pSturm[i]
                val r = p!!.getCoefficientOfDegree(p.getDegree() - 1)
                res[i] = if (r!!.num > 0) 1 else -1
            }
            return res
        }

    fun getSign(r: Rational?): IntArray {
        val res = IntArray(pSturm.size)
        for (i in res.indices) {
            val p = pSturm[i]
            val num = p!!.evaluate(r).num
            if (num > 0) {
                res[i] = 1
            } else if (num == 0L) {
                res[i] = 0
            } else {
                res[i] = -1
            }
        }
        return res
    }

    private fun signCount(s: IntArray): Int {
        var signCount = 0
        var isFirstSignSet = false
        var isPrevPlus = false
        for (i in s.indices) {
            if (s[i] != 0) {
                if (isFirstSignSet) {
                    val isPlus = s[i] > 0
                    if (isPlus != isPrevPlus) {
                        signCount++
                        isPrevPlus = isPlus
                    }
                } else {
                    isPrevPlus = s[i] > 0
                    isFirstSignSet = true
                }
            }
        }
        return signCount
    }

    private val latexNL = "\\\\\n\n"
    private val sDetail = arrayOf("P(x)", "P'(x)")
    private fun _toString(withDolar: Boolean): String {
        //String s="\n\\\\";
        val sb = StringBuilder()
        val sCount = IntArray(intervals.size + 2)
        sb.append(sturmToString(withDolar))
        sb.append("Sign at x= $-\\infty$: ")
        val rMinus = signMinusInf
        sCount[0] = signCount(rMinus)
        sb.append(
            """   ${signsToString(rMinus)}
$latexNL"""
        )
        for (i in intervals.indices) {
            val rat = intervals[i]
            val r = getSign(rat)
            sCount[i + 1] = signCount(r)
            sb.append("Sign at x= $rat ")
            sb.append(
                """   ${signsToString(r)}
$latexNL"""
            )
        }
        val rPlus = signInf
        sCount[sCount.size - 1] = signCount(rPlus)
        sb.append("Sign at x= $\\infty$: ")
        sb.append(
            """   ${signsToString(rPlus)}
$latexNL"""
        )
        sb.append("between $-\\infty$ and " + intervals[0] + signChangesToStatement(sCount[0] - sCount[1]) + latexNL)
        for (i in 1 until sCount.size - 2) {
            sb.append("between " + intervals[i - 1] + " and " + intervals[i] + signChangesToStatement(sCount[i] - sCount[i + 1]) + latexNL)
        }
        sb.append("between " + intervals[intervals.size - 1] + " and $\\infty$ " + signChangesToStatement(sCount[sCount.size - 2] - sCount[sCount.size - 1]) + latexNL)
        return sb.toString()
    }

    private fun signChangesToStatement(sc: Int): String {
        if (sc == 0) return " there are no real roots.\n"
        return if (sc == 1) " there is 1 real root.\n" else " there are $sc real roots.\n"
    }

    private fun sturmToString(withDolar: Boolean): String {
        val sb = StringBuilder()
        val dolar = if (withDolar) "$" else ""
        sb.append(
            """
                ${dolar}P(x)= $p$dolar
                $latexNL
                """.trimIndent()
        )
        for (i in pSturm.indices) {
            val sdet = if (i < 2) sDetail[i] + "= " else "-Remainder(P_" + (i - 2) + "(x), P_" + (i - 1) + "(x))= "
            sb.append(
                """
                    ${dolar}P_$i(x)= $sdet${pSturm[i]!!.toLatex()}$dolar
                    $latexNL
                    """.trimIndent()
            )
        }
        return sb.toString()
    }

    private fun signsToString(r: IntArray): String {
        val sb = StringBuilder()
        for (i in r.indices) {
            val sign = if (r[i] == 1) "+" else "-"
            sb.append(sign)
        }
        sb.append("  sign changes: " + signCount(r))
        return sb.toString()
    }
}