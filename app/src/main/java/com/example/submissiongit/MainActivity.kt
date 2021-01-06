package com.example.submissiongit


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var listData : ArrayList<GitItems> = ArrayList()
    private lateinit var adapter: GitAdapter

    companion object{
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Github User's Search"

        adapter = GitAdapter(listData)

        recyclerViewGit()
        searchUserGit()
        getUserGit()
    }

    private fun searchUserGit() { search.setOnQueryTextListener(object  : SearchView.OnQueryTextListener! {
        override fun onQueryTextSubmit(query: String): Boolean {
            if (query.isEmpty()) {
                return true
            } else {
                listData.clear()
                getUserGitSearch(query)
            }
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }
    })

    }

    private fun getUserGitSearch(id: String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 6ca0b2146249d297fc38d9904e772df4ec88020b")
        val url = "https://api.github.com/users?q=$id"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetailGit(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun recyclerViewGit() {
        rvGit.layoutManager = LinearLayoutManager(rvGit.context)
        rvGit.setHasFixedSize(true)
        rvGit.addItemDecoration(
            DividerItemDecoration(
                rvGit.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }



    private fun getUserGit() {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 6ca0b2146249d297fc38d9904e772df4ec88020b")
        val url = "https://api.github.com/users"
        client.get(url, object  : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetailGit(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                progressBar.visibility = View.INVISIBLE
                val  errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDetailGit(id: String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 6ca0b2146249d297fc38d9904e772df4ec88020b")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login").toString()
                    val name: String? = jsonObject.getString("name").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    val company: String? = jsonObject.getString("company").toString()
                    val location: String? = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos").toString()
                    val followers: String? = jsonObject.getString("followers").toString()
                    val following: String? = jsonObject.getString("following").toString()
                    listData.add(
                        GitItems(
                            username,
                            name,
                            avatar,
                            company,
                            location,
                            followers,
                            following,
                            repository
                        )
                    )
                    showRecyclerList()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun showRecyclerList() {
        rvGit.layoutManager = LinearLayoutManager(this)
        rvGit.adapter = adapter
    }

}


