package ie.wit.clublist.main

import android.app.Application
import ie.wit.clublist.models.ClublistJSONStore
import ie.wit.clublist.models.ClublistMemStore
import ie.wit.clublist.models.ClublistStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainApp : Application(), AnkoLogger {

  lateinit var clublists: ClublistStore

  override fun onCreate() {
    super.onCreate()
    clublists = ClublistJSONStore(applicationContext)
    info("Clublist started")
  }
}