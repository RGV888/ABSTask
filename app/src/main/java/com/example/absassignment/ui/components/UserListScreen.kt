package com.example.absassignment.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.absassignment.R
import com.example.absassignment.data.model.User
import com.example.absassignment.viewmodel.UserViewModel
import com.google.gson.Gson

@Composable
fun UserListScreen(navController: NavController, viewModel: UserViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.observeAsState(UserViewModel.UserUiState.Loading)


    Column {

        SearchBar(viewModel)

        LaunchedEffect(Unit) {
            viewModel.fetchUsers(20)
        }

        when (uiState) {
            is UserViewModel.UserUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is UserViewModel.UserUiState.Success -> {
                val users = (uiState as UserViewModel.UserUiState.Success).users
                LazyColumn {
                    items(users) { user ->
                        UserCard(user, navController)
                    }
                }
            }

            is UserViewModel.UserUiState.Error -> {
                val message = (uiState as UserViewModel.UserUiState.Error).message
                Text(
                    text = message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

    }
}

@Composable
fun SearchBar(viewModel: UserViewModel) {
    var userCount by remember { mutableStateOf("20") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userCount,
            label = { Text("Enter number of users") },
            onValueChange = {
                userCount = it
            },
            shape = RoundedCornerShape(12.dp)
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
//            viewModel.fetchUsers(userCount.toIntOrNull() ?: 20)
//            keyboardController?.hide()

            val count = userCount.toIntOrNull() // Try to parse the input
            if (count != null && count > 0) {
                // If valid number, fetch that number of users
                viewModel.fetchUsers(count)
                keyboardController?.hide()
            } else {
                // Invalid input, show an error message
              //  errorMessage = "Please enter a valid number"
                if (userCount.isEmpty()) {
                    // If input is empty, fetch the default 20 users
                    viewModel.fetchUsers(20)
                }
             }

        }) {
            Text(text = "Get Users")
        }
    }
}


@Composable
fun UserCard(user: User, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                val userJson = Gson().toJson(user)
                val encodedJson = Uri.encode(userJson)
                navController.navigate("userDetail/$encodedJson")
            }
            .fillMaxWidth()
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .defaultMinSize(minHeight = 100.dp)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            AsyncImage(
                model = user.picture.thumbnail,
                contentDescription = "User Profile Picture",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "${user.name.first} ${user.name.last}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFFFF5733)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "${user.location.street.name}, ${user.location.city}, ${user.location.country}",
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }
        }

    }


}
