package ie.wit.clublist.models

import android.content.Context
import android.system.Os.read
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.clublist.helpers.exists
import ie.wit.clublist.helpers.read
import ie.wit.clublist.helpers.write
import it.wit.clublist.models.ClublistModel
import org.jetbrains.anko.AnkoLogger
import java.nio.file.Files.exists
import java.nio.file.Files.write
import java.util.*

val JSON_FILE = "clublists.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<ClublistModel>>() {}.type



fun generateRandomId(): Long {
  return Random().nextLong()
}

class ClublistJSONStore : ClublistStore, AnkoLogger {

  val context: Context
  var clublists = mutableListOf<ClublistModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<ClublistModel> {
    return clublists
  }

  override fun create(clublist: ClublistModel) {
    clublist.id = generateRandomId()
    clublists.add(clublist)
    serialize()
  }

  override fun update(clublist: ClublistModel) {
    val clublistsList = findAll() as ArrayList<ClublistModel>
    var foundClublist: ClublistModel? = clublistsList.find { p -> p.id == clublist.id }
    if (foundClublist != null) {
      foundClublist.title = clublist.title
      foundClublist.details = clublist.details
      foundClublist.value = clublist.value
      foundClublist.image = clublist.image

    }
    serialize()
  }

  override fun delete(clublist: ClublistModel) {
    clublists.remove(clublist)
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(clublists, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    clublists = Gson().fromJson(jsonString, listType)
  }
}
