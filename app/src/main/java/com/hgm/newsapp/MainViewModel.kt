package com.hgm.newsapp

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgm.newsapp.core.navigation.Route
import com.hgm.newsapp.domain.use_case.AppEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @author：HGM
 * @created：2024/1/29 0029
 * @description：
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
      private val appEntryUseCase: AppEntryUseCase
) : ViewModel() {

      var splashCondition by mutableStateOf(true)
            private set

      var startDestination by mutableStateOf(Route.AppStartNavigation.route)
            private set

      init {
            appEntryUseCase.readAppEntry().onEach { shouldNavToHomeScreen ->
                  startDestination = if (shouldNavToHomeScreen) {
                        Route.NewsNavigation.route
                  } else {
                        Route.AppStartNavigation.route
                  }
                  delay(300)
                  splashCondition = false
            }.launchIn(viewModelScope)
      }

}