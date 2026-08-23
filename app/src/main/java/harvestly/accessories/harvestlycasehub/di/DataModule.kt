package harvestly.accessories.harvestlycasehub.di

import harvestly.accessories.harvestlycasehub.data.repository.CartRepository
import harvestly.accessories.harvestlycasehub.data.repository.DOFIPOnboardingRepo
import harvestly.accessories.harvestlycasehub.data.repository.OrderRepository
import harvestly.accessories.harvestlycasehub.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        DOFIPOnboardingRepo(
            dofipOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}