package quantum.api.framework.playersense

interface QuantumSenseEntry {

    val key: String
    val supplier: () -> Any
    fun getValue(): Any
}