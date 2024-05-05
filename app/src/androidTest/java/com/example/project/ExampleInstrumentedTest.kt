import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.project.R
import com.example.project.databases.AppDatabase
import com.example.project.databases.entities.ParkingE
import com.example.project.databases.entities.Reservation
import com.example.project.databases.entities.User
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var mDataBase: AppDatabase
    @Before
    fun initDB() {
        val appContext =
            InstrumentationRegistry.getInstrumentation().targetContext
        mDataBase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }
    @Test
    fun testInsertReservation() {
        val user1 = User(1000, "tuto1", "tuto2")
        val parking = ParkingE(
            nom="t",
            commune = "t",
            adresse = "r",
            prix = "r",
            dispo = "r",
            distance = "r",
            places = 100,
            img = R.drawable.p1
        )


        mDataBase.getUserDao().addUser(user1)
        val user = mDataBase.getUserDao().getUsersByID(1000)

        mDataBase.getParkingDao().addParking(parking)
        val list2 = mDataBase.getParkingDao().getParkingByName("t")

        val reservation = Reservation(id=10,userId=user.id, parkingId = list2.get(0).id,date= Date())

        mDataBase.getReservationDao().addReservation(reservation)
        val res = mDataBase.getReservationDao().getReservationById(reservation.id)

        assertEquals(reservation,res)
    }

    @Test
    fun testGetAllReservations() {
        val user1 = User(1000, "tuto1", "tuto2")
        val parking = ParkingE(
            nom="t",
            commune = "t",
            adresse = "r",
            prix = "r",
            dispo = "r",
            distance = "r",
            places = 100,
            img = R.drawable.p1
        )


        mDataBase.getUserDao().addUser(user1)
        val user = mDataBase.getUserDao().getUsersByID(1000)

        mDataBase.getParkingDao().addParking(parking)
        val list2 = mDataBase.getParkingDao().getParkingByName("t")

        val reservation1 = Reservation(id=10,userId=user.id, parkingId = list2.get(0).id,date= Date())
        val reservation2 = Reservation(id=11,userId=user.id, parkingId = list2.get(0).id,date= Date())
        mDataBase.getReservationDao().addReservation(reservation1)
        mDataBase.getReservationDao().addReservation(reservation2)
        val res = mDataBase.getReservationDao().getAllReservations()

        var reservations:MutableList<Reservation> = mutableListOf()
        reservations.add(reservation1)
        reservations.add(reservation2)

        assertEquals(reservations,res)
    }

    @Test
    fun testGetReservationByDate() {
        val user1 = User(1000, "tuto1", "tuto2")
        val parking = ParkingE(
            nom="t",
            commune = "t",
            adresse = "r",
            prix = "r",
            dispo = "r",
            distance = "r",
            places = 100,
            img = R.drawable.p1
        )


        mDataBase.getUserDao().addUser(user1)
        val user = mDataBase.getUserDao().getUsersByID(1000)

        mDataBase.getParkingDao().addParking(parking)
        val list2 = mDataBase.getParkingDao().getParkingByName("t")

        val date = Date()
        val reservation = Reservation(id=10,userId=user.id, parkingId = list2.get(0).id,date= date)

        mDataBase.getReservationDao().addReservation(reservation)
        val res = mDataBase.getReservationDao().getReservationByDate(reservation.date)

        assertEquals(reservation,res.get(0))
    }

    @After
    fun closeDb(){
        mDataBase?.close()
    }
}