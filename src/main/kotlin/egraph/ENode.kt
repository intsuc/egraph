package egraph

data class ENode(
    val label: String,
    val arguments: MutableList<EClassId>,
) {
    constructor(label: String, vararg arguments: EClassId) : this(label, arguments.toMutableList())

    override fun toString(): String = label + arguments.joinToString(", ", "(", ")")
}
