package harvestly.accessories.harvestlycasehub.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import harvestly.accessories.harvestlycasehub.data.dao.CartItemDao
import harvestly.accessories.harvestlycasehub.data.dao.OrderDao
import harvestly.accessories.harvestlycasehub.data.database.converter.Converters
import harvestly.accessories.harvestlycasehub.data.entity.CartItemEntity
import harvestly.accessories.harvestlycasehub.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DOFIPDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}