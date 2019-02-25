package quantum.api.framework.utils

/**
 * A helper class as a more functional approach for kotlin components
 *
 * The amount of overridden component methods can go up to 255, although 20 should be more than enough.
 */
interface ComponentDelegate<out T> {
    fun getComponent(index: Int): T

    operator fun component1() = getComponent(1)
    operator fun component2() = getComponent(2)
    operator fun component3() = getComponent(3)
    operator fun component4() = getComponent(4)
    operator fun component5() = getComponent(5)
    operator fun component6() = getComponent(6)
    operator fun component7() = getComponent(7)
    operator fun component8() = getComponent(8)
    operator fun component9() = getComponent(9)
    operator fun component10() = getComponent(10)
    operator fun component11() = getComponent(11)
    operator fun component12() = getComponent(12)
    operator fun component13() = getComponent(13)
    operator fun component14() = getComponent(14)
    operator fun component15() = getComponent(15)
    operator fun component16() = getComponent(16)
    operator fun component17() = getComponent(17)
    operator fun component18() = getComponent(18)
    operator fun component19() = getComponent(19)
    operator fun component20() = getComponent(20)
}