package com.gramaangana.app

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min

private val DeepForest = Color(0xFF2D5A27)
private val Sage = Color(0xFF456B3D)
private val Terracotta = Color(0xFFB24721)
private val Bone = Color(0xFFFCF9F8)
private val SurfaceWhite = Color.White
private val TextDark = Color(0xFF1A1A1A)
private val TextSecondary = Color(0xFF6B6B6B)
private val FreeGreen = Color(0xFF4CAF50)
private val BookedRed = Color(0xFFF44336)
private val CardShape = RoundedCornerShape(8.dp)
private val TimeSlots = listOf("08:00-10:00", "10:00-12:00", "12:00-14:00", "14:00-16:00", "16:00-18:00", "18:00-20:00")
private val Purposes = listOf("Wedding Function", "Religious Ceremony", "Community Meeting", "Sports Event", "Cultural Program", "Other")

class GramaAnganaApp : Application() {
    val database by lazy { AppDatabase.get(this) }
    val session by lazy { SessionManager(this) }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannels(this)
        WorkManager.getInstance(this)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GramaAnganaRoot() }
    }
}

@Entity(indices = [Index(value = ["mobileNumber"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val mobileNumber: String,
    val passwordHash: String,
    val role: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("userId"), Index(value = ["date", "timeSlot"], unique = true)]
)
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val bookerName: String,
    val bookerPhone: String,
    val date: String,
    val timeSlot: String,
    val purpose: String,
    val notes: String,
    val status: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    @DrawableRes val bannerImageRes: Int,
    val isPublic: Boolean = true
)

@Entity
data class MaintenanceProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val goalAmount: Float,
    val collectedAmount: Float,
    val isGoalReached: Boolean = false
)

@Entity
data class VillageAlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val status: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(indices = [Index("userId"), Index("projectId")])
data class UserSupportEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val projectId: Int,
    val amountSupported: Float,
    val supportedAt: Long = System.currentTimeMillis()
)

data class SupportedProject(
    val id: Int,
    val name: String,
    val description: String,
    val goalAmount: Float,
    val collectedAmount: Float,
    val isGoalReached: Boolean,
    val amountSupported: Float
)

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity WHERE mobileNumber = :mobile LIMIT 1")
    suspend fun getUserByMobile(mobile: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT COUNT(*) FROM UserEntity")
    suspend fun count(): Int
}

@Dao
interface BookingDao {
    @Query("SELECT * FROM BookingEntity WHERE date = :date ORDER BY timeSlot")
    fun getBookingsForDate(date: String): Flow<List<BookingEntity>>

    @Query("SELECT * FROM BookingEntity WHERE date = :date ORDER BY timeSlot")
    suspend fun getBookingsForDateOnce(date: String): List<BookingEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("UPDATE BookingEntity SET status = :status WHERE id = :id")
    suspend fun updateBookingStatus(id: Int, status: String)

    @Query("SELECT * FROM BookingEntity WHERE userId = :userId ORDER BY date DESC, timeSlot DESC")
    fun getBookingsByUser(userId: Int): Flow<List<BookingEntity>>

    @Query("SELECT * FROM BookingEntity ORDER BY date DESC, timeSlot DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM BookingEntity WHERE date = :date AND timeSlot = :timeSlot)")
    suspend fun isSlotTaken(date: String, timeSlot: String): Boolean
}

@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntity WHERE isPublic = 1 ORDER BY date, time")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM EventEntity WHERE (date || ' ' || time) >= :now AND isPublic = 1 ORDER BY date, time LIMIT 1")
    fun getNextEvent(now: String): Flow<EventEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity): Long

    @Query("SELECT COUNT(*) FROM EventEntity")
    suspend fun count(): Int
}

@Dao
interface MaintenanceDao {
    @Query("SELECT * FROM MaintenanceProjectEntity ORDER BY isGoalReached, id")
    fun getAllProjects(): Flow<List<MaintenanceProjectEntity>>

    @Query("SELECT * FROM MaintenanceProjectEntity WHERE id = :id")
    suspend fun getProjectById(id: Int): MaintenanceProjectEntity?

    @Query("UPDATE MaintenanceProjectEntity SET collectedAmount = :amount, isGoalReached = :reached WHERE id = :id")
    suspend fun updateCollected(id: Int, amount: Float, reached: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: MaintenanceProjectEntity): Long

    @Query("SELECT COUNT(*) FROM MaintenanceProjectEntity")
    suspend fun count(): Int
}

@Dao
interface AlertDao {
    @Query("SELECT * FROM VillageAlertEntity ORDER BY createdAt DESC")
    fun getAllAlerts(): Flow<List<VillageAlertEntity>>

    @Query("UPDATE VillageAlertEntity SET status = :status WHERE id = :id")
    suspend fun updateAlertStatus(id: Int, status: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: VillageAlertEntity): Long

    @Query("SELECT COUNT(*) FROM VillageAlertEntity")
    suspend fun count(): Int
}

@Dao
interface UserSupportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupport(support: UserSupportEntity): Long

    @Query(
        "SELECT p.id, p.name, p.description, p.goalAmount, p.collectedAmount, p.isGoalReached, SUM(s.amountSupported) AS amountSupported " +
            "FROM UserSupportEntity s INNER JOIN MaintenanceProjectEntity p ON p.id = s.projectId WHERE s.userId = :userId GROUP BY p.id"
    )
    fun getSupportedProjectsByUser(userId: Int): Flow<List<SupportedProject>>
}

