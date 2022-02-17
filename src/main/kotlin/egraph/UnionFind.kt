package egraph

import it.unimi.dsi.fastutil.bytes.ByteArrayList
import it.unimi.dsi.fastutil.ints.IntArrayList

class UnionFind(capacity: Int = 10) {
    private val parents: IntArrayList = IntArrayList(capacity)
    private val ranks: ByteArrayList = ByteArrayList(capacity)

    private inline var Int.parent
        get(): Int = parents.getInt(this)
        set(value) {
            parents[this] = value
        }

    private inline var Int.rank
        get(): Byte = ranks.getByte(this)
        set(value) {
            ranks[this] = value
        }

    fun new(): Int = parents.size.also {
        parents += it
        ranks += 0
    }

    fun union(x1: Int, x2: Int) {
        val root1 = find(x1)
        val root2 = find(x2)

        if (root1 == root2) return

        val rank1 = root1.rank
        val rank2 = root2.rank

        if (rank1 < rank2) {
            root1.parent = root2
        } else if (rank1 > rank2) {
            root2.parent = root1
        } else {
            root2.parent = root1
            root1.rank = root1.rank.inc()
        }
    }

    fun find(x: Int): Int {
        var self = x
        while (self != self.parent) {
            val grandparent = self.parent.parent
            self.parent = grandparent
            self = grandparent
        }
        return self
    }

    fun equals(x1: Int, x2: Int): Boolean = find(x1) == find(x2)
}
