package location.chat_head.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import location.chat_head.R

class ChatHeadService : Service() {

   var view: View? = null
   var windowManager: WindowManager? = null

   override fun onBind(intent: Intent): IBinder? {
      return null
   }

   override fun onCreate() {
      super.onCreate()
      view = LayoutInflater.from(this).inflate(R.layout.layout_head, null)

      val params = WindowManager.LayoutParams(
         WindowManager.LayoutParams.WRAP_CONTENT,
         WindowManager.LayoutParams.WRAP_CONTENT,
         WindowManager.LayoutParams.TYPE_PHONE,
         WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
         PixelFormat.TRANSLUCENT
      )
      params.gravity = Gravity.TOP or Gravity.END
      params.x = -1
      params.y = 100
      windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
      windowManager?.addView(view, params)
   }

   override fun onDestroy() {
      super.onDestroy()
      view?.let { view -> windowManager?.removeView(view) }
   }
}
