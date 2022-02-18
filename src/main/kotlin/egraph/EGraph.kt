package egraph

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.ints.IntArrayList
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap

@Suppress("NAME_SHADOWING")
class EGraph {
    private val unionFind: UnionFind = UnionFind()
    private val classes: Int2ObjectMap<EClass> = Int2ObjectOpenHashMap()
    private val hashcons: Object2IntMap<ENode> = Object2IntOpenHashMap<ENode>().apply { defaultReturnValue(NULL_ID) }
    private val worklist: IntArrayList = IntArrayList()

    fun add(n: ENode): EClassId = lookup(n).takeIf { it != NULL_ID } ?: unionFind.new().also { id ->
        classes[id] = EClass(mutableSetOf(n), mutableMapOf())
        hashcons[n] = id
        n.arguments.forEach { classes[it].parents[n] = id }
    }

    fun merge(a: EClassId, b: EClassId): EClassId {
        val a = find(a)
        val b = find(b)
        if (a == b) return a

        val id = unionFind.union(a, b)

        classes[id] = classes[a] union classes[b]
        classes -= if (id == a) b else a
        classes[id].nodes.forEach { hashcons[it] = id }

        worklist += id
        rebuild()

        return id
    }

    fun find(a: EClassId): EClassId = unionFind.find(a)

    fun equals(a: EClassId, b: EClassId): Boolean = find(a) == find(b)

    private fun lookup(n: ENode): EClassId = hashcons.getInt(canonicalize(n))

    private fun canonicalize(n: ENode): ENode = ENode(n.label, n.arguments.map { unionFind.find(it) }.toMutableList())

    private fun rebuild() {
        while (!worklist.isEmpty) {
            IntOpenHashSet(worklist).forEach { repair(classes[it]) }
            worklist.clear()
        }
    }

    private fun repair(c: EClass) {
        c.parents.forEach { (n, id) ->
            hashcons -= n
            hashcons[canonicalize(n)] = find(id)
        }
        val parents = mutableMapOf<ENode, EClassId>()
        c.parents.forEach { (n, id) ->
            val n = canonicalize(n)
            parents[n]?.let { merge(id, it) }
            parents[n] = find(id)
        }
        c.parents.clear()
        c.parents += parents
    }
}
