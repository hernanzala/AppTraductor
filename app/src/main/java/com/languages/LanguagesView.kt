package com.languages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.apptraductor.R
import kotlinx.coroutines.launch

@Composable
fun LanguagesView() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = LanguagesStore(context)

    val indexLang by dataStore.getIndexLang.collectAsState(initial = 0)

    val items = listOf("English", "Spanish")
    val languages = getStrings()
    var currenyLanguages by remember { mutableStateOf(languages[indexLang]) }

    LaunchedEffect(indexLang) {
        currenyLanguages = languages[indexLang]
    }

    var expanded by remember { mutableStateOf(false) }
    var selectIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = items[selectIndex])
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectIndex = index
                            currenyLanguages = languages[index]
                            scope.launch {
                                dataStore.saveIndexLang(index)
                            }
                            expanded = false
                        })
                }
            }
        }
        Text(text = currenyLanguages["title"].toString(), fontWeight = FontWeight.Bold)
        Text(text = currenyLanguages["subtitle"].toString())
    }


}