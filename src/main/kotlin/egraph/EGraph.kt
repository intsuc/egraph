package egraph

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap

// TODO: maintain congruence invariant
class EGraph {
    private val unionFind: UnionFind = UnionFind()
    private val classes: Int2ObjectMap<EClass> = Int2ObjectOpenHashMap()
    private val hashcons: Object2IntMap<ENode?> = Object2IntOpenHashMap<ENode?>().apply { defaultReturnValue(NULL_ID) }

    fun add(n: ENode): EClassId = lookup(n).takeIf { it != NULL_ID } ?: unionFind.new()
        .let { unionFind.find(it) }
        .also {
            classes[it] = EClass(mutableSetOf(n))
            hashcons[n] = it
        }

    fun merge(a: EClassId, b: EClassId) {
        unionFind.union(a, b)
        classes[unionFind.find(a)] = classes[a] union classes[b]
    }

    fun find(a: EClassId): EClassId = unionFind.find(a)

    private fun lookup(n: ENode): EClassId = hashcons.getInt(canonicalize(n))

    private fun canonicalize(n: ENode): ENode = n.map { unionFind.find(it) }
}
