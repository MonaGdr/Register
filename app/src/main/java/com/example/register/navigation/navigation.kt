package com.example.register.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.register.datastore.StoreUserInfo
import com.example.register.pages.infoScreen
import com.example.register.pages.signUpScreen

@Composable
fun navigation() {

    //context
    val context:Context = LocalContext.current

    //storeUserInfo
    val storeUserInfo = StoreUserInfo(context = context)

    //check if the user has already signed up
    val savedName = storeUserInfo.getName.collectAsState(initial = "")
    val savedFamily = storeUserInfo.getFamily.collectAsState(initial = "")
    val savedId = storeUserInfo.getId.collectAsState(initial = "")
    val savedDateOfBirth = storeUserInfo.getDateOfBirth.collectAsState(initial = "")

    val startDest = if ( savedName.value != "" && savedFamily.value != "" &&
        savedId.value != "" && savedDateOfBirth.value != ""
    )
        //lunch to info screen
        Screen.infoScreen.route
    else
        //lunch to sign up screen
        Screen.signupScreen.route


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDest ){

        //sign up screen
        composable(route = Screen.signupScreen.route){ signUpScreen(navController = navController, storeUserInfo) }

        //info screen
        composable(route = Screen.infoScreen.route) {infoScreen(navController = navController, storeUserInfo = storeUserInfo) }
    }
}