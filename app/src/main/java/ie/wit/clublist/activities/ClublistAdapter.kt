package ie.wit.clublist.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.clublist.R
import ie.wit.clublist.helpers.readImageFromPath
import it.wit.clublist.models.ClublistModel
import kotlinx.android.synthetic.main.card_clublist.view.*
import kotlinx.android.synthetic.main.card_clublist.view.edt_clublist_title
import kotlinx.android.synthetic.main.card_clublist.view.edt_details

interface ClublistListener {
  fun onClublistClick(clublist: ClublistModel)
}

class ClublistAdapter constructor(private var clublists: List<ClublistModel>,
                                   private val listener: ClublistListener) : RecyclerView.Adapter<ClublistAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_clublist, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val clublist = clublists[holder.adapterPosition]
    holder.bind(clublist, listener)
  }

  override fun getItemCount(): Int = clublists.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(clublist: ClublistModel, listener: ClublistListener) {
      itemView.edt_clublist_title.text = clublist.title
      itemView.edt_details.text = clublist.details
      itemView.tv_value.text= clublist.value
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, clublist.image))
      itemView.setOnClickListener { listener.onClublistClick(clublist) }
    }
  }
}