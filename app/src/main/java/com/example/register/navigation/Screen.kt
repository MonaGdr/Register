package com.example.register.navigation

sealed class Screen( val route: String ){

    object signupScreen: Screen("signup_screen")
    object infoScreen: Screen("info_screen")

}
