package egraph

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UnionFindTest {
    @Test
    fun test() {
        UnionFind().run {
            val a = new()
            val b = new()
            val c = new()

            assertEquals(a, find(a))
            assertEquals(b, find(b))
            assertEquals(c, find(c))

            union(a, b)

            assertTrue(equals(a, b))
            assertFalse(equals(a, c))
            assertFalse(equals(b, c))

            assertEquals(a, find(a))
            assertEquals(a, find(b))
            assertEquals(c, find(c))

            union(b, c)

            assertTrue(equals(c, b))
            assertTrue(equals(b, a))
            assertTrue(equals(c, a))

            assertEquals(a, find(a))
            assertEquals(a, find(b))
            assertEquals(a, find(c))
        }
    }
}
