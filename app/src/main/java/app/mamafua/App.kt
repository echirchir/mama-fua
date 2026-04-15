package app.mamafua

import android.app.Application
import app.mamafua.di.DataModule
import app.mamafua.di.DatabaseModule
import app.mamafua.di.DomainModule
import app.mamafua.di.PreferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            printLogger()
            androidContext(this@App)
            modules(
                DataModule,
                DomainModule,
                DatabaseModule,
                PreferencesModule
            )
        }
    }
}
