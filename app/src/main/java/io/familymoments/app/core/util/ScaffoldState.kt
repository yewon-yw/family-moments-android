package io.familymoments.app.core.util

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ObserverModifierNode
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.node.observeReads
import androidx.compose.ui.platform.InspectorInfo

@Stable
class ScaffoldState {
    var hasShadow by mutableStateOf(false)
        private set

    var hasBackButton by mutableStateOf(false)
        private set

    var hasIcon by mutableStateOf(false)
        private set

    fun updateShadow(hasShadow: Boolean) {
        this.hasShadow = hasShadow
    }

    fun updateBackButton(hasBackButton: Boolean) {
        this.hasBackButton = hasBackButton
    }

    fun updateIcon(hasIcon: Boolean) {
        this.hasIcon = hasIcon
    }
}

val LocalScaffoldState = compositionLocalOf { ScaffoldState() }

private class ScaffoldNode(
    var hasShadow: Boolean,
    var hasBackButton: Boolean,
    var hasIcon: Boolean,
) : Modifier.Node(), CompositionLocalConsumerModifierNode, ObserverModifierNode {
    private var observedValue: ScaffoldState? = null
    override fun onAttach() {
        onObservedReadsChanged()
    }

    override fun onDetach() {
        observedValue = null
    }

    override fun onObservedReadsChanged() {
        observeReads {
            observedValue = currentValueOf(LocalScaffoldState).let {
                it.updateShadow(hasShadow)
                it.updateBackButton(hasBackButton)
                it.updateIcon(hasIcon)
                it
            }
        }
    }
}

private data class ScaffoldElement(
    val hasShadow: Boolean,
    val hasBackButton: Boolean,
    val hasIcon: Boolean,
) : ModifierNodeElement<ScaffoldNode>() {
    override fun InspectorInfo.inspectableProperties() {
        name = "topAppBar"
        properties["hasShadow"] = hasShadow
        properties["hasBackButton"] = hasBackButton
        properties["hasIcon"] = hasIcon
    }

    override fun create() =
        ScaffoldNode(hasShadow = hasShadow, hasBackButton = hasBackButton, hasIcon = hasIcon)

    override fun update(node: ScaffoldNode) {
        node.hasShadow = hasShadow
        node.hasBackButton = hasBackButton
        node.hasIcon = hasIcon
    }
}

fun Modifier.scaffoldState(
    hasShadow: Boolean,
    hasBackButton: Boolean,
    hasIcon: Boolean = true,
) = this then ScaffoldElement(hasShadow, hasBackButton, hasIcon)
