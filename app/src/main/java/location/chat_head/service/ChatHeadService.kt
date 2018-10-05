package location.chat_head.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import location.chat_head.R


class ChatHeadService : Service() {

   var view: View? = null
   var windowManager: WindowManager? = null
   var collapsedView: View? = null
   var expandedView: View? = null
   var expandedButton: View? = null

   var wrapParams = WindowManager.LayoutParams(
           WindowManager.LayoutParams.WRAP_CONTENT,
           WindowManager.LayoutParams.WRAP_CONTENT,
           WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
           WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
           PixelFormat.TRANSLUCENT
   )
   var expandParams = WindowManager.LayoutParams(
           WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
           WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
           PixelFormat.TRANSLUCENT
   )

   override fun onBind(intent: Intent): IBinder? {
      return null
   }
   override fun onCreate() {
      super.onCreate()
      setupHead()
   }

   private fun setupHead() {
      view = LayoutInflater.from(this).inflate(R.layout.layout_head, null)

      wrapParams.gravity = Gravity.TOP or Gravity.END
      wrapParams.x = -1
      wrapParams.y = 100

      windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
      windowManager?.addView(view, wrapParams)
      collapsedView = view?.findViewById<View>(R.id.collapsed_view)
      expandedView = view?.findViewById<View>(R.id.expanded_view)
      expandedButton = view?.findViewById<View>(R.id.expanded_button)
      collapsedView?.setOnClickListener { expandHead(view!!) }
      Log.d("View", "setuphead")
   }

   private fun expandHead(view: View) {
      windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
      windowManager?.removeView(view)
      windowManager?.addView(view, expandParams)
      collapsedView?.visibility = View.GONE
      expandedView?.visibility = View.VISIBLE
      expandedButton?.setOnClickListener { collapseHead(view) }
      //expandedView?.setOnClickListener { collapseHead(view) }
      collapsedView?.setOnClickListener { null }
      Log.d("View", "Expanded")
   }

   private fun collapseHead(view: View) {
      windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
      windowManager?.removeView(view)
      windowManager?.addView(view, wrapParams)
      collapsedView?.visibility = View.VISIBLE
      expandedView?.visibility = View.GONE
      collapsedView?.setOnClickListener { expandHead(view) }
      expandedButton?.setOnClickListener { null }
      Log.d("View", "Collapse")
   }

   override fun onDestroy() {
      super.onDestroy()
      view?.let { view -> windowManager?.removeView(view) }
   }
}