@Database(
    entities = [UserEntity::class, BookingEntity::class, EventEntity::class, MaintenanceProjectEntity::class, VillageAlertEntity::class, UserSupportEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookingDao(): BookingDao
    abstract fun eventDao(): EventDao
    abstract fun maintenanceDao(): MaintenanceDao
    abstract fun alertDao(): AlertDao
    abstract fun userSupportDao(): UserSupportDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "grama_angana.db").build().also { instance = it }
        }
    }
}

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("grama_session", Context.MODE_PRIVATE)
    val isLoggedIn get() = prefs.getBoolean("isLoggedIn", false)
    val userMobile get() = prefs.getString("userMobile", "") ?: ""
    val userName get() = prefs.getString("userName", "") ?: ""
    val userId get() = prefs.getInt("userId", -1)
    val isGuest get() = prefs.getBoolean("isGuest", false)
    val isAdmin get() = prefs.getBoolean("isAdmin", false)
    var notificationsEnabled: Boolean
        get() = prefs.getBoolean("notificationsEnabled", true)
        set(value) = prefs.edit().putBoolean("notificationsEnabled", value).apply()

    fun saveUser(user: UserEntity) {
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putBoolean("isGuest", false)
            .putBoolean("isAdmin", user.role == "ADMIN")
            .putInt("userId", user.id)
            .putString("userMobile", user.mobileNumber)
            .putString("userName", user.fullName)
            .apply()
    }

    fun saveGuest() {
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putBoolean("isGuest", true)
            .putBoolean("isAdmin", false)
            .putInt("userId", -1)
            .putString("userMobile", "")
            .putString("userName", "Guest")
            .apply()
    }

    fun clear() {
        val notify = notificationsEnabled
        prefs.edit().clear().putBoolean("notificationsEnabled", notify).apply()
    }
}

object PasswordUtils {
    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

object FirebaseAuthMapper {
    fun emailForMobile(mobile: String): String = "$mobile@gramaangana.app"
}

object DateUtils {
    private val displayDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("en", "IN"))
    private val displayEvent = DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale("en", "IN"))
    fun db(date: LocalDate): String = date.toString()
    fun display(date: String): String = LocalDate.parse(date).format(displayDate)
    fun event(date: String, time: String): String {
        val suffix = LocalTime.parse(time).format(DateTimeFormatter.ofPattern("h:mm a", Locale("en", "IN")))
        return "${LocalDate.parse(date).format(displayEvent)} · $suffix"
    }
    fun slotLabel(slot: String): String {
        val (start, end) = slot.split("-").map { LocalTime.parse(it).format(DateTimeFormatter.ofPattern("h a", Locale("en", "IN"))) }
        return "$start-$end"
    }
    fun currentSlot(): String? {
        val now = LocalTime.now()
        return TimeSlots.firstOrNull {
            val (s, e) = it.split("-").map(LocalTime::parse)
            !now.isBefore(s) && now.isBefore(e)
        }
    }
}

object NotificationHelper {
    const val BOOKING_CHANNEL = "booking_updates"
    const val EVENT_CHANNEL = "event_reminders"
    const val MAINTENANCE_CHANNEL = "maintenance_alerts"
    const val PROJECT_CHANNEL = "project_alerts"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(NotificationManager::class.java)
        listOf(
            NotificationChannel(BOOKING_CHANNEL, "Booking Updates", NotificationManager.IMPORTANCE_HIGH),
            NotificationChannel(EVENT_CHANNEL, "Event Reminders", NotificationManager.IMPORTANCE_DEFAULT),
            NotificationChannel(MAINTENANCE_CHANNEL, "Maintenance Alerts", NotificationManager.IMPORTANCE_DEFAULT),
            NotificationChannel(PROJECT_CHANNEL, "Village Project Alerts", NotificationManager.IMPORTANCE_HIGH)
        ).forEach(manager::createNotificationChannel)
    }

    fun notify(context: Context, channel: String, title: String, body: String, id: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.ic_stat_grama)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        NotificationManagerCompat.from(context).notify(id, notification)
    }
}

class EventReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        NotificationHelper.notify(
            applicationContext,
            NotificationHelper.EVENT_CHANNEL,
            inputData.getString("title") ?: "Event Reminder",
            inputData.getString("body") ?: "A village event starts soon."
        )
        return Result.success()
    }
}

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application as GramaAnganaApp
    private val db = app.database
    private val session = app.session
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val today = LocalDate.now().toString()
    private val now = "${LocalDate.now()} ${LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))}"

    val selectedDate = MutableStateFlow(LocalDate.now())
    val calendarMonth = MutableStateFlow(YearMonth.now())
    val events = db.eventDao().getAllEvents().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val nextEvent = db.eventDao().getNextEvent(now).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val projects = db.maintenanceDao().getAllProjects().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val alerts = db.alertDao().getAllAlerts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val todayBookings = db.bookingDao().getBookingsForDate(today).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val selectedBookings: StateFlow<List<BookingEntity>> = selectedDate
        .flatMapLatest { db.bookingDao().getBookingsForDate(it.toString()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val allBookings = db.bookingDao().getAllBookings().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val myBookings: StateFlow<List<BookingEntity>> =
        if (session.userId > 0) db.bookingDao().getBookingsByUser(session.userId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
        else MutableStateFlow(emptyList())
    val supportedProjects: StateFlow<List<SupportedProject>> =
        if (session.userId > 0) db.userSupportDao().getSupportedProjectsByUser(session.userId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
        else MutableStateFlow(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) { seedDatabase() }
    }

    private suspend fun seedDatabase() {
        if (db.userDao().count() == 0) {
            db.userDao().insertUser(UserEntity(fullName = "Demo Admin", mobileNumber = "9999900000", passwordHash = PasswordUtils.hash("admin123"), role = "ADMIN"))
        }
        if (db.eventDao().count() == 0) {
            val base = LocalDate.now()
            listOf(
                EventEntity(name = "Deepawali Community Celebration", date = base.plusDays(15).toString(), time = "18:00", location = "Village Hall, Main Courtyard", description = "Families gather for lamps, rangoli, sweets, music, and a shared dinner in the main courtyard.", bannerImageRes = R.drawable.banner_deepawali),
                EventEntity(name = "Monthly Gram Sabha Meeting", date = base.plusDays(7).toString(), time = "10:00", location = "Community Centre, Ground Floor", description = "Open council meeting for budget updates, road work planning, and resident questions.", bannerImageRes = R.drawable.banner_sabha),
                EventEntity(name = "Youth Cricket Tournament", date = base.plusDays(21).toString(), time = "08:00", location = "Village Maidan", description = "Inter-lane cricket matches with refreshments and prize distribution for youth teams.", bannerImageRes = R.drawable.banner_cricket)
            ).forEach { id ->
                val eventId = db.eventDao().insertEvent(id)
                scheduleEventReminder(id.copy(id = eventId.toInt()))
            }
        }
        if (db.maintenanceDao().count() == 0) {
            listOf(
                MaintenanceProjectEntity(name = "Ceiling Fan Replacement - Village Hall", description = "3 of 5 fans need replacement before summer gatherings.", goalAmount = 5000f, collectedAmount = 3200f),
                MaintenanceProjectEntity(name = "Solar Street Lighting - North Lane", description = "Install two solar lamps near the bus stop lane.", goalAmount = 12000f, collectedAmount = 7800f),
                MaintenanceProjectEntity(name = "Community Well Repair", description = "Repair pulley and reinforce stone lining near the well.", goalAmount = 8000f, collectedAmount = 7500f)
            ).forEach { db.maintenanceDao().insertProject(it) }
        }
        if (db.alertDao().count() == 0) {
            db.alertDao().insertAlert(VillageAlertEntity(title = "Well Pathway Repairs", description = "Use the North Gate entrance as an alternate route", status = "IN_PROGRESS"))
            db.alertDao().insertAlert(VillageAlertEntity(title = "Electricity Meter Upgrade", description = "Power outage expected 10 AM-12 PM on maintenance days", status = "COMPLETED"))
        }
    }

    fun setDate(date: LocalDate) { selectedDate.value = date }
    fun previousMonth() { calendarMonth.value = calendarMonth.value.minusMonths(1) }
    fun nextMonth() { calendarMonth.value = calendarMonth.value.plusMonths(1) }

    suspend fun login(mobile: String, password: String): String? {
        if (mobile.length != 10 || mobile.any { !it.isDigit() }) return "Enter a valid 10-digit mobile number"
        if (password.length < 6) return "Password must be at least 6 characters"

        val firebaseResult = runCatching {
            firebaseAuth.signInWithEmailAndPassword(FirebaseAuthMapper.emailForMobile(mobile), password).await()
        }

        if (firebaseResult.isSuccess) {
            val firebaseUser = firebaseResult.getOrNull()?.user
            val localUser = withContext(Dispatchers.IO) {
                db.userDao().getUserByMobile(mobile) ?: createLocalUserFromFirebase(
                    name = firebaseUser?.displayName?.takeIf { it.isNotBlank() } ?: "Village Member",
                    mobile = mobile,
                    password = password,
                    role = "USER"
                )
            }
            session.saveUser(localUser)
            return null
        }

        val localUser = withContext(Dispatchers.IO) { db.userDao().getUserByMobile(mobile) }
        return if (localUser != null && localUser.role == "ADMIN" && localUser.passwordHash == PasswordUtils.hash(password)) {
            session.saveUser(localUser)
            null
        } else {
            "Invalid mobile number or password"
        }
    }

    suspend fun register(name: String, mobile: String, password: String, confirm: String): String? {
        val error = validateRegistration(name, mobile, password, confirm)
        if (error != null) return error

        val firebaseResult = runCatching {
            val result = firebaseAuth.createUserWithEmailAndPassword(FirebaseAuthMapper.emailForMobile(mobile), password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name.trim()).build())?.await()
            result
        }

        if (firebaseResult.isFailure) {
            val message = firebaseResult.exceptionOrNull()?.message.orEmpty()
            return when {
                message.contains("email address is already", ignoreCase = true) -> "This mobile number is already registered"
                message.contains("network", ignoreCase = true) -> "Could not reach Firebase. Check your internet connection."
                else -> "Firebase registration failed. ${message.ifBlank { "Please try again." }}"
            }
        }

        val user = withContext(Dispatchers.IO) {
            db.userDao().getUserByMobile(mobile) ?: createLocalUserFromFirebase(name.trim(), mobile, password, "USER")
        }
        session.saveUser(user)
        return null
    }

    fun continueAsGuest() { session.saveGuest() }
    fun logout() {
        runCatching { firebaseAuth.signOut() }
        session.clear()
    }
    fun isGuest() = session.isGuest
    fun isAdmin() = session.isAdmin
    fun userName() = session.userName
    fun userMobile() = session.userMobile
    fun userId() = session.userId
    fun notificationsEnabled() = session.notificationsEnabled
    fun setNotificationsEnabled(value: Boolean) { session.notificationsEnabled = value }

    suspend fun submitBooking(name: String, phone: String, date: LocalDate, slot: String, purpose: String, notes: String): String {
        if (session.isGuest) return "REGISTER_REQUIRED"
        if (name.isBlank() || phone.length != 10 || purpose.isBlank()) return "Please complete all required booking details"
        return withContext(Dispatchers.IO) {
            if (db.bookingDao().isSlotTaken(date.toString(), slot)) {
                "This slot was just booked. Please choose another."
            } else {
                runCatching {
                    db.bookingDao().insertBooking(
                        BookingEntity(
                            userId = max(session.userId, 0),
                            bookerName = name.trim(),
                            bookerPhone = phone,
                            date = date.toString(),
                            timeSlot = slot,
                            purpose = purpose,
                            notes = notes.take(200),
                            status = "PENDING"
                        )
                    )
                }.fold({ "Booking request submitted! Awaiting admin approval." }, { "This slot was just booked. Please choose another." })
            }
        }
    }

    fun updateBookingStatus(booking: BookingEntity, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.bookingDao().updateBookingStatus(booking.id, status)
            if (status == "APPROVED") {
                NotificationHelper.notify(getApplication(), NotificationHelper.BOOKING_CHANNEL, "Booking approved", "Your booking for ${DateUtils.display(booking.date)} ${DateUtils.slotLabel(booking.timeSlot)} has been approved!")
            }
        }
    }

    fun supportProject(project: MaintenanceProjectEntity, onDone: (String) -> Unit) {
        if (session.isGuest) {
            onDone("REGISTER_REQUIRED")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val updated = min(project.goalAmount, project.collectedAmount + 50f)
            val reached = updated >= project.goalAmount
            db.maintenanceDao().updateCollected(project.id, updated, reached)
            db.userSupportDao().insertSupport(UserSupportEntity(userId = session.userId, projectId = project.id, amountSupported = 50f))
            if (reached && !project.isGoalReached) {
                NotificationHelper.notify(getApplication(), NotificationHelper.MAINTENANCE_CHANNEL, "Funding goal reached", "${project.name} has reached its funding goal! Thank you for your support.")
            }
            withContext(Dispatchers.Main) { onDone("Thank you! Your ₹50 support has been recorded.") }
        }
    }

    fun completeAlert(alert: VillageAlertEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.alertDao().updateAlertStatus(alert.id, "COMPLETED")
            NotificationHelper.notify(getApplication(), NotificationHelper.PROJECT_CHANNEL, "Project alert completed", "${alert.title} - Work has been completed. Normal access restored.")
        }
    }

    private fun scheduleEventReminder(event: EventEntity) {
        val eventTime = LocalDateTime.of(LocalDate.parse(event.date), LocalTime.parse(event.time))
        val delayMillis = java.time.Duration.between(LocalDateTime.now(), eventTime.minusHours(1)).toMillis()
        if (delayMillis <= 0) return
        val data = Data.Builder()
            .putString("title", "${event.name} starts in 1 hour")
            .putString("body", "${event.name} starts in 1 hour at ${event.location}")
            .build()
        val request = OneTimeWorkRequestBuilder<EventReminderWorker>().setInitialDelay(delayMillis, TimeUnit.MILLISECONDS).setInputData(data).build()
        WorkManager.getInstance(getApplication()).enqueue(request)
    }

    private suspend fun createLocalUserFromFirebase(name: String, mobile: String, password: String, role: String): UserEntity {
        val newUser = UserEntity(fullName = name, mobileNumber = mobile, passwordHash = PasswordUtils.hash(password), role = role)
        val id = db.userDao().insertUser(newUser).toInt()
        return newUser.copy(id = id)
    }
}

