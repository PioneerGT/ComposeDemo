package com.tme.qqmusiccar.composedemo.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.content.AlbumListScene
import com.tme.qqmusiccar.composedemo.content.PlayerScene
import com.tme.qqmusiccar.composedemo.content.SongListScene
import com.tme.qqmusiccar.composedemo.ui.ComposeDemoTheme
import kotlinx.coroutines.delay

/**
 * Create by tinguo on 2024/4/2
 * Copyright (c) 2024 TME. All rights reserved.
 */
class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Home.route) {
                    composable(Routes.Home.route) { navBackStackEntry ->
                        val mainSceneViewModel = ViewModelProvider(this@MainActivity)[MainSceneViewModel::class.java]
                        MainScene(
                            mainSceneViewModel,
                            onNavigate = {route->
                                navController.navigate(route)
                            }
                        )
                    }
                    composable(Routes.Player.route) { navBackStackEntry ->
                        val mainSceneViewModel = ViewModelProvider(this@MainActivity)[MainSceneViewModel::class.java]
                        PlayerScene(mainSceneViewModel = mainSceneViewModel)
                    }
                    composable(Routes.Detail.route) { navBackStackEntry ->
                        Column {
                            Text(text = "DETAIL")
                            Button(onClick = {
                                navController.navigateUp()
                            }) {
                                Text(text = "返回MainScene")
                            }
                        }
                    }
                    composable(Routes.SongList.route) { navBackStackEntry->
                        val mainSceneViewModel = ViewModelProvider(this@MainActivity)[MainSceneViewModel::class.java]
                        SongListScene {
                            mainSceneViewModel.switchSong(it)
                            navController.navigate(Routes.Player.route)
                        }
                    }
                    composable(Routes.AlbumList.route) { navBackStackEntry ->
                        AlbumListScene {
                            navController.navigate(Routes.Detail.route)
                        }
                    }
                }
            }
        }
    }

    sealed class Routes(val route: String, val name: String) {
        data object Home: Routes("home", "首页", )
        data object Detail: Routes("detail", "详情页")
        data object Player: Routes("player", "大图模糊")
        data object SongList: Routes("song_list", "歌曲列表")
        data object AlbumList: Routes("album_list", "专辑列表")
    }

    @Composable
    fun MainScene(
        mainSceneViewModel: MainSceneViewModel,
        onNavigate: (String) -> Unit
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val transitionState = remember {
                MutableTransitionState(mainSceneViewModel.showSplash.value)
            }
            val transition = updateTransition(
                targetState = transitionState,
                label = "splashTransition"
            )
            val splashAlpha by transition.animateFloat(
                transitionSpec = { tween(durationMillis = 100) }, label = "splashAlpha"
            ) {
                Log.v("MainActivity", "splashAlpha: ${it.currentState}")
                if (it.targetState) 1f else 0f
            }

            val contentAlpha by transition.animateFloat(
                transitionSpec = { tween(durationMillis = 300) }, label = "contentAlpha"
            ) {
                Log.v("MainActivity", "contentAlpha: ${it.currentState}")
                if (it.targetState) 0f else 1f
            }

            Box {
                SplashScreen(
                    modifier = Modifier.alpha(splashAlpha),
                    onTimeout = {
                        Log.i("MainActivity", "splash onTimeout")
                        transitionState.targetState = false
                        mainSceneViewModel.splashFinished()
                    }
                )

                MainScreen(
                    modifier = Modifier.alpha(contentAlpha),
                    onNavigate = onNavigate
                )
            }
        }
    }

    @Composable
    private fun SplashScreen(
        modifier: Modifier = Modifier,
        onTimeout: () -> Unit
    ) {
        val currentOnTimeout by rememberUpdatedState(newValue = onTimeout)

        LaunchedEffect(key1 = Unit) {
            delay(2000L)
            currentOnTimeout()
        }

        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

            Text(text = "Hello GT", modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally), fontSize = 20.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
            )
        }
    }

    @Composable
    private fun MainScreen(
        modifier: Modifier = Modifier,
        onNavigate: (String) -> Unit
    ) {
        val routes = listOf(
            Routes.Detail,
            Routes.Player,
            Routes.SongList,
            Routes.AlbumList
        )

        Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "MainScreen", modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally), fontSize = 20.sp)

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(routes) {item->
                    Button(
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            onNavigate("${item.route}")
                        }) {
                            Text(text = "跳转${item.name}", modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                            )
                        }
                }
            }
        }
    }

}