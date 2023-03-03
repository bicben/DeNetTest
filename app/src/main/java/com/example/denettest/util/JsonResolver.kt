package com.example.denettest.util

import android.util.JsonReader
import android.util.JsonToken
import android.util.JsonWriter
import com.example.denettest.model.Node
import com.example.denettest.presentation.NodeImpl
import java.io.StringReader
import java.io.StringWriter
import javax.inject.Singleton

@Singleton
class JsonResolver {

    fun saveNodesToJson(initialNode: Node): String {
        val stringWriter = StringWriter()
        return JsonWriter(stringWriter).use { writer ->
            writer.setIndent(" ")
            writeNode(initialNode, writer)
            stringWriter.buffer.toString()
        }
    }

    private fun writeNode(node: Node, writer: JsonWriter) {
        writer.beginObject()
        writer.name(CHILDREN)
        if (node.children.isEmpty()) writer.nullValue()
        else {
            writer.beginArray()
            node.children.forEach {
                writeNode(it, writer)
            }
            writer.endArray()
        }
        writer.endObject()
    }

    fun loadNodesFromJson(json: String): Node {
        return if (json.isNotEmpty())
            JsonReader(StringReader(json)).use { reader ->
                if (reader.peek() == JsonToken.BEGIN_OBJECT)
                    readNode(reader, null)
                else NodeImpl(null)
            }
        else NodeImpl(null)
    }

    private fun readNode(reader: JsonReader, parent: Node?): Node {
        return with(reader) {
            beginObject()
            val node = NodeImpl(parent)
            if (nextName() == CHILDREN && peek() != JsonToken.NULL) {
                beginArray()
                while (hasNext()) {
                    node.children.add(readNode(reader, node))
                }
                endArray()
            } else skipValue()
            endObject()
            node
        }
    }

    companion object {
        const val CHILDREN = "children"
    }
}