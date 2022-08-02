package br.com.mbecker

import android.app.Application
import br.com.mbecker.data.AppDatabase
import br.com.mbecker.data.BusinessCardRepository

class App : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessCardRepository(database.businessDao()) }
}