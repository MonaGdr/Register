package com.example.register.pages

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.register.datastore.StoreUserInfo
import com.example.register.model.Person

@Composable
fun infoScreen(navController: NavController, storeUserInfo: StoreUserInfo) {

    //get the info from data store
    val savedName = storeUserInfo.getName.collectAsState(initial = "")
    val savedFamily = storeUserInfo.getFamily.collectAsState(initial = "")
    val savedId = storeUserInfo.getId.collectAsState(initial = "")
    val savedDateOfBirth = storeUserInfo.getDateOfBirth.collectAsState(initial = "")

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),

        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            //title
            titleText(text = "User Information")

            //information table
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ){
                Divider(color = Color.Gray)
                tableRow(field = "Name", value = savedName.value)
                Divider(color = Color.Gray)

                tableRow(field = "Family", value = savedFamily.value )
                Divider(color = Color.Gray)

                tableRow(field = "Id", value = savedId.value)
                Divider(color = Color.Gray)

                tableRow(field = "Date of birth", value = savedDateOfBirth.value)
                Divider(color = Color.Gray)

            }

            //exit button
            exitButton(title = "Exit", navController, storeUserInfo )
        }
    }
}




@Preview
@Composable
fun infoScreenPreview(){

    val navController = rememberNavController()

    val context: Context = LocalContext.current
    val storeUserInfo = StoreUserInfo(context)

    infoScreen(navController, storeUserInfo)
}