package egraph

data class ENode(
    private val label: String,
    private val arguments: MutableList<EClassId> = mutableListOf(),
) {
    constructor(label: String, vararg arguments: EClassId) : this(label, arguments.toMutableList())

    fun map(transform: (EClassId) -> EClassId): ENode = ENode(label, arguments.map(transform).toMutableList())
}
