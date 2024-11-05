package com.example.absassignment.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.absassignment.R
import com.example.absassignment.data.model.User
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun UserDetailScreen(userInfo: String?) {


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val decodedJson = userInfo?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) }
        val user = decodedJson?.let { Gson().fromJson(it, User::class.java) }


        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.detailsScreenHeader))
                    .defaultMinSize(200.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = user?.picture?.large,
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .aspectRatio(1f),
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    error = painterResource(id = R.drawable.ic_launcher_background),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${user?.name?.first} ${user?.name?.last}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

           // Spacer(modifier = Modifier.height(16.dp))




            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)

            ) {
                // Contact Information
                Text(
                    text = "Contact Information",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Email:", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Phone:", style = MaterialTheme.typography.bodyMedium)
                    }

                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (user != null) {
                            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
                            Text(text = user.phone, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //User Details
                Text(
                    text = "User Details",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {


                        Text(text = "Name:", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Gender:", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Age:", style = MaterialTheme.typography.bodyMedium)
                    }

                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (user != null) {
                            Text(
                                text = "${user.name.first} ${user.name.last}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(text = user.gender, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = "${user.dob.age} years old",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

