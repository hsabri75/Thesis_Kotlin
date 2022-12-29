class CFOperation(var X: CF, var Y: CF, type: opType) {
    var a: Long = 0
    var ax: Long = 0
    var ay: Long = 0
    var axy: Long = 0
    var b: Long = 0
    var bx: Long = 0
    var by: Long = 0
    var bxy: Long = 0

    //boolean isFinished=false;
    var terms = ArrayList<Long>()
    var inputFinished = false
    private var pX = 0
    private var pY = 0

    init {
        if (type == opType.addition) {
            ax = 1
            ay = 1
            b = 1
        } else if (type == opType.subtraction) {
            ax = 1
            ay = -1
            b = 1
        } else if (type == opType.multiplication) {
            axy = 1
            b = 1
        } else {
            ax = 1
            by = 1
        }
    }

    fun calculate() {
        println("\$x = \\u000crac{" + X.num + "}{" + X.den + "} = " + X.toString() + " $\\\\")
        println()
        println("\$y = \\u000crac{" + Y.num + "}{" + Y.den + "} = " + Y.toString() + " $\\\\")
        println()
        println("\$z = x + y $\\\\")
        println()
        println("Matrix representation of addition")
        println()
        println("$$")
        println("\\begin{bmatrix}")
        println("0 & 1 & 1 & 0\\\\")
        println("1 & 0 & 0 & 0")
        println("\\end{bmatrix}")
        println("$$")
        println()
        while (!((b == 0L) and (bx == 0L) and (by == 0L) and (bxy == 0L))) {
            nextStep()
        }
        val rx = Rational(X.num, X.den)
        val ry = Rational(Y.num, Y.den)
        val rz = rx.add(ry)
        println("So continued fraction representation of z is {\\color{red}" + terms + "}, which is equal to $\\u000crac{" + rz.num + "}{" + rz.den + "}$")
        //System.out.println(this.terms);
    }

    private fun nextStep() {
        val e = checkZTerm()
        if (e != -1L) {
            println("\$r = \\lfloor\\cfrac{$a}{$b}\\rfloor = \\lfloor\\cfrac{$ax}{$bx}\\rfloor =\\lfloor\\cfrac{$ay}{$by}\\rfloor = \\lfloor\\cfrac{$axy}{$bxy}\\rfloor = $e   \\Rightarrow $")
            egest(e)
            return
        } else {
            var chooseX = false
            chooseX = if (b != 0L && bx != 0L && by != 0L) {
                val r0 = Rational(a, b)
                val rx = Rational(ax, bx)
                val ry = Rational(ay, by)
                val sx = rx.subtract(r0).absolute()
                val sy = ry.subtract(r0).absolute()
                val dif = sx.subtract(sy)
                dif.num > 0
            } else {
                (b == 0L) and (bx != 0L) and (by == 0L) or ((b != 0L) and (bx == 0L) and (by != 0L))
            }
            if (chooseX) {
                println("$|\\u000crac{$a}{$b} - \\u000crac{$ax}{$bx}| > |\\u000crac{$a}{$b} - \\u000crac{$ay}{$by}|  \\Rightarrow $")
                ingestX()
                pX++
            } else {
                println("$|\\u000crac{$a}{$b} - \\u000crac{$ax}{$bx}| \\ngtr |\\u000crac{$a}{$b} - \\u000crac{$ay}{$by}|  \\Rightarrow $")
                ingestY()
                pY++
            }
        }
    }

    private fun checkZTerm(): Long {
        if (inputFinished) return a / b
        if (b == 0L || bx == 0L || by == 0L || bxy == 0L) {
            return -1
        }
        val f = a / b
        val fx = ax / bx
        val fy = ay / by
        val fxy = axy / bxy
        return if (f == fx && f == fy && f == fxy) {
            f
        } else -1
    }

    private fun ingestX() {
        if (pX >= X.element.size) {
            a = ax
            b = bx
            ay = axy
            by = bxy
            println(
                """Substitute $\infty$ for x
\\
${op}"""
            )
        } else {
            val p = X.element[pX]
            val tempa = a
            a = ax
            ax = tempa + p * ax
            val tempay = ay
            ay = axy
            axy = tempay + p * axy
            val tempb = b
            b = bx
            bx = tempb + p * bx
            val tempby = by
            by = bxy
            bxy = tempby + p * bxy
            println(
                """Substitute ${"$"}$p+\u000crac{1}{x}$ for x
\\
${op}"""
            )
        }
    }

    private fun ingestY() {
        if (pY >= Y.element.size) {
            a = ay
            b = by
            ax = axy
            bx = bxy
            println(
                """Substitute $\infty$ for y
\\
${op}"""
            )
        } else {
            val q = Y.element[pY]
            val tempa = a
            a = ay
            ay = tempa + q * ay
            val tempax = ax
            ax = axy
            axy = tempax + q * axy
            val tempb = b
            b = by
            by = tempb + q * by
            val tempbx = bx
            bx = bxy
            bxy = tempbx + q * bxy
            println(
                """Substitute ${"$"}$q+\u000crac{1}{y}$ for y
\\
${op}"""
            )
        }
    }

    private fun egest(r: Long) {
        terms.add(r)
        var temp = a
        a = b
        b = temp - r * b
        temp = ax
        ax = bx
        bx = temp - r * bx
        temp = ay
        ay = by
        by = temp - r * by
        temp = axy
        axy = bxy
        bxy = temp - r * bxy
        println(
            """
                Extract {\color{red}$r} and substitute $\u000crac{1}{z-$r}$
                \\
                ${op}
                """.trimIndent()
        )
    }

    val op_: String
        get() {
            val sb = StringBuilder()
            sb.append("[$a $ax $ay $axy\\")
            sb.append("$b $bx $by $bxy]")
            return sb.toString()
        }
    val op: String
        get() {
            val sb = StringBuilder()
            sb.append("$$\n")
            sb.append("\\begin{bmatrix}\n")
            sb.append("$a & $ax & $ay & $axy\\\\ \n")
            sb.append("$b & $bx & $by & $bxy \n")
            sb.append("\\end{bmatrix}\n")
            sb.append("$$\n")
            return sb.toString()
        }


    enum class opType {
        addition, subtraction, multiplication, division
    }
}