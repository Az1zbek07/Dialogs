package com.example.dialog

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        findViewById<MaterialButton>(R.id.btnSimple).setOnClickListener { simpleDialog() }
        findViewById<MaterialButton>(R.id.btnSingle).setOnClickListener { singleChoice() }
        findViewById<MaterialButton>(R.id.btnMulti).setOnClickListener { multiChoice(it) }
        findViewById<MaterialButton>(R.id.btnConfirm).setOnClickListener { confirm() }
        findViewById<MaterialButton>(R.id.btnFullScreen).setOnClickListener { fullScreen() }
        findViewById<MaterialButton>(R.id.btnProgress).setOnClickListener { progressDialog() }
        findViewById<MaterialButton>(R.id.btnCustom).setOnClickListener { customDialog() }
        findViewById<MaterialButton>(R.id.btnHorizontal).setOnClickListener { horizontalDialog() }
    }

    private fun fullScreen() {
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
            .apply {
                setTitle("Fullscreen Dialog")
                setMessage("This is dialog")
                setPositiveButton("Confirm") { dg, _ ->
                    dg.dismiss()
                }
                setNegativeButton("Cancel", null)
                setIcon(R.drawable.ic_launcher_background)
                create()
                show()
            }
    }

    private fun progressDialog() {
        ProgressDialog(this).apply {
            setTitle("Progress")
            setMessage("This is message")
            create()
            show()
        }
    }

    private fun customDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(dialogView)
        val btnNo: MaterialButton = dialogView.findViewById(R.id.btnNo)
        val btnYes: MaterialButton = dialogView.findViewById(R.id.btnYes)
        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }
        btnYes.setOnClickListener { finish() }
        alertDialog.show()
    }

    private fun horizontalDialog() {
        val progressDialog = ProgressDialog(this)
        progressDialog.max = 100
        progressDialog.setTitle("Horizontal progress dialog")
        progressDialog.setMessage("Loading...")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.show()

        val handler: Handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                progressDialog.incrementProgressBy(1)
            }
        }
        Thread{
            try {
                while (progressDialog.progress <= progressDialog.max){
                    Thread.sleep(100)
                    handler.sendMessage(handler.obtainMessage())
                    if (progressDialog.progress == progressDialog.max){
                        progressDialog.dismiss()
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }.start()
    }

    private fun simpleDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Warning")
            setMessage("Do you want to exit")
            setPositiveButton("Yes") { _, _ ->
                finish()
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    private fun singleChoice() {
        AlertDialog.Builder(this).apply {
            setTitle("Languages")
            setPositiveButton("OK", null)
            setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val items = arrayOf("Java", "Kotlin", "Dart", "C++", "Android")
            setSingleChoiceItems(items, 5) { _, which ->
                Toast.makeText(this@MainActivity, items[which], Toast.LENGTH_SHORT).show()
            }
        }.create().show()
    }

    private fun multiChoice(view: View) {
        AlertDialog.Builder(this).apply {
            setTitle("Football Clubs")
            val items = arrayOf("Liverpool", "Barca", "Real", "Totenham", "MCI")
            val choices = booleanArrayOf(false, false, false, false, false)
            setMultiChoiceItems(items, choices) { listener, int, _ ->
                Snackbar.make(view, items[int], Snackbar.LENGTH_SHORT).setAction("UNDO") {}.show()
                listener.dismiss()
            }
        }.create().show()
    }

    private fun confirm() {
        val background: android.widget.LinearLayout =
            findViewById(com.example.dialog.R.id.background)
        androidx.appcompat.app.AlertDialog.Builder(this).apply {
            setTitle("Choose your color")
            val items = kotlin.arrayOf("Purple 200", "Purple 500", "Purple 700")
            setSingleChoiceItems(items, 4) { listener, which ->
                when (which) {
                    0 -> background.setBackgroundColor(
                        androidx.core.content.ContextCompat.getColor(
                            this@MainActivity,
                            com.example.dialog.R.color.purple_200
                        )
                    )
                    1 -> background.setBackgroundColor(
                        androidx.core.content.ContextCompat.getColor(
                            this@MainActivity,
                            com.example.dialog.R.color.purple_500
                        )
                    )
                    2 -> background.setBackgroundColor(
                        androidx.core.content.ContextCompat.getColor(
                            this@MainActivity,
                            com.example.dialog.R.color.purple_700
                        )
                    )
                }
                listener.dismiss()
            }
        }.create().show()
    }
}