package plugins

fun configureRouting(routes: () -> Unit) {
    routes.invoke()
}
