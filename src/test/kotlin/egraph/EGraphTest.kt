package egraph

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EGraphTest {
    @Test
    fun differentNodes() {
        EGraph().run {
            val a = add(ENode("a"))
            val b = add(ENode("b"))
            assertFalse(equals(a, b))

            merge(a, b)
            assertTrue(equals(a, b))
        }
    }

    @Test
    fun sameNodes() {
        EGraph().run {
            val a1 = add(ENode("a"))
            val a2 = add(ENode("a"))
            assertTrue(equals(a1, a2))

            merge(a1, a2)
            assertTrue(equals(a1, a2))
        }
    }

    @Test
    fun mergeFunctions() {
        EGraph().run {
            val a = add(ENode("a"))
            val fa = add(ENode("f", a))
            val ga = add(ENode("g", a))
            assertFalse(equals(fa, ga))

            merge(fa, ga)
            assertTrue(equals(fa, ga))
        }
    }

    @Test
    fun alreadyEquivalent() {
        EGraph().run {
            val a1 = add(ENode("a"))
            val b = add(ENode("b"))
            assertFalse(equals(a1, b))

            merge(a1, b)
            assertTrue(equals(a1, b))

            val a2 = add(ENode("a"))
            assertTrue(equals(a1, a2))
        }
    }

    @Test
    fun upwardMerging() {
        EGraph().run {
            val a = add(ENode("a"))
            val b = add(ENode("b"))
            val c = add(ENode("c"))
            val fab = add(ENode("f", a, b))
            val fac = add(ENode("f", a, c))
            val gfab = add(ENode("g", fab))
            val gfac = add(ENode("g", fac))
            assertFalse(equals(b, c))
            assertFalse(equals(fab, fac))
            assertFalse(equals(gfab, gfac))

            merge(b, c)
            assertTrue(equals(b, c))
            assertTrue(equals(fab, fac))
            assertTrue(equals(gfab, gfac))
        }
    }

    @Test
    fun recursive() {
        EGraph().run {
            val a = add(ENode("a"))
            val fa = add(ENode("f", a))
            assertFalse(equals(a, fa))

            merge(a, fa)
            assertTrue(equals(a, fa))

            val ffa = add(ENode("f", fa))
            assertTrue(equals(fa, ffa))

            val fffa = add(ENode("f", ffa))
            assertTrue(equals(ffa, fffa))
        }
    }
}
