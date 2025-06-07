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
    data class EditScheduleRoute(val scheduleId: Long? = null) : AppRoute()

    @Serializable
    object ScheduleLogRoute : AppRoute()

}