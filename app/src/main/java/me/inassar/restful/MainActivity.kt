package me.inassar.restful

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import me.inassar.restful.model.DataItem
import me.inassar.restful.sample.SampleDataProvider
import me.inassar.restful.services.MyService
import me.inassar.restful.utils.NetworkHelper

class MainActivity : AppCompatActivity() {

    var dataItemList = SampleDataProvider.dataItemList
    //    lateinit var mDataSource: DataSource
    lateinit var mItemList: List<DataItem>
    lateinit var mCategories: Array<String>
    lateinit var mItemAdapter: DataItemAdapter

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val dataItems = intent?.getParcelableArrayExtra(MyService.MY_SERVICE_PAYLOAD) as Array<DataItem>
            toast("Received ${dataItems.size} items from service")
            mItemList = dataItemList.toList()
            displayDataItems(null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationDrawer()
        database()
        itemDisplayToggle()
//        displayDataItems(null)

        networkCall()

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(
                broadcastReceiver,
                IntentFilter(MyService.MY_SERVICE_MESSAGE)
            )
    }

    private fun networkCall() {
        if (NetworkHelper.hasNetworkAccess(this)) {
            startService(Intent(this@MainActivity, MyService::class.java).apply {
                data = Uri.parse(JSON_URL)
            })
        } else {
            toast("Network not available!")
        }
    }

    private fun itemDisplayToggle() {
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val grid = settings.getBoolean(getString(R.string.pref_display_grid), false)

        if (grid) {
            rvItems.layoutManager = GridLayoutManager(this, 3)
        }
    }

    private fun database() {
//        mDataSource = DataSource(this)
//        mDataSource.open()
//        mDataSource.seedDatabase(dataItemList)
    }

    private fun navigationDrawer() {
        mCategories = resources.getStringArray(R.array.categories)
        left_drawer.adapter = ArrayAdapter(
            this,
            R.layout.drawer_list_item, mCategories
        )
        left_drawer.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val category = mCategories[position]
            toast("You chose $category")
            drawer_layout.closeDrawer(left_drawer)
            displayDataItems(category)
        }
    }

    private fun displayDataItems(category: String?) {
//        mItemList = mDataSource.getAllItems(category)
        if (!mItemList.isNullOrEmpty()) {
            mItemAdapter = DataItemAdapter(this, mItemList)
            rvItems.adapter = mItemAdapter
        } else {
            toast("No data to display")
        }
    }

    override fun onPause() {
        super.onPause()
//        mDataSource.close()
    }

    override fun onResume() {
        super.onResume()
//        mDataSource.open()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadcastReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_signin -> {
                val intent = Intent(this, SigninActivity::class.java)
                startActivityForResult(intent, SIGNIN_REQUEST)
                return true
            }
            R.id.action_settings -> {
                // Show the settings screen
                val settingsIntent = Intent(this, PrefsActivity::class.java)
                startActivity(settingsIntent)
                return true
            }
            R.id.action_all_items -> {
                // display all items
                displayDataItems(null)
                return true
            }
            R.id.action_choose_category -> {
                //open the drawer
                drawer_layout.openDrawer(left_drawer)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == SIGNIN_REQUEST) {
            val email = data!!.getStringExtra(SigninActivity.EMAIL_KEY)
            toast("You signed in as $email")
            getSharedPreferences(MY_GLOBAL_PREFS, Context.MODE_PRIVATE).edit().apply {
                putString(SigninActivity.EMAIL_KEY, email)
                apply()
            }
        }
    }

    companion object {

        private const val SIGNIN_REQUEST = 1001
        val MY_GLOBAL_PREFS = "my_global_prefs"
        private val TAG = MainActivity::class.java.simpleName
        private const val JSON_URL = "http://560057.youcanlearnit.net/services/json/itemsfeed.php"
    }

}
