package com.example.denettest.model

import androidx.compose.runtime.snapshots.SnapshotStateList

interface Node {
    val parent: Node?
    val children: SnapshotStateList<Node>
    val name: Int
        get() = hashCode() and 0b11111111111111111111
}