private fun validateRegistration(name: String, mobile: String, password: String, confirm: String): String? = when {
    name.isBlank() -> "Full name cannot be empty"
    mobile.length != 10 || mobile.any { !it.isDigit() } -> "Enter a valid 10-digit mobile number"
    password.length < 6 -> "Password must be at least 6 characters"
    password != confirm -> "Passwords do not match"
    else -> null
}

@Composable
fun GramaAnganaRoot() {
    val context = LocalContext.current
    val app = context.applicationContext as GramaAnganaApp
    val vm: AppViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = AppViewModel(app) as T
    })
    val navController = rememberNavController()
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Permission launcher is shown from composition below; no work needed here.
        }
    }

    MaterialTheme(
        colorScheme = androidx.compose.material3.lightColorScheme(primary = DeepForest, secondary = Sage, tertiary = Terracotta, background = Bone, surface = SurfaceWhite, onPrimary = Color.White, onBackground = TextDark),
        typography = androidx.compose.material3.Typography(
            headlineLarge = androidx.compose.ui.text.TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            titleMedium = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
            bodyMedium = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
            labelLarge = androidx.compose.ui.text.TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        )
    ) {
        Surface(color = Bone, modifier = Modifier.fillMaxSize()) {
            NotificationPermissionRequest()
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") { SplashScreen(vm, navController) }
                composable("auth") { AuthScreen(vm, navController, snackbar) }
                composable("main/{tab}") { backStack ->
                    val startTab = backStack.arguments?.getString("tab") ?: "home"
                    MainShell(vm, navController, snackbar, startTab)
                }
                composable("booking/{date}/{slot}") { backStack ->
                    val date = LocalDate.parse(backStack.arguments?.getString("date"))
                    val slot = backStack.arguments?.getString("slot").orEmpty()
                    BookingFormScreen(vm, navController, snackbar, date, slot)
                }
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                SnackbarHost(hostState = snackbar)
            }
        }
    }
}

@Composable
private fun NotificationPermissionRequest() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

@Composable
fun SplashScreen(vm: AppViewModel, navController: NavController) {
    val context = LocalContext.current
    var start by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(if (start) 1f else 0f, tween(2500, easing = LinearEasing), label = "splash")
    LaunchedEffect(Unit) {
        start = true
        delay(2500)
        val route = if ((context.applicationContext as GramaAnganaApp).session.isLoggedIn) "main/home" else "auth"
        navController.navigate(route) { popUpTo("splash") { inclusive = true } }
    }
    Column(Modifier.fillMaxSize().background(DeepForest).padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CourtyardMark(Modifier.size(140.dp), Color(0xFFFCF9F8))
        Spacer(Modifier.height(24.dp))
        Text("Grama-Angana", color = Color(0xFFFCF9F8), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Digital town square for village life", color = Color(0xFFE7EFE3), fontSize = 14.sp)
        Spacer(Modifier.height(32.dp))
        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = Color(0xFFFCF9F8), trackColor = Sage)
    }
}

