package egraph

import it.unimi.dsi.fastutil.bytes.ByteArrayList
import it.unimi.dsi.fastutil.ints.IntArrayList

class UnionFind(capacity: Int = 10) {
    private val parents: IntArrayList = IntArrayList(capacity)
    private val ranks: ByteArrayList = ByteArrayList(capacity)

    fun new(): Int = parents.size.also {
        parents += it
        ranks += 0
    }

    fun union(x1: Int, x2: Int) {
        val root1 = find(x1)
        val root2 = find(x2)

        if (root1 == root2) return

        val rank1 = ranks.getByte(root1)
        val rank2 = ranks.getByte(root2)

        if (rank1 < rank2) {
            parents[root1] = root2
        } else if (rank1 > rank2) {
            parents[root2] = root1
        } else {
            parents[root2] = root1
            ranks[root1] = (ranks.getByte(root1) + 1).toByte()
        }
    }

    fun find(x: Int): Int {
        var self = x
        while (self != parents.getInt(self)) {
            val grandparent = parents.getInt(parents.getInt(self))
            parents[self] = grandparent
            self = grandparent
        }
        return self
    }

    fun equals(x1: Int, x2: Int): Boolean = find(x1) == find(x2)
}
