package com.example.denettest.presentation

import androidx.compose.runtime.mutableStateListOf
import com.example.denettest.model.Node

class NodeImpl(override val parent: Node?) : Node {
    override val children = mutableStateListOf<Node>()
}