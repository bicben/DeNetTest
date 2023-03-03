package com.example.denettest.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.denettest.model.Node
import com.example.denettest.util.FileResolver
import com.example.denettest.util.JsonResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fileResolver: FileResolver,
    private val jsonResolver: JsonResolver,
) : ViewModel() {

    private val _currentNode = MutableStateFlow<Node?>(null)
    val currentNode = _currentNode.asStateFlow()

    private val _currentChildren: MutableList<Node> = mutableStateListOf()
    val currentChildren: List<Node> = _currentChildren

    private var initialNode: Node? = null

    fun selectNode(nextNode: Node) {
        setCurrentNode(nextNode)
    }

    private fun setCurrentNode(node: Node) {
        _currentNode.value = node
        _currentChildren.clear()
        _currentChildren.addAll(node.children)
    }

    fun addChild(parent: Node) {
        val newNode = NodeImpl(parent)
        _currentNode.value?.children?.add(newNode)
        _currentChildren.add(newNode)
    }

    fun deleteNode(node: Node) {
        _currentNode.value?.children?.remove(node)
        _currentChildren.remove(node)
    }

    fun loadNodes() {
        viewModelScope.launch {
            val string = fileResolver.loadNodesFromFile()
            val node = jsonResolver.loadNodesFromJson(string)
            initialNode = node
            setCurrentNode(node)
        }
    }

    fun saveNodes() {
        initialNode?.let {
            viewModelScope.launch {
                val string = jsonResolver.saveNodesToJson(it)
                fileResolver.saveNodesToFile(string)
            }
        }
    }
}