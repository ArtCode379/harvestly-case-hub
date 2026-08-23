package harvestly.accessories.harvestlycasehub.di

import harvestly.accessories.harvestlycasehub.data.datastore.DOFIPOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { DOFIPOnboardingPrefs(androidContext()) }
}