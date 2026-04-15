package app.mamafua.ui.components

enum class CoffeeFlavorDrawable(val drawableRes: Int, private val flavorName: String) {
    CITRIC(app.mamafua.ui.R.drawable.citric, "citric"),
    CHOCOLATE(app.mamafua.ui.R.drawable.chocolate, "chocolate"),
    NUTTY(app.mamafua.ui.R.drawable.nutty, "nutty"),
    FLORAL(app.mamafua.ui.R.drawable.floral, "floral"),
    SWEET(app.mamafua.ui.R.drawable.sweet, "sweet"),
    SPICES(app.mamafua.ui.R.drawable.spices, "spices"),
    FRUITY(app.mamafua.ui.R.drawable.fruity, "fruity");

    companion object {
        fun fromName(name: String) = entries.firstOrNull { it.flavorName.equals(name, true) }
    }
}
