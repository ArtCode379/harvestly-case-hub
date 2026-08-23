package harvestly.accessories.harvestlycasehub.di

import harvestly.accessories.harvestlycasehub.ui.viewmodel.AppViewModel
import harvestly.accessories.harvestlycasehub.ui.viewmodel.CartViewModel
import harvestly.accessories.harvestlycasehub.ui.viewmodel.CheckoutViewModel
import harvestly.accessories.harvestlycasehub.ui.viewmodel.DOFIPOnboardingVM
import harvestly.accessories.harvestlycasehub.ui.viewmodel.OrderViewModel
import harvestly.accessories.harvestlycasehub.ui.viewmodel.ProductDetailsViewModel
import harvestly.accessories.harvestlycasehub.ui.viewmodel.ProductViewModel
import harvestly.accessories.harvestlycasehub.ui.viewmodel.DOFIPSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        DOFIPSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        DOFIPOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}