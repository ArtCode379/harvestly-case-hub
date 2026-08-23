package harvestly.accessories.harvestlycasehub.di

import androidx.room.Room
import harvestly.accessories.harvestlycasehub.data.database.DOFIPDatabase
import org.koin.dsl.module

private const val DB_NAME = "dofip_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = DOFIPDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<DOFIPDatabase>().cartItemDao() }

    single { get<DOFIPDatabase>().orderDao() }
}