@Composable
fun AuthScreen(vm: AppViewModel, navController: NavController, snackbar: SnackbarHostState) {
    var registerMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LazyColumn(Modifier.fillMaxSize().background(Bone), contentPadding = PaddingValues(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Spacer(Modifier.height(42.dp))
            CourtyardMark(Modifier.size(98.dp), DeepForest)
            Spacer(Modifier.height(16.dp))
            Text("Grama-Angana", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(if (registerMode) "Create your village account" else "Welcome back to your village space", color = TextSecondary)
            Spacer(Modifier.height(28.dp))
        }
        if (registerMode) {
            item { OutlinedInputField("Full Name", name, { name = it }, KeyboardType.Text, Icons.Default.Person) }
        }
        item { OutlinedInputField("Mobile Number", mobile, { if (it.length <= 10) mobile = it.filter(Char::isDigit) }, KeyboardType.Phone, Icons.Default.AccountCircle) }
        item { OutlinedInputField("Password", password, { password = it }, KeyboardType.Password, Icons.Default.Visibility, password = true) }
        if (registerMode) {
            item { OutlinedInputField("Confirm Password", confirm, { confirm = it }, KeyboardType.Password, Icons.Default.Visibility, password = true) }
        }
        item {
            Spacer(Modifier.height(12.dp))
            PrimaryButton(if (registerMode) "Create Account" else "Login Securely", enabled = !loading) {
                loading = true
                scope.launch {
                    val error = if (registerMode) vm.register(name, mobile, password, confirm) else {
                        when {
                            mobile.length != 10 -> "Enter a valid 10-digit mobile number"
                            password.length < 6 -> "Password must be at least 6 characters"
                            else -> vm.login(mobile, password)
                        }
                    }
                    loading = false
                    if (error == null) navController.navigate("main/home") { popUpTo("auth") { inclusive = true } } else snackbar.showSnackbar(error)
                }
            }
            Spacer(Modifier.height(10.dp))
            OutlinedButton(onClick = { registerMode = !registerMode }, modifier = Modifier.fillMaxWidth().height(52.dp), shape = CardShape, colors = ButtonDefaults.outlinedButtonColors(contentColor = DeepForest)) {
                Text(if (registerMode) "Back to Login" else "Register an Account")
            }
            TextButton(onClick = { vm.continueAsGuest(); navController.navigate("main/home") { popUpTo("auth") { inclusive = true } } }) {
                Text("Continue as Guest", color = Terracotta, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(8.dp))
            Text("Demo admin: 9999900000 / admin123", color = TextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun MainShell(vm: AppViewModel, rootNav: NavHostController, snackbar: SnackbarHostState, startTab: String) {
    var currentTab by remember(startTab) { mutableStateOf(startTab) }
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = SurfaceWhite) {
                listOf(
                    Triple("home", "Home", Icons.Default.Home),
                    Triple("calendar", "Calendar", Icons.Default.CalendarMonth),
                    Triple("events", "Events", Icons.Default.Flag),
                    Triple("profile", "Profile", Icons.Default.Person)
                ).forEach { (route, label, icon) ->
                    NavigationBarItem(selected = currentTab == route, onClick = { currentTab = route }, icon = { Icon(icon, null) }, label = { Text(label) })
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize().background(Bone)) {
            when (currentTab) {
                "home" -> HomeScreen(vm, onTab = { currentTab = it }, rootNav = rootNav)
                "calendar" -> CalendarScreen(vm, rootNav, snackbar)
                "events" -> EventsScreen(vm, rootNav, snackbar)
                "profile" -> ProfileScreen(vm, rootNav)
            }
        }
    }
}

@Composable
fun HomeScreen(vm: AppViewModel, onTab: (String) -> Unit, rootNav: NavController) {
    val todayBookings by vm.todayBookings.collectAsState()
    val nextEvent by vm.nextEvent.collectAsState()
    val projects by vm.projects.collectAsState()
    val alerts by vm.alerts.collectAsState()
    val slot = DateUtils.currentSlot()
    val activeBooking = todayBookings.firstOrNull { it.timeSlot == slot }
    val activeProjects = projects.filter { !it.isGoalReached }
    val fundingGap = activeProjects.sumOf { max(0.0, (it.goalAmount - it.collectedAmount).toDouble()) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Text(if (vm.isGuest()) "Welcome, Guest" else "Namaskar, ${vm.userName()}!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text("Here is what is happening around the village today.", color = TextSecondary)
        }
        item {
            StatusCard("Village Hall", if (activeBooking == null) "✓ Available Today" else "Booked Until ${DateUtils.slotLabel(activeBooking.timeSlot).substringAfter("-")}", "Tap to check all slots", DeepForest) { onTab("calendar") }
        }
        item {
            StatusCard("Next Gathering", nextEvent?.name ?: "No events scheduled", nextEvent?.let { DateUtils.event(it.date, it.time) } ?: "Check back soon", Sage) { onTab("events") }
        }
        item {
            StatusCard("Village Projects", "${activeProjects.size} active projects", "₹${fundingGap.toInt()} total funding gap", Terracotta) { onTab("events") }
        }
        item { SectionHeader("Quick Actions") }
        item {
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.height(220.dp), verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), userScrollEnabled = false) {
                items(
                    listOf(
                        Triple(Icons.Default.MeetingRoom, "Book Hall", "calendar"),
                        Triple(Icons.Default.CalendarMonth, "Community Calendar", "calendar"),
                        Triple(Icons.Default.Event, "Events", "events"),
                        Triple(Icons.Default.AccountCircle, "My Bookings", "profile")
                    )
                ) { item -> QuickActionItem(item.first, item.second) { onTab(item.third) } }
            }
        }
        item { SectionHeader("Village Project Alerts") }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(alerts) { alert -> AlertCard(alert, Modifier.width(280.dp)) }
            }
        }
    }
}

@Composable
fun CalendarScreen(vm: AppViewModel, rootNav: NavController, snackbar: SnackbarHostState) {
    val month by vm.calendarMonth.collectAsState()
    val selected by vm.selectedDate.collectAsState()
    val bookings by vm.selectedBookings.collectAsState()
    val bookedSlots = bookings.associateBy { it.timeSlot }
    val scope = rememberCoroutineScope()
    var guestDialog by remember { mutableStateOf(false) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("Book a Space", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark) }
        item { MonthCalendar(month, selected, bookings.map { LocalDate.parse(it.date) }.toSet(), vm::previousMonth, vm::nextMonth, vm::setDate) }
        item { SectionHeader("Time Slots for ${DateUtils.display(selected.toString())}") }
        items(TimeSlots) { slot ->
            val booking = bookedSlots[slot]
            TimeSlotCard(DateUtils.slotLabel(slot), if (booking == null) "Free" else "Booked", booking?.bookerName?.substringBefore(" ")) {
                if (booking == null) {
                    if (vm.isGuest()) guestDialog = true else rootNav.navigate("booking/${selected}/$slot")
                }
            }
        }
    }
    if (guestDialog) RegisterDialog("You need to register to book the Village Hall.", rootNav) { guestDialog = false }
}

@Composable
fun BookingFormScreen(vm: AppViewModel, navController: NavController, snackbar: SnackbarHostState, date: LocalDate, slot: String) {
    var name by remember { mutableStateOf(if (vm.isGuest()) "" else vm.userName()) }
    var phone by remember { mutableStateOf(if (vm.isGuest()) "" else vm.userMobile()) }
    var purpose by remember { mutableStateOf(Purposes.first()) }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var guestDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LazyColumn(Modifier.fillMaxSize().background(Bone), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
                Text("Booking Request", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
        item {
            CardBlock("Contact Details") {
                OutlinedInputField("Full Name", name, { name = it }, KeyboardType.Text, Icons.Default.Person)
                OutlinedInputField("Mobile Number", phone, { if (it.length <= 10) phone = it.filter(Char::isDigit) }, KeyboardType.Phone, Icons.Default.AccountCircle)
            }
        }
        item {
            CardBlock("Booking Details") {
                Box {
                    OutlinedTextField(value = purpose, onValueChange = {}, readOnly = true, label = { Text("Purpose / Activity Type") }, modifier = Modifier.fillMaxWidth().clickable { expanded = true }, shape = CardShape)
                    androidx.compose.material3.DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        Purposes.forEach { androidx.compose.material3.DropdownMenuItem(text = { Text(it) }, onClick = { purpose = it; expanded = false }) }
                    }
                }
                OutlinedTextField(value = DateUtils.display(date.toString()), onValueChange = {}, readOnly = true, label = { Text("Selected Date") }, modifier = Modifier.fillMaxWidth(), shape = CardShape)
                OutlinedTextField(value = DateUtils.slotLabel(slot), onValueChange = {}, readOnly = true, label = { Text("Selected Time Slot") }, modifier = Modifier.fillMaxWidth(), shape = CardShape)
                OutlinedTextField(value = notes, onValueChange = { if (it.length <= 200) notes = it }, label = { Text("Notes / Special Requests") }, minLines = 3, modifier = Modifier.fillMaxWidth(), shape = CardShape)
            }
        }
        item {
            PrimaryButton("Submit Request", enabled = name.isNotBlank() && phone.length == 10) {
                scope.launch {
                    val result = vm.submitBooking(name, phone, date, slot, purpose, notes)
                    if (result == "REGISTER_REQUIRED") guestDialog = true else {
                        snackbar.showSnackbar(result)
                        if (result.startsWith("Booking")) navController.popBackStack()
                    }
                }
            }
        }
    }
    if (guestDialog) RegisterDialog("You need to register to book the Village Hall.", navController) { guestDialog = false }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(vm: AppViewModel, rootNav: NavController, snackbar: SnackbarHostState) {
    val events by vm.events.collectAsState()
    val projects by vm.projects.collectAsState()
    val scope = rememberCoroutineScope()
    var selectedEvent by remember { mutableStateOf<EventEntity?>(null) }
    var guestDialog by remember { mutableStateOf(false) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Events & Community", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = { rootNav.navigate("main/calendar") }) { Text("View Calendar", color = Terracotta) }
            }
        }
        item { SectionHeader("Community Events") }
        if (events.isEmpty()) {
            item { EmptyState("No events scheduled yet. Check back soon!") }
        } else {
            items(events) { event -> EventCard(event) { selectedEvent = event } }
        }
        item { SectionHeader("Community Maintenance") }
        items(projects) { project ->
            MaintenanceCard(project) {
                vm.supportProject(project) { message ->
                    if (message == "REGISTER_REQUIRED") guestDialog = true else scope.launch { snackbar.showSnackbar(message) }
                }
            }
        }
    }
    selectedEvent?.let { event ->
        ModalBottomSheet(onDismissRequest = { selectedEvent = null }, sheetState = rememberModalBottomSheetState()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Image(painterResource(event.bannerImageRes), null, Modifier.fillMaxWidth().height(170.dp).clip(CardShape), contentScale = ContentScale.Crop)
                Text(event.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(DateUtils.event(event.date, event.time), color = Terracotta, fontWeight = FontWeight.SemiBold)
                Text(event.location, color = TextSecondary)
                Text(event.description, color = TextDark)
                Spacer(Modifier.height(24.dp))
            }
        }
    }
    if (guestDialog) RegisterDialog("Register to support this project.", rootNav) { guestDialog = false }
}

@Composable
fun ProfileScreen(vm: AppViewModel, rootNav: NavController) {
    val myBookings by vm.myBookings.collectAsState()
    val allBookings by vm.allBookings.collectAsState()
    val supported by vm.supportedProjects.collectAsState()
    var notifications by remember { mutableStateOf(vm.notificationsEnabled()) }
    val bookings = if (vm.isAdmin()) allBookings else myBookings
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { Text("Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
        item {
            CardBlock(null) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Box(Modifier.size(64.dp).clip(CircleShape).background(DeepForest), contentAlignment = Alignment.Center) {
                        Text((if (vm.isGuest()) "G" else vm.userName().split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")).ifBlank { "U" }, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                    Column(Modifier.weight(1f)) {
                        Text(if (vm.isGuest()) "Guest" else vm.userName(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(if (vm.isGuest()) "Read-only village access" else vm.userMobile(), color = TextSecondary)
                        AssistChip(onClick = {}, label = { Text(if (vm.isGuest()) "Guest" else if (vm.isAdmin()) "Admin" else "User") })
                    }
                }
            }
        }
        item { SectionHeader(if (vm.isAdmin()) "All Booking Requests" else "My Bookings") }
        if (bookings.isEmpty()) item { EmptyState("No bookings yet.") } else items(bookings) { booking -> BookingRow(booking, vm.isAdmin()) { status -> vm.updateBookingStatus(booking, status) } }
        item { SectionHeader("Supported Projects") }
        if (supported.isEmpty()) item { EmptyState("No supported projects yet.") } else items(supported) { project ->
            CardBlock(null) {
                Text(project.name, fontWeight = FontWeight.Bold)
                Text("You supported ₹${project.amountSupported.toInt()} · ₹${project.collectedAmount.toInt()} of ₹${project.goalAmount.toInt()}", color = TextSecondary)
                LinearProgressIndicator(progress = { min(1f, project.collectedAmount / project.goalAmount) }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = DeepForest)
            }
        }
        item {
            CardBlock("Settings") {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, null, tint = DeepForest)
                        Text("Notifications")
                    }
                    Switch(checked = notifications, onCheckedChange = { notifications = it; vm.setNotificationsEnabled(it) })
                }
                Text("App version 1.0", color = TextSecondary)
            }
        }
        item {
            OutlinedButton(
                onClick = { vm.logout(); rootNav.navigate("auth") { popUpTo(0) { inclusive = true } } },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = CardShape,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Terracotta)
            ) {
                Icon(Icons.Default.Logout, null)
                Spacer(Modifier.width(8.dp))
                Text("Logout")
            }
        }
    }
}

@Composable
fun MonthCalendar(month: YearMonth, selected: LocalDate, bookingDates: Set<LocalDate>, previous: () -> Unit, next: () -> Unit, onDate: (LocalDate) -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                IconButton(previous) { Icon(Icons.Default.ArrowBack, null) }
                Text("${month.month.getDisplayName(TextStyle.FULL, Locale("en", "IN"))} ${month.year}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(next) { Icon(Icons.Default.ArrowForward, null) }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { Text(it, Modifier.weight(1f), textAlign = TextAlign.Center, color = TextSecondary, fontSize = 12.sp) }
            }
            val first = month.atDay(1)
            val leading = first.dayOfWeek.value % 7
            val days = List(leading) { null } + (1..month.lengthOfMonth()).map { month.atDay(it) }
            days.chunked(7).forEach { week ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    week.forEach { day ->
                        Box(Modifier.weight(1f).aspectRatio(1f).padding(2.dp).clip(CircleShape).background(if (day == selected) DeepForest else Color.Transparent).clickable(enabled = day != null) { day?.let(onDate) }, contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(day?.dayOfMonth?.toString().orEmpty(), color = if (day == selected) Color.White else TextDark, fontWeight = if (day == LocalDate.now()) FontWeight.Bold else FontWeight.Normal)
                                if (day != null && bookingDates.contains(day)) Box(Modifier.size(5.dp).clip(CircleShape).background(Terracotta))
                            }
                        }
                    }
                    repeat(7 - week.size) { Spacer(Modifier.weight(1f).aspectRatio(1f)) }
                }
            }
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, subtitle: String, backgroundColor: Color, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = backgroundColor), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, color = Color.White.copy(alpha = .85f), fontSize = 14.sp)
            Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.White.copy(alpha = .9f), fontSize = 13.sp)
        }
    }
}

@Composable
fun QuickActionItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Card(Modifier.fillMaxSize().clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(icon, null, tint = DeepForest, modifier = Modifier.size(30.dp))
            Spacer(Modifier.height(10.dp))
            Text(label, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun TimeSlotCard(timeRange: String, status: String, bookerName: String?, onClick: () -> Unit) {
    val free = status == "Free"
    Card(Modifier.fillMaxWidth().clickable(enabled = free, onClick = onClick), colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(timeRange, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                if (bookerName != null) Text("Booked by $bookerName", color = TextSecondary)
            }
            StatusChip(status, if (free) FreeGreen else BookedRed)
        }
    }
}

@Composable
fun EventCard(event: EventEntity, onViewDetails: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column {
            Image(painterResource(event.bannerImageRes), null, Modifier.fillMaxWidth().height(150.dp), contentScale = ContentScale.Crop)
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(event.name, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text(DateUtils.event(event.date, event.time), color = Terracotta, fontWeight = FontWeight.SemiBold)
                Text(event.location, color = TextSecondary)
                Text(event.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                TextButton(onClick = onViewDetails) { Text("View Details", color = DeepForest, fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}

@Composable
fun MaintenanceCard(project: MaintenanceProjectEntity, onSupport: () -> Unit) {
    val progress = min(1f, project.collectedAmount / project.goalAmount)
    Card(colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (project.isGoalReached) Box(Modifier.fillMaxWidth().clip(CardShape).background(DeepForest).padding(8.dp), contentAlignment = Alignment.Center) { Text("Goal Reached! 🎉", color = Color.White, fontWeight = FontWeight.Bold) }
            Text(project.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(project.description, color = TextSecondary)
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape), color = DeepForest, trackColor = Color(0xFFE5E5E5), strokeCap = StrokeCap.Round)
            Text("₹${project.collectedAmount.toInt()} collected of ₹${project.goalAmount.toInt()} goal", fontWeight = FontWeight.SemiBold)
            Button(onClick = onSupport, enabled = !project.isGoalReached, colors = ButtonDefaults.buttonColors(containerColor = Terracotta), shape = CardShape) {
                Icon(Icons.Default.Favorite, null)
                Spacer(Modifier.width(8.dp))
                Text(if (project.isGoalReached) "Goal Reached 🎉" else "Support")
            }
        }
    }
}

@Composable
fun AlertCard(alert: VillageAlertEntity, modifier: Modifier = Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("${alert.title} - ${if (alert.status == "COMPLETED") "Completed" else "In Progress"}", fontWeight = FontWeight.Bold)
            Text(alert.description, color = TextSecondary)
            StatusChip(if (alert.status == "COMPLETED") "Completed" else "In Progress", if (alert.status == "COMPLETED") DeepForest else Terracotta)
        }
    }
}

@Composable
fun SectionHeader(title: String, actionLabel: String? = null, onAction: (() -> Unit)? = null) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        if (actionLabel != null && onAction != null) TextButton(onClick = onAction) { Text(actionLabel, color = Terracotta) }
    }
}

@Composable
fun PrimaryButton(label: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth().height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = DeepForest), shape = CardShape) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun OutlinedInputField(label: String, value: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType, leadingIcon: ImageVector, password: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(leadingIcon, null) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (password) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        shape = CardShape,
        singleLine = !label.startsWith("Notes")
    )
}

@Composable
fun CardBlock(title: String?, content: @Composable ColumnScope.() -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (title != null) Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

@Composable
fun StatusChip(text: String, color: Color) {
    Box(Modifier.clip(CircleShape).background(color.copy(alpha = .14f)).border(1.dp, color.copy(alpha = .3f), CircleShape).padding(horizontal = 12.dp, vertical = 6.dp)) {
        Text(text, color = color, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
    }
}

@Composable
fun BookingRow(booking: BookingEntity, admin: Boolean, onStatus: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Card(Modifier.fillMaxWidth().clickable { expanded = !expanded }, colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(booking.purpose, fontWeight = FontWeight.Bold)
                    Text("${DateUtils.display(booking.date)} · ${DateUtils.slotLabel(booking.timeSlot)}", color = TextSecondary)
                }
                StatusChip(booking.status.lowercase().replaceFirstChar { it.titlecase() }, when (booking.status) { "APPROVED" -> FreeGreen; "REJECTED" -> BookedRed; else -> Terracotta })
            }
            AnimatedVisibility(expanded) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Booker: ${booking.bookerName} · ${booking.bookerPhone}", color = TextSecondary)
                    if (booking.notes.isNotBlank()) Text("Notes: ${booking.notes}")
                    if (admin && booking.status == "PENDING") {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { onStatus("APPROVED") }, colors = ButtonDefaults.buttonColors(containerColor = FreeGreen), shape = CardShape) { Text("Approve") }
                            OutlinedButton(onClick = { onStatus("REJECTED") }, shape = CardShape, colors = ButtonDefaults.outlinedButtonColors(contentColor = BookedRed)) { Text("Reject") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyState(message: String) {
    Card(colors = CardDefaults.cardColors(containerColor = SurfaceWhite), shape = CardShape) {
        Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(Icons.Default.CheckCircle, null, tint = Sage, modifier = Modifier.size(42.dp))
            Text(message, color = TextSecondary, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun RegisterDialog(message: String, navController: NavController, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Register to continue") },
        text = { Text(message) },
        confirmButton = { Button(onClick = { onDismiss(); navController.navigate("auth") { popUpTo(0) { inclusive = true } } }, colors = ButtonDefaults.buttonColors(containerColor = DeepForest)) { Text("Register Now") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel", color = Terracotta) } }
    )
}

@Composable
fun CourtyardMark(modifier: Modifier, color: Color) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val stroke = w * .055f
        drawRoundRect(color = color, topLeft = androidx.compose.ui.geometry.Offset(w * .16f, h * .18f), size = androidx.compose.ui.geometry.Size(w * .68f, h * .18f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(stroke, stroke))
        drawRect(color = color, topLeft = androidx.compose.ui.geometry.Offset(w * .24f, h * .36f), size = androidx.compose.ui.geometry.Size(stroke, h * .44f))
        drawRect(color = color, topLeft = androidx.compose.ui.geometry.Offset(w * .71f, h * .36f), size = androidx.compose.ui.geometry.Size(stroke, h * .44f))
        drawArc(color = color, startAngle = 180f, sweepAngle = 180f, useCenter = false, topLeft = androidx.compose.ui.geometry.Offset(w * .32f, h * .36f), size = androidx.compose.ui.geometry.Size(w * .36f, h * .36f), style = androidx.compose.ui.graphics.drawscope.Stroke(stroke))
        drawRect(color = color, topLeft = androidx.compose.ui.geometry.Offset(w * .12f, h * .80f), size = androidx.compose.ui.geometry.Size(w * .76f, stroke))
    }
}
