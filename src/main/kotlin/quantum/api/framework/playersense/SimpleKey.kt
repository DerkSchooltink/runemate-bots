package quantum.api.framework.playersense

open class SimpleKey(override val key: String, override val supplier: () -> Any) : QuantumSenseEntry {

    override fun getValue(): Any {
        /*
        val key = "quantum_$key"
        var v: Any? = PlayerSense.get(key)
        if(v == null) {
            v = supplier()
            PlayerSense.put(key, v)
        }
        */
        return supplier()
    }
}