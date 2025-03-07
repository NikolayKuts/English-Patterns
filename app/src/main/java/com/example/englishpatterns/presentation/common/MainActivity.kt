package com.example.englishpatterns.presentation.common

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.englishpatterns.data.ResourcesContentManager
import com.example.englishpatterns.data.TextAudioPlayer
import com.example.englishpatterns.data.TextSpeaker
import com.example.englishpatterns.data.patternStore
import com.example.englishpatterns.data.weekPatternStorage
import com.example.englishpatterns.data.yandexApi.YandexWordInfoProvider
import com.example.englishpatterns.presentation.collectWhenStarted
import com.example.englishpatterns.presentation.common.customTabs.ChatGptCustomTabManager
import com.example.englishpatterns.presentation.irregularVerbsPractice.IrregularVerbsPracticeEvent
import com.example.englishpatterns.presentation.irregularVerbsPractice.IrregularVerbsPracticeScreen
import com.example.englishpatterns.presentation.irregularVerbsPractice.IrregularVerbsPracticeViewModel
import com.example.englishpatterns.presentation.navigation.AppNavGraph
import com.example.englishpatterns.presentation.navigation.Screen
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingMviViewModel
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingEvent
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingScreen
import com.example.englishpatterns.presentation.patternPractisingScreen.PatternPracticingViewModel
import com.example.englishpatterns.presentation.webConten.WebContentScreen
import com.example.englishpatterns.presentation.webConten.WebContentState
import com.example.englishpatterns.ui.theme.EnglishPatternsTheme
import com.lib.lokdroid.core.logD
import com.lib.lokdroid.core.logW
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val viewModel: MviViewModel<MainState, MainAction, MainEvent> by viewModels<MainViewModel> {
        MainViewModelFactory(
            patternStorage = this.patternStore,
            weekPatternStorage = this.weekPatternStorage,
        )
    }

    private val localStorage by lazy { LocalStorage.getInstance(context = application) }
    private val chatGptCustomTabManager = ChatGptCustomTabManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        var textSpeaker: TextSpeaker? = null

        setContent {
            EnglishPatternsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = EnglishPatternsTheme.colors.surface
                ) {
                    Scaffold { paddingValues ->
                        val navController = rememberNavController()

                        LaunchedEffect(key1 = Unit) {
                            observeEventState(navController = navController)
                        }

                        AppNavGraph(
                            navHostController = navController,
                            mainScreenContent = {
                                MainScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = viewModel.uiState.collectAsState().value,
                                    sendAction = viewModel::sendAction
                                )
                            },
                            patternPracticingScreenContent = { rawPatternGroups ->
                                val patternPracticingViewModel: PatternPracticingMviViewModel =
                                    viewModel<PatternPracticingViewModel>(
                                        factory = PatternPracticingViewModel.Factory(
                                            patternGroupResources = rawPatternGroups,
                                            resourcesContentManager = ResourcesContentManager(
                                                context = application
                                            ),
                                            yandexWordInfoProvider = YandexWordInfoProvider(
                                                context = application
                                            ),
                                            weekPatternStorage = this.weekPatternStorage,
                                            textAudioPlayer = TextAudioPlayer()
                                        )
                                    )

                                LaunchedEffect(key1 = Unit) {
                                    textSpeaker = TextSpeaker(context = application)

                                    patternPracticingViewModel.eventState.collectLatest { event ->
                                        when (event) {
                                            is PatternPracticingEvent.WarmupCustomTabs -> {
                                                chatGptCustomTabManager.prepareCustomTab(
                                                    context = this@MainActivity,
                                                    url = event.url
                                                )
                                            }

                                            is PatternPracticingEvent.RedirectionToWordHuntAppRequired -> {
//                                                startActivityWithCheck(intent = event.intent)
                                                copyToClipboard(clipboardUnit = event.clipboardUnit)
                                                navController.navigateToWebContentScreen(url = event.url)
                                            }

                                            is PatternPracticingEvent.RedirectionToKlafAppRequired -> {
                                                startActivityWithCheck(intent = event.intent)
                                            }

                                            is PatternPracticingEvent.RedirectionToGhatGptAppRequired -> {
                                                copyToClipboard(clipboardUnit = event.enClipboardUnit)

                                                localStorage.apply {
                                                    enClipText = event.enClipboardUnit.text
                                                    ruClipText = event.ruClipboardUnit.text
                                                    selectedClipText =
                                                        event.selectedClipboardUnit.text
                                                }

                                                chatGptCustomTabManager.launchCustomTab(
                                                    url = event.url,
                                                    context = this@MainActivity
                                                )
                                            }

                                            is PatternPracticingEvent.RedirectionToYouGlishPageRequired -> {
//                                                startActivityWithCheck(intent = event.intent)
                                                navController.navigateToWebContentScreen(url = event.url)
                                            }

                                            is PatternPracticingEvent.RedirectionToGoogleImagesPageRequired -> {
                                                navController.navigateToWebContentScreen(url = event.url)
                                            }

                                            is PatternPracticingEvent.RedirectionToWordTemplateSearchPageRequired -> {
                                                navController.navigateToWebContentScreen(url = event.url)
                                            }

                                            is PatternPracticingEvent.TextToSpeech -> {
                                                textSpeaker?.speak(text = event.text)
                                            }

                                            PatternPracticingEvent.WeekPatternStored -> {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Week pattern stored",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }

                                val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

                                DisposableEffect(lifecycleOwner) {
                                    lifecycleOwner.lifecycle.addObserver(
                                        patternPracticingViewModel.textAudioPlayer
                                    )

                                    onDispose {
                                        lifecycleOwner.lifecycle.removeObserver(
                                            patternPracticingViewModel.textAudioPlayer
                                        )
                                        textSpeaker?.stop()
                                        textSpeaker = null
                                    }
                                }

                                PatternPracticingScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = patternPracticingViewModel.uiState.collectAsState().value,
                                    sendAction = patternPracticingViewModel::sendAction
                                )
                            },
                            webContentScreenContent = { url ->
                                WebContentScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = WebContentState(url = url)
                                )
                            },
                            irregularVerbsScreenContent = {
                                textSpeaker = TextSpeaker(context = application)
                                val irregularVerbsPracticeViewModel =
                                    viewModel<IrregularVerbsPracticeViewModel>()

                                LaunchedEffect(key1 = Unit) {
                                    irregularVerbsPracticeViewModel.eventState.collectLatest { event ->
                                        logD("event observed: $event")
                                        when (event) {
                                            is IrregularVerbsPracticeEvent.TextToSpeech -> {
                                                textSpeaker?.speak(text = event.text)
                                            }
                                        }
                                    }
                                }

                                val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

                                DisposableEffect(lifecycleOwner) {
                                    onDispose {
                                        textSpeaker?.stop()
                                        textSpeaker = null
                                    }
                                }


                                IrregularVerbsPracticeScreen(
                                    modifier = Modifier.padding(paddingValues),
                                    state = irregularVerbsPracticeViewModel.uiState.collectAsState().value,
                                    sendAction = irregularVerbsPracticeViewModel::sendAction
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun observeEventState(navController: NavController) {
        viewModel.eventState.collectWhenStarted(lifecycleOwner = this) { event ->
            logD("event observed: $event")

            when (event) {
                is MainEvent.PatternPracticingRequired -> {
                    val destination = Screen.PatternPracticingScreen(
                        patternGroupResources = event.patternGroupResources
                    )

                    navController.navigate(route = destination)
                }

                MainEvent.IrregularVerbsPracticeRequired -> {
                    navController.navigateToIrregularVerbsPracticeScreen()
                }
            }
        }
    }

    private fun startActivityWithCheck(intent: Intent) {
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            logW("Application not found")
        }
    }

    private fun NavController.navigateToWebContentScreen(url: String) {
        val destination = Screen.WebContentScreen(url = url)

        navigate(route = destination)
    }

    private fun NavController.navigateToIrregularVerbsPracticeScreen() {
        navigate(route = Screen.IrregularVerbs)
    }
}