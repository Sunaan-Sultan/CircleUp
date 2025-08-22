//package com.project.example
//
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Modifier
//import androidx.fragment.app.FragmentActivity
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.project.example.ui.appbar.AppBar
//import com.project.example.ui.appbar.BottomNavigationBar
//import com.project.example.ui.appbar.Navigation
//import com.project.example.PreferencesManager
//import com.project.example.ui.theme.CircleUpTheme
//import com.project.example.ui.theme.rememberWindowSizeClass
//import kotlinx.coroutines.runBlocking
//
//class MainActivity : FragmentActivity() {
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val role = PreferencesManager(applicationContext)
//            .getKey("userRole", "member")
//
//        val mockLoader = MockLoader(this)
//        runBlocking { mockLoader.init() }
//
//        val prefs = PreferencesManager(applicationContext)
//        val userRole  = prefs.getKey("userRole", "member")
//        Log.d("MAIN", "Read userRole from prefs = $userRole")
//
//        val savedUser = prefs.getUsername("user", "")
//        val savedPass = prefs.getPassword("pass", "")
//        val startDestination = if (savedUser.isNotEmpty() && savedPass.isNotEmpty()) {
//            "home"
//        } else {
//            "login"
//        }
//        Log.d("MAIN", "startDestination = $startDestination (savedUser='${savedUser}', savedPass='${savedPass.isNotEmpty()}')")
//
//        setContent {
//            // inside setContent { ... } - Material3 version
//            val navController = rememberNavController()
//
//// track route reliably using a listener
//            val currentRouteState = rememberSaveable { mutableStateOf<String?>(null) }
//            DisposableEffect(navController) {
//                val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
//                    currentRouteState.value = destination.route
//                }
//                navController.addOnDestinationChangedListener(listener)
//                onDispose { navController.removeOnDestinationChangedListener(listener) }
//            }
//
//            val currentRoute = currentRouteState.value
//            val hideOn = listOf("login", "registration", "forgetPassword")
//            val shouldShowBars = currentRoute != null && currentRoute !in hideOn
//
//            val snackbarHostState = remember { SnackbarHostState() }
//            val window = rememberWindowSizeClass()
//
//            CircleUpTheme(window) {
//                Scaffold(
//                    topBar = {
//                        if (shouldShowBars) {
//                            AppBar(
//                                title = when (currentRoute) {
//                                    "login" -> "Log In"
//                                    "registration" -> "Registration"
//                                    "forgetPassword" -> "Forget Password"
//                                    "home" -> "Circle UP"
//                                    "profile" -> "Profile"
//                                    "myCart" -> "My Cart"
//                                    "bio" -> "Update Profile"
//                                    else -> "Circle UP"
//                                },
//                                showCartIcon = false,
//                                showBackButton = currentRoute == "myCart",
//                                navController = navController
//                            )
//                        }
//                    },
//                    bottomBar = {
//                        if (shouldShowBars) BottomNavigationBar(navController)
//                    },
//                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // correct paramless lambda
//                ) { innerPadding ->
//                    Navigation(
//                        navController = navController,
//                        innerPadding = innerPadding,
//                        startDestination = startDestination,
//                        userRole = userRole,
//                        preferencesManager = prefs,
//                        snackbarHostState = snackbarHostState // pass down
//                    )
//                }
//            }
//        }
//    }
//}

package com.project.example

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.example.ui.appbar.AppBar
import com.project.example.ui.appbar.BottomNavigationBar
import com.project.example.ui.appbar.Navigation
import com.project.example.ui.theme.CircleUpTheme
import com.project.example.ui.theme.rememberWindowSizeClass
import kotlinx.coroutines.runBlocking

class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mockLoader = MockLoader(this)
        runBlocking { mockLoader.init() }

        val prefs = PreferencesManager(applicationContext)
        val userRole = prefs.getKey("userRole", "member")
        Log.d("MAIN", "Read userRole from prefs = $userRole")

        val savedUser = prefs.getUsername("user", "")
        val savedPass = prefs.getPassword("pass", "")
        val startDestination = if (savedUser.isNotEmpty() && savedPass.isNotEmpty()) {
            "home"
        } else {
            "login"
        }
        Log.d("MAIN", "startDestination = $startDestination (savedUser='${savedUser}', savedPass='${savedPass.isNotEmpty()}')")

        setContent {
            val navController = rememberNavController()

            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route

            val hideOn = listOf("login", "registration", "forgetPassword")
            val shouldShowBars = currentRoute != null && currentRoute !in hideOn

            val window = rememberWindowSizeClass()

            CircleUpTheme(window) {
                if (shouldShowBars) {
                    Scaffold(
                        topBar = {
                            AppBar(
                                title = when (currentRoute) {
                                    "home" -> "Circle UP"
                                    "profile" -> "Profile"
                                    "products" -> "Products"
                                    "myCart" -> "My Cart"
                                    "bio" -> "Update Profile"
                                    else -> "Circle UP"
                                },
                                showCartIcon = false,
                                showBackButton = currentRoute == "myCart",
                                navController = navController
                            )
                        },
                        bottomBar = {
                            BottomNavigationBar(navController)
                        },

                    ) { innerPadding ->
                        Navigation(
                            navController = navController,
                            innerPadding = innerPadding,
                            startDestination = startDestination,
                            preferencesManager = prefs,
                        )
                    }
                } else {
                    Navigation(
                        navController = navController,
                        innerPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                        startDestination = startDestination,
                        preferencesManager = prefs,
                    )
                }
            }
        }
    }
}