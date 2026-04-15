package app.mamafua.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val MAMA_FUA_PREFS = "MAMA_FUA_PREFS"

val DataModule = module {
    // Data layer dependencies will be added here
}

val DomainModule = module {
    // Domain layer dependencies (use cases) will be added here
}

val DatabaseModule = module {
    // Database dependencies will be added here
}

val PreferencesModule = module {
    single<SharedPreferences> { androidContext().getSharedPreferences(MAMA_FUA_PREFS, Context.MODE_PRIVATE) }
}
