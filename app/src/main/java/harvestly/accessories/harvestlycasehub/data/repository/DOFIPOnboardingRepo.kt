package harvestly.accessories.harvestlycasehub.data.repository

import harvestly.accessories.harvestlycasehub.data.datastore.DOFIPOnboardingPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DOFIPOnboardingRepo(
    private val dofipOnboardingStoreManager: DOFIPOnboardingPrefs,
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun observeOnboardingState(): Flow<Boolean?> {
        return dofipOnboardingStoreManager.onboardedStateFlow
    }

    suspend fun setOnboardingState(state: Boolean) {
        withContext(coroutineDispatcher) {
            dofipOnboardingStoreManager.setOnboardedState(state)
        }
    }
}