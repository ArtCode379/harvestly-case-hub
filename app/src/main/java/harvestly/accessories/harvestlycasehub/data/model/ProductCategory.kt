package harvestly.accessories.harvestlycasehub.data.model

import androidx.annotation.StringRes
import harvestly.accessories.harvestlycasehub.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    CASES(R.string.dofip_category_cases),
    CHARGING(R.string.dofip_category_charging),
    AUDIO(R.string.dofip_category_audio),
    PROTECTION(R.string.dofip_category_protection),
    ESSENTIALS(R.string.dofip_category_essentials)
}
