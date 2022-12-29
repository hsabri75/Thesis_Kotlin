class CF(var num: Long, var den: Long) {
    var element = ArrayList<Long>()

    init {
        fillCF()
    }

    constructor(r: Rational) : this(r.num, r.den)

    private fun fillCF() {
        var n = num
        var d = den
        var quot = Math.floorDiv(n, d)
        var rem = n - d * quot
        while (rem != 0L) {
            element.add(quot)
            n = d
            d = rem
            quot = Math.floorDiv(n, d)
            rem = n - d * quot
        }
        element.add(quot)
        //System.out.println(element);
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[")
        for (i in 0 until element.size - 1) {
            sb.append(element[i].toString() + ", ")
        }
        sb.append(element[element.size - 1].toString() + "]")
        return sb.toString()
    }
}