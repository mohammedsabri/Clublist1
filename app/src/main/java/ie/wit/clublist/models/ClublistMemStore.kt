package ie.wit.clublist.models

import it.wit.clublist.models.ClublistModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class ClublistMemStore : ClublistStore, AnkoLogger {

  val clublists = ArrayList<ClublistModel>()

  override fun findAll(): List<ClublistModel> {
    return clublists
  }

  override fun create(clublist: ClublistModel) {
    clublist.id = getId()
    clublists.add(clublist)
    logAll()
  }

  override fun update(clublist: ClublistModel) {
    var foundClublist: ClublistModel? = clublists.find { p -> p.id == clublist.id }
    if (foundClublist != null) {
      foundClublist.title = clublist.title
      foundClublist.details = clublist.details
      foundClublist.value = clublist.value
      foundClublist.image = clublist.image

      logAll();
    }
  }

  override fun delete(clublist: ClublistModel) {
    clublists.remove(clublist)
  }

  fun logAll() {
    clublists.forEach { info("${it}") }
  }
}