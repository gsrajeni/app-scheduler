import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoute {
    @Serializable
    object SplashRoute : AppRoute()

    @Serializable
    object DashboardRoute : AppRoute()

    @Serializable
    object AddScheduleRoute : AppRoute()

    @Serializable
    object ScheduleLogRoute : AppRoute()

}