package com.example.notepadmvcpattern.viewer

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.notepadmvcpattern.R
import com.example.notepadmvcpattern.controller.Controller
import com.example.notepadmvcpattern.utils.FileUtils.getUri
import com.example.notepadmvcpattern.utils.GetFileActivityResultContract
import com.example.notepadmvcpattern.utils.PermissionUtil
import com.example.notepadmvcpattern.utils.PermissionUtil.LOCATION_REQUEST_CODE
import com.example.notepadmvcpattern.utils.getPath
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarMain: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var titleName: EditText
    private lateinit var bottomNav: BottomNavigationView
    private var controller: Controller
    private val result = registerForActivityResult(GetFileActivityResultContract()){
//        Toast.makeText(this, it.size,Toast.LENGTH_SHORT).show()
        val uri = getPath(this,it[0])
        getUri(uri,contentResolver, titleName)
        Log.d("vvvvvvvv","nnnnnnn")
    }

    init {
        controller = Controller(viewer = this)
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appBarMain = findViewById(R.id.toolbar)
        titleName = findViewById(R.id.titleName)
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNav = findViewById(R.id.bottomNav)


        if (PermissionUtil.checkPermisssion(this))

        setSupportActionBar(appBarMain)
        appBarMain.title = "Меню"
        setupFrameLayot()

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            appBarMain,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        appBarConfiguration = AppBarConfiguration(
            setOf(
            ), drawerLayout
        )
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {

            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

     private fun setupFrameLayot() {
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_text_size-> {

                }
                R.id.nav_text_color-> {

                }
                R.id.nav_copy-> {
                    textCopyThenPost(titleName.toString())
//                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                    val clipS: ClipData = ClipData.newPlainText("simple text", "Hello, World!")
//                    val copyUri: Uri = Uri.parse("$CONTACTS$COPY_PATH/$clipboard")
//                    val clips: ClipData = ClipData.newUri(contentResolver, "URI", copyUri)
//                    val appIntent = Intent(this, com.example.demo.myapplication::class.java)
//                    val clip: ClipData = ClipData.newIntent("Intent", appIntent)
//                    clipboard.setPrimaryClip(clips)
                }
                R.id.nav_insert-> {

                }
            }
            true
        }
    }
    fun textCopyThenPost(textCopied:String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", textCopied))
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
            Toast.makeText(this, titleName.text, Toast.LENGTH_SHORT).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        when (id) {
            R.id.nav_cart -> {

            }
            R.id.open -> {
                result.launch(false)
            }
            R.id.save -> {

            }
            R.id.download -> {
                controller
            }
            R.id.print -> {
                controller
            }
            R.id.info -> {
                controller
            }
            R.id.power -> {
                controller
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }
    companion object{
        const val CONTACTS = "content://com.example.contacts"
        const val COPY_PATH = "/copy"
    }
}