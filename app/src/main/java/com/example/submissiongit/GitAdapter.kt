package com.example.submissiongit


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_row_git.view.*
import kotlin.collections.ArrayList

var list = ArrayList<GitItems>()
lateinit var mcontext: Context

class GitAdapter(private var listData: ArrayList<GitItems>) : RecyclerView.Adapter<GitAdapter.ListViewHolder>() {
    init {
        list = listData
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: CircleImageView = itemView.img_avatar
        var txtUsername: TextView = itemView.txt_username
        var txtName: TextView = itemView.txt_name
        var txtCompany: TextView = itemView.txt_company
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_git, viewGroup, false)
        val listViewHolder = ListViewHolder(view)
        mcontext = viewGroup.context
        return listViewHolder
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgAvatar)

        holder.txtUsername.text = data.username
        holder.txtName.text = data.name
        holder.txtCompany.text = data.company

        holder.itemView.setOnClickListener {
            val gitItem = GitItems(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.follower,
                data.following
            )
            val intentDetail = Intent(mcontext, MainActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DATA, gitItem)
            mcontext.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = list.size


}

