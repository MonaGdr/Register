package com.example.register.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.register.MainActivity
import com.example.register.datastore.StoreUserInfo
import com.example.register.model.Person
import com.example.register.model.Visible


@Composable
fun signUpScreen( navController: NavController, storeUserInfo: StoreUserInfo?){


    var personState = remember {
        mutableStateOf(Person())
    }

    //for warnings
    val visibleState = remember{
        mutableStateOf(Visible())
    }
    

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),


    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            //title
            titleText(text = "Register")

            //fields the user needs to fill

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //name field
                if(visibleState.value.nameWarning)
                    warningText(field = "name")
                editText(hint = "name", personState){ person, newValue -> person.copy(name = newValue) }


                //family field
                if(visibleState.value.familyWarning)
                    warningText(field = "family")
                editText(hint = "family", personState ){ person, newValue -> person.copy(family = newValue) }

                //id field
                if(visibleState.value.idWarning)
                    warningText(field = "id")
                editText(hint = "id", personState ){ person, newValue -> person.copy(id = newValue) }

                //date of birth field
                if(visibleState.value.dateOfBirthWarning)
                    warningText(field = "date of birth")
                editText(hint = "date of birth",  personState  ){ person, newValue -> person.copy(dateOfBirth = newValue) }

            }

            //register button
            if (storeUserInfo != null) {
                registerButton(title = "register", personState, visibleState, navController, storeUserInfo){
                        visible -> visible.copy(nameWarning = personState.value.name == "",
                    familyWarning = personState.value.family == "" ,
                    idWarning = personState.value.id == "" || !isIdValid(personState.value.id),
                    dateOfBirthWarning = personState.value.dateOfBirth == "" || !isDateOfBirthValid(personState.value.dateOfBirth)
                )}

            }

        }
    }
}

@Preview
@Composable
fun signUpScreenPreview(){

    val navController = rememberNavController()
    signUpScreen(navController, null)
}

