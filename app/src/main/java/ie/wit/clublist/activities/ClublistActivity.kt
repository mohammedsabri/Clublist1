package ie.wit.clublist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ie.wit.clublist.R
import ie.wit.clublist.helpers.readImage
import ie.wit.clublist.helpers.readImageFromPath
import ie.wit.clublist.helpers.showImagePicker
import ie.wit.clublist.main.MainApp
import it.wit.clublist.models.ClublistModel
import kotlinx.android.synthetic.main.activity_clublist.*
import kotlinx.android.synthetic.main.activity_clublist.edt_clublist_title
import kotlinx.android.synthetic.main.activity_clublist.edt_details
import kotlinx.android.synthetic.main.card_clublist.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class ClublistActivity : AppCompatActivity(), AnkoLogger {

  var clublist = ClublistModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_clublist)

    //setting the toolbar and adding the title.
    setSupportActionBar(toolbarAdd). apply {
      title = getString(R.string.title_clublist_activity)
    }
    info("Clublist Activity started..")

    app = application as MainApp
    var edit = false

    if (intent.hasExtra("clublist_edit")) {
      edit = true
      clublist = intent.extras?.getParcelable("clublist_edit")!!
      edt_clublist_title.setText(clublist.title)
      edt_details.setText(clublist.details)
      edt_clubvalue.setText(clublist.value)
      img_clublistImage.setImageBitmap(readImageFromPath(this, clublist.image))
      if (clublist.image != null) {
        btn_chooseImage.setText(R.string.change_clublist_image)
      }
      btnAdd.setText(R.string.save_clublist)
    }

    btnAdd.setOnClickListener() {
      clublist.title = edt_clublist_title.text.toString()
      clublist.details = edt_details.text.toString()
      clublist.value = edt_clubvalue.text.toString()
      // input data validation
      if (clublist.title.isEmpty()) {
        toast(R.string.enter_clublist_title)
      } else if (clublist.details.isEmpty()) {
        toast(R.string.enter_clublist_details)
      } else if (clublist.value.isEmpty()) {
        toast(R.string.enter_clublist_value)
      }else {
        if (edit) {
          app.clublists.update(clublist.copy())
        } else {
          app.clublists.create(clublist.copy())
        }
      }
      info("add Button Pressed: $edt_clublist_title")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    btn_chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }


  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_clublist, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_delete -> {
        app.clublists.delete(clublist)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          clublist.image = data.getData().toString()
          img_clublistImage.setImageBitmap(readImage(this, resultCode, data))
          btn_chooseImage.setText(R.string.change_clublist_image)
        }
      }

    }
  }
}

