/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.spikeysanju.wiggles.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.spikeysanju.wiggles.component.ItemDogCard
import dev.spikeysanju.wiggles.component.TopBar
import dev.spikeysanju.wiggles.data.FakeDogDatabase
import dev.spikeysanju.wiggles.model.Dog
import dev.spikeysanju.wiggles.ui.theme.MyTheme

@OptIn(ExperimentalAnimationApi::class)
@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewHome(){
    MyTheme() {
        Home(navController = rememberAnimatedNavController(), dogList = FakeDogDatabase.dogList) {

        }
    }
}

@Composable
fun Home(navController: NavHostController, dogList: List<Dog>, toggleTheme: () -> Unit) {
    LazyColumn {
        item {
            TopBar(
                onToggle = {
                    toggleTheme()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(dogList) {
            dogList.forEach {
                ItemDogCard(
                    it,
                    onItemClicked = { dog ->
                        navController.navigate("details/${dog.id}/${dog.name}/${dog.location}")
                    }
                )
            }
        }
    }
}
