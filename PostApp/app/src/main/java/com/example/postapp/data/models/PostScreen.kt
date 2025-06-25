package com.example.postapp.data.models

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var editingPost by remember { mutableStateOf<Post?>(null) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerenciador de Posts") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
            TextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Conteúdo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
            Button(
                onClick = {
                    isLoading = true
                    viewModel.createPost(
                        title, content,
                        onSuccess = {
                            Toast.makeText(context, "Post criado com sucesso", Toast.LENGTH_SHORT).show()
                            isLoading = false
                            title = ""
                            content = ""
                        },
                        onError = {
                            Toast.makeText(context, "Erro ao criar Post!", Toast.LENGTH_SHORT).show()
                            isLoading = false
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Criar Post", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                LazyColumn {
                    items(viewModel.posts) { post ->
                        PostItem(
                            post = post,
                            onDelete = { viewModel.deletePost(it) },
                            onEdit = { editingPost = it }
                        )
                    }
                }
            }

            if (editingPost != null) {
                AlertDialog(
                    onDismissRequest = { editingPost = null },
                    title = { Text("Editar Post", color = MaterialTheme.colorScheme.primary) },
                    text = {
                        Column {
                            TextField(
                                value = editingPost!!.title,
                                onValueChange = { editingPost = editingPost!!.copy(title = it) },
                                label = { Text("Título") },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = editingPost!!.content,
                                onValueChange = { editingPost = editingPost!!.copy(content = it) },
                                label = { Text("Conteúdo") },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.updatePost(editingPost!!.id, editingPost!!.title, editingPost!!.content)
                                editingPost = null
                            }
                        ) {
                            Text("Salvar", color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { editingPost = null }) {
                            Text("Cancelar", color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                )
            }
        }
    }
}
