package egraph

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class EGraphTest {
    @Test
    fun differentNodes() {
        EGraph().run {
            val a = add(ENode("a"))
            val b = add(ENode("b"))
            assertNotEquals(find(a), find(b))

            merge(a, b)
            assertEquals(find(a), find(b))
        }
    }

    @Test
    fun sameNodes() {
        EGraph().run {
            val a1 = add(ENode("a"))
            val a2 = add(ENode("a"))
            assertEquals(find(a1), find(a2))

            merge(a1, a2)
            assertEquals(find(a1), find(a2))
        }
    }

    @Test
    fun mergeFunctions() {
        EGraph().run {
            val a = add(ENode("a"))
            val f = add(ENode("f", a))
            val g = add(ENode("g", a))
            assertNotEquals(find(f), find(g))

            merge(f, g)
            assertEquals(find(f), find(g))
        }
    }

    @Test
    fun mergeArguments() {
        EGraph().run {
            val a = add(ENode("a"))
            val b = add(ENode("b"))
            val f = add(ENode("f", a))
            val g = add(ENode("g", b))
            assertNotEquals(find(f), find(g))

            merge(a, b)
            assertEquals(find(f), find(g))
        }
    }

    @Test
    fun alreadyEquivalent() {
        EGraph().run {
            val a1 = add(ENode("a"))
            val b = add(ENode("b"))
            assertNotEquals(find(a1), find(b))

            merge(a1, b)
            assertEquals(find(a1), find(b))

            val a2 = add(ENode("a"))
            assertEquals(find(a1), find(a2))
        }
    }
}
