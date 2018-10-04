package location.chat_head

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import location.chat_head.service.ChatHeadService

class MainActivity : AppCompatActivity() {
   private val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      checkPermission()
   }

   private fun checkPermission() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
         val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
         startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
      } else {
         initChatHead()
      }
   }

   private fun initChatHead() {
      findViewById<View>(R.id.trigger_head_button).setOnClickListener { showHead() }
   }

   private fun showHead() {
      startService(Intent(this, ChatHeadService::class.java))
      finish()
   }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
         if (resultCode == RESULT_OK) {
            initChatHead()
         } else {
            Toast.makeText(this, "Draw over app not available", Toast.LENGTH_SHORT).show()
         }
      } else {
         super.onActivityResult(requestCode, resultCode, data)
      }
   }
}
