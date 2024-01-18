package com.example.register.pages


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.register.datastore.StoreUserInfo
import com.example.register.model.Person
import com.example.register.model.Visible
import com.example.register.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun titleText(text:String){

    Text(
        text = text,
        style = TextStyle(
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun warningText(field: String){

    Text(
        text = "*You need to enter a valid $field in this field.",
        color = Color.Red,
        modifier = Modifier.padding(8.dp)

    )
}

@Composable
fun editText(hint:String, personState:MutableState<Person>, update: (Person, String) -> Person) {

    val textState = remember {
        mutableStateOf("")
    }


   TextField(
       value = textState.value,
       onValueChange = {
           textState.value = it
           personState.value = update(personState.value, it)
       },
       label = { Text(text = hint)},
       modifier = Modifier
           .padding(8.dp)
           .fillMaxWidth()
   )

}

@Composable
fun registerButton (title:String, personState:MutableState<Person>,
                    visibleState: MutableState<Visible>, navController: NavController, storeUserInfo: StoreUserInfo,
                    update: (Visible) -> Visible, ){

    //coroutine Scope to edit data store
    val scope = rememberCoroutineScope()

    Button(
        onClick = {


            if (personState.value.name != "" && personState.value.family != "" &&
                personState.value.id != "" && personState.value.dateOfBirth != "" &&
                isIdValid(personState.value.id) && isDateOfBirthValid(personState.value.dateOfBirth)){

                    //save the user info
                    scope.launch {
                        storeUserInfo.saveName(personState.value.name)
                        storeUserInfo.saveFamily(personState.value.family)
                        storeUserInfo.saveId(personState.value.id)
                        storeUserInfo.saveDateOfBirth(personState.value.dateOfBirth)
                    }

                    //navigate to infoScreen
                    navController.navigate(Screen.infoScreen.route)


            }
            else{

                //show warning for missing or invalid fields
                visibleState.value = update(visibleState.value)

            }


        },
        modifier = Modifier.padding(8.dp)

    ) {
        Text(text = title)
    }
}

@Composable
fun tableRow( field:String, value:String ){

    Row(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = field,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
fun exitButton (title: String, navController: NavController, storeUserInfo: StoreUserInfo){

    //coroutine Scope to edit data store
    val scope = rememberCoroutineScope()

    Button(
        onClick = {

            //delete the user info
            scope.launch {
                storeUserInfo.deleteName()
                storeUserInfo.deleteFamily()
                storeUserInfo.deleteId()
                storeUserInfo.deleteDateOfBirth()
            }

            //navigate to sign Up Screen
            navController.navigate(Screen.signupScreen.route)
        },
        modifier = Modifier.padding(8.dp)
    ) {
        
        Text(text = title)
        
    }
}


fun isIdValid(id: String): Boolean {
    return id.length == 10 && id.all { it.isDigit() }
}

fun isDateOfBirthValid(date: String): Boolean {

    val separators = listOf('.', '/', '-')

    if (date.length !in 8..10 || date.count { it in separators } != 2) return false

    val separator = separators.firstOrNull { it in date } ?: return false

    val parts = date.split(separator)
    if (parts.size != 3) return false

    val year = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val day = parts[2].toIntOrNull() ?: return false

    if (day !in 1..31 || month !in 1..12 || year !in 1900..2024) return false

    return true
}
