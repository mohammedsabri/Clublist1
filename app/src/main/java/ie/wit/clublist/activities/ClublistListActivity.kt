package ie.wit.clublist.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.clublist.R
import ie.wit.clublist.main.MainApp
import it.wit.clublist.models.ClublistModel
import kotlinx.android.synthetic.main.activity_clublist_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult


class ClublistListActivity : AppCompatActivity(), ClublistListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_clublist_list)
    app = application as MainApp
    toolbar.title = title
    setSupportActionBar(toolbar)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = ClublistAdapter(app.clublists.findAll(), this)
    loadClublists()
  }

  private fun loadClublists() {
    showClublists( app.clublists.findAll())
  }

  fun showClublists (clublists: List<ClublistModel>) {
    recyclerView.adapter = ClublistAdapter(clublists, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_add -> startActivityForResult<ClublistActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onClublistClick(clublist: ClublistModel) {
    startActivityForResult(intentFor<ClublistActivity>().putExtra("clublist_edit", clublist), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadClublists()
    super.onActivityResult(requestCode, resultCode, data)
  }
}