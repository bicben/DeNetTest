package com.example.denettest.util

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStreamWriter
import javax.inject.Singleton

@Singleton
class FileResolver(private val context: Context) {

    suspend fun loadNodesFromFile(): String {
        return withContext(Dispatchers.IO) {
            context.filesDir.listFiles()?.find { it.canRead() && it.isFile && it.name == FILE_NAME }?.let {
                context.openFileInput(FILE_NAME).use { stream ->
                    String(stream.readBytes(), Charsets.UTF_8)
                }
            } ?: ""
        }
    }

    suspend fun saveNodesToFile(json: String) {
        withContext(Dispatchers.IO) {
            try {
                context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { stream ->
                    with(OutputStreamWriter(stream)) {
                        write(json)
                        flush()
                        close()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val FILE_NAME = "nodes.file"
    }
}