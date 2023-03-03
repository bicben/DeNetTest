@file:OptIn(ExperimentalFoundationApi::class)

package com.example.denettest.ui

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.denettest.presentation.MainViewModel
import com.example.denettest.ui.theme.DeNetTestTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val currentNode by viewModel.currentNode.collectAsState()

    currentNode?.let { node ->

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(18.dp)
        ) {
            node.parent?.let { parent ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                    Text(text = "Parent Node   ", fontSize = 20.sp, textAlign = TextAlign.Start)
                    NodeItem(name = parent.name, Modifier.clickable { viewModel.selectNode(parent) })
                }
            }

            Divider(thickness = 16.dp)

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                Text(text = "Current Node  ", fontSize = 20.sp, textAlign = TextAlign.Start)
                NodeItem(name = node.name, modifier = Modifier.background(Color.Green))
            }

            Divider(thickness = 16.dp)

            Text(text = "Children Nodes", fontSize = 20.sp, textAlign = TextAlign.Center)

            LazyRow(
                modifier = modifier.fillMaxWidth(). padding(vertical = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(viewModel.currentChildren) { child ->
                    NodeItem(name = child.name,
                        modifier = Modifier
                            .combinedClickable(
                                onClick = { viewModel.selectNode(child) },
                                onLongClick = { viewModel.deleteNode(child) }
                            ))
                }
                item {
                    AddNodeItem(modifier = Modifier.clickable { viewModel.addChild(node) })
                }
            }
        }
    }
}

@Composable
fun NodeItem(name: Int, modifier: Modifier) {
    Card(
        modifier = modifier
            .height(72.dp)
            .width(72.dp)
            .padding(1.dp)
            .aspectRatio(1f),
        elevation = 2.dp
    ) {
        Log.d("NodeTest", "NodeItem created with name: $name")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
        ) {
            Text(text = "NODE", fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.DarkGray)
            Text(text = name.toString(), color = Color.Black)
        }
    }
}

@Composable
fun AddNodeItem(modifier: Modifier) {
    Card(
        modifier = modifier
            .height(72.dp)
            .width(72.dp)
            .padding(1.dp)
            .aspectRatio(1f),
        shape = CircleShape,
        elevation = 4.dp
    ) {
        Image(
            imageVector = Icons.Filled.Add, contentDescription = "Add node",
            modifier = Modifier
                .background(color = Color.White)
                .border(width = 3.dp, color = Color.Cyan, shape = CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeNetTestTheme {
//        NodeItem(name = 123456, Modifier)
//        AddNodeItem(Modifier)
    }
}