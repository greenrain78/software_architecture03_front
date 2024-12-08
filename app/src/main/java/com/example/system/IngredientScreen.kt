package com.example.system

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ui.viewmodel.IngredientViewModel
import java.time.LocalDate
import java.time.ZoneId

fun fromLocalDate(date: LocalDate): Long {
    return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun init(ingredientViewModel: IngredientViewModel) {
    //ingredientViewModel.deleteAllIngredients()
    ingredientViewModel.updateIngredientUiState(
        Ingredient(
            id = 1,
            name = "pickle1",
            quantity = 1,
            expirationDate = fromLocalDate(LocalDate.of(2024, 11, 16)),
            url = "url"
        )
    )
    ingredientViewModel.insertIngredient()

    ingredientViewModel.updateIngredientUiState(
        Ingredient(
            id = 2,
            name = "apple2",
            quantity = 3,
            expirationDate = fromLocalDate(LocalDate.of(2024, 11, 22)),
            url = "url"
        )
    )
    ingredientViewModel.insertIngredient()

    ingredientViewModel.updateIngredientUiState(
        Ingredient(
            id = 3,
            name = "pickle3",
            quantity = 3,
            expirationDate = fromLocalDate(LocalDate.of(2024, 11, 26)),
            url = "url"
        )
    )
    ingredientViewModel.insertIngredient()

    ingredientViewModel.updateIngredientUiState(
        Ingredient(
            id = 4,
            name = "apple4",
            quantity = 3,
            expirationDate = fromLocalDate(LocalDate.of(2024, 11, 30)),
            url = "url"
        )
    )
    ingredientViewModel.insertIngredient()

    ingredientViewModel.getIngredientList()
    ingredientViewModel.updateExpiredDateUiState(fromLocalDate(LocalDate.of(2024, 11, 17)))
    ingredientViewModel.updateWarningDateUiState(fromLocalDate(LocalDate.of(2024, 11, 17)),
        fromLocalDate(LocalDate.of(2024, 11, 24)))
    ingredientViewModel.getExpiredIngredientList()
    ingredientViewModel.getWarningIngredientList()
}

@Composable
fun IngredientScreen(
    context : Context,
    ingredientViewModel: IngredientViewModel = IngredientViewModel(context)
) {
    init(ingredientViewModel)

    val ingredientList : List<Ingredient> = ingredientViewModel.ingredientList.collectAsStateWithLifecycle().value
    val expiredIngredientList : List<Ingredient> = ingredientViewModel.expiredIngredientList.collectAsStateWithLifecycle().value
    val warningIngredientList : List<Ingredient> = ingredientViewModel.warningIngredientList.collectAsStateWithLifecycle().value

    Row(
        modifier = Modifier.padding(horizontal = 50.dp)
    ) {
        Column() {
            Text(text = "모든 식재료")
            LazyColumn(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(10.dp)
                    .background(color = Color.LightGray)
            ) {
                items(ingredientList) { ingredient ->
                    Text(
                        text = ingredient.name,
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White),
                        fontSize = 30.sp
                    )
                }
            }
        }

        Column() {
            Text(text = "유통 기한이 지난 식재료")
            LazyColumn(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(10.dp)
                    .background(color = Color.LightGray)
            ) {
                items(expiredIngredientList) { expiredIngredient ->
                    Text(
                        text = expiredIngredient.name,
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White),
                        fontSize = 30.sp
                    )
                }
            }
        }

        Column() {
            Text(text = "유통 기한이 얼마 안 남은 식재료")
            LazyColumn(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(10.dp)
                    .background(color = Color.LightGray)
            ) {
                items(warningIngredientList) { warningIngredient ->
                    Text(
                        text = warningIngredient.name,
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White),
                        fontSize = 30.sp
                    )
                }
            }
        }
    }


}

@Composable
fun IngredientInsertScreen(
    ingredientViewModel: IngredientViewModel
) {

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ingredient Form")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = expirationDate,
            onValueChange = { expirationDate = it },
            label = { Text("Expiration Date (yyyy-MM-dd)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val ingredient = Ingredient(
                    name = name,
                    quantity = quantity.toInt(),
                    expirationDate = fromLocalDate(LocalDate.parse(expirationDate)),
                    url = url
                )

                ingredientViewModel.updateIngredientUiState(ingredient)
                ingredientViewModel.insertIngredient()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}