package ie.wit.clublist.models

import it.wit.clublist.models.ClublistModel


interface ClublistStore {
  fun findAll(): List<ClublistModel>
  fun create(clublist: ClublistModel)
  fun update(clublist: ClublistModel)
  fun delete(clublist: ClublistModel)
}