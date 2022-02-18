package egraph

// TODO: maintain congruence invariant
class EGraph(
    private val unionFind: UnionFind = UnionFind(),
    private val classes: MutableMap<EClassId, EClass> = mutableMapOf(),
    private val hashcons: MutableMap<ENode, EClassId> = mutableMapOf(),
) {
    fun add(n: ENode): EClassId = lookup(n) ?: unionFind.new()
        .let { unionFind.find(it) }
        .also {
            classes[it] = EClass(mutableSetOf(n))
            hashcons[n] = it
        }

    fun merge(a: EClassId, b: EClassId) {
        unionFind.union(a, b)
        classes[unionFind.find(a)] = classes[a]!! union classes[b]!!
    }

    fun find(a: EClassId): EClassId = unionFind.find(a)

    private fun lookup(n: ENode): EClassId? = hashcons[canonicalize(n)]

    private fun canonicalize(n: ENode): ENode = n.map { unionFind.find(it) }
}
