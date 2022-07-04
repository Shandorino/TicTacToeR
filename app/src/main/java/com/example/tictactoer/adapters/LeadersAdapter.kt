package com.example.tictactoer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tictactoer.data.local.Leader
import com.example.tictactoer.databinding.FragmentUserInfoBinding
import com.example.tictactoer.databinding.LeaderCardHolderBinding

class LeadersAdapter: RecyclerView.Adapter<LeadersAdapter.LeadersViewHolder>() {

    private val items: MutableList<Leader> = mutableListOf()

    inner class LeadersViewHolder(private val binding: LeaderCardHolderBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun onBind(leader: Leader) {
                with(binding) {
                    rating.text = leader.rating.toString()
                    fullName.text = "${leader.firstName} ${leader.lastName}"
                    Glide.with(binding.root).load(leader.photoURL).centerCrop().into(userImage)
                }
            }

        }

    fun setList(list: List<Leader>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LeaderCardHolderBinding.inflate(inflater, parent, false)

        return LeadersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeadersViewHolder, position: Int) {
        val item = items[position]

        holder.onBind(item)
    }

    override fun getItemCount(): Int = items.size


}