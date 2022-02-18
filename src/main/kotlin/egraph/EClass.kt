package egraph

class EClass(
    val nodes: MutableSet<ENode>,
    val parents: MutableMap<ENode, EClassId>,
) {
    infix fun union(that: EClass): EClass = EClass((this.nodes union that.nodes).toMutableSet(), (this.parents + that.parents).toMutableMap())

    override fun toString(): String = nodes.joinToString(", ", "{", "}")
}
