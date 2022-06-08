package com.phyothinzaraung.kotlinflow_example.learn.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phyothinzaraung.kotlinflow_example.R
import com.phyothinzaraung.kotlinflow_example.data.local.entitiy.User

class LocalUserAdapter(private val users: ArrayList<User>): RecyclerView.Adapter<LocalUserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindData(user: User){
            val txtUserName = itemView.findViewById<TextView>(R.id.textViewUserName)
            val txtEmail = itemView.findViewById<TextView>(R.id.textViewUserEmail)
            val image = itemView.findViewById<ImageView>(R.id.imageViewAvatar)

            txtUserName.text = user.name
            txtEmail.text = user.email
            Glide.with(itemView.context).load(user.avatar).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item,parent, false))


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(users[position])
    }

    override fun getItemCount() = users.size

    fun addData(userlist: List<User>){
        users.addAll(userlist)
    }
}