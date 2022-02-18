package egraph

class EClass(
    private val nodes: MutableSet<ENode> = mutableSetOf(),
) {
    infix fun union(that: EClass): EClass = EClass((this.nodes union that.nodes).toMutableSet())
}
