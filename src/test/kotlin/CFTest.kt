import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CFTest {
    @Test
    fun generalCF() {
        assertEquals(CF(5, 17).toString(), "[0, 3, 2, 2]")
        assertEquals(CF(7, 13).toString(), "[0, 1, 1, 6]")
        assertEquals(CF(184, 221).toString(), "[0, 1, 4, 1, 36]")
        assertEquals(CF(43, 19).toString(), "[2, 3, 1, 4]")
    }
}