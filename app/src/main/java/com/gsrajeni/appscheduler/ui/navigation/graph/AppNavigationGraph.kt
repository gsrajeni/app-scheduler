import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gsrajeni.appscheduler.ui.navigation.LocalNavHostController
import com.gsrajeni.appscheduler.ui.screens.add_schedule.AddScheduleScreen
import com.gsrajeni.appscheduler.ui.screens.dashboard.DashboardScreen
import com.gsrajeni.appscheduler.ui.screens.schedule_logs.ScheduleLogsScreen
import com.gsrajeni.appscheduler.ui.screens.splash.SplashScreen



@Composable
fun AppNavigationGraph() {
    val navController = LocalNavHostController.current!!
    NavHost(
        navController = navController,
        startDestination = AppRoute.SplashRoute,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) }
    ) {
        navigationGraph()
    }
}

fun NavGraphBuilder.navigationGraph() {
    composable<AppRoute.SplashRoute> {
        SplashScreen()
    }
    composable<AppRoute.DashboardRoute> {
        DashboardScreen()
    }
    composable<AppRoute.AddScheduleRoute> {
        AddScheduleScreen()
    }
    composable<AppRoute.ScheduleLogRoute> {
        ScheduleLogsScreen()
    }
}