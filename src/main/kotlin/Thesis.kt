import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

object Thesis {
    fun CFForThesis() {
        val c1 = CF(7, 3)
        val c2 = CF(2, 11)
        val op1 = CFOperation(c1, c2, CFOperation.opType.addition)
        op1.calculate()
    }

    fun SturmForThesis() {

        val p = Polynomial(intArrayOf(2, -10, -20, 0, 5, 1))
        val intervals = intArrayOf(-1, 0, 1)
        val st = Sturm(p, intervals)
        st.findSeries()
        println(st)

    /*
        Rational r= new Rational(-3,2);
        System.out.println(r+ ": "+ p.evaluate(r));
        for(int i=0;i<intervals.length;i++){
            int v= intervals[i];
            System.out.println(v+ ": "+ p.evaluate(v));
        }*/
    }

    fun PolynomialLongDivision() {
        val dividend = Polynomial(intArrayOf(-3, 4, -1, 1))
        val divisor = Polynomial(intArrayOf(-1, 0, 1))
        dividend.divide(divisor)
        println("\\begin{aligned}")
        val latexTab = ""
        val latexNL = "\n\\\\"
        println(latexTab + "&\\underline" + dividend.quotient!!.toLatex() + "" + latexNL)
        println("" + divisor.toLatex() + "    &" + dividend.toLatex() + "" + latexNL)
        for (i in dividend.divisionDetails.indices) {
            val und = if (i % 2 == 0) "\\underline" else ""
            println(latexTab + "&" + und + dividend.divisionDetails[i].toLatex() + "" + latexNL)
        }
        println("\\end{aligned}")
        //System.out.println(dividend.remainder);
    }

    fun toImage() {
        try {
            val width = 200
            val height = 200

            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            val bi = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            val ig2 = bi.createGraphics()
            ig2.paint = Color(255, 255, 255)
            ig2.fillRect(0, 0, bi.width, bi.height)
            val font = Font("TimesRoman", Font.BOLD, 20)
            ig2.font = font
            val message = "www.java2s.com!"
            val fontMetrics = ig2.fontMetrics
            val stringWidth = fontMetrics.stringWidth(message)
            val stringHeight = fontMetrics.ascent
            ig2.paint = Color.black
            ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4)
            ig2.drawLine(10, 10, 30, 30)
            ImageIO.write(bi, "PNG", File("yourImageName.PNG"))
            //ImageIO.write(bi, "JPEG", new File("c:\\yourImageName.JPG"));
            //ImageIO.write(bi, "gif", new File("c:\\yourImageName.GIF"));
            //ImageIO.write(bi, "BMP", new File("c:\\yourImageName.BMP"));
        } catch (ie: IOException) {
            ie.printStackTrace()
        }
    }


}