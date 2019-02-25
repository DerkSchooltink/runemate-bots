package quantum.api.framework.extensions

import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Spinner

fun <T> ComboBox<T>.setSelection(value: String?) = if (value == null) selectionModel.selectFirst() else selectionModel.select(Integer.parseInt(value))

fun Spinner<Int>.setSelection(value: String?) = if (value == null) valueFactory.value = 0 else valueFactory.value = Integer.parseInt(value)

fun CheckBox.setSelection(value: String?) {
    isSelected = value?.toBoolean() ?: false
}