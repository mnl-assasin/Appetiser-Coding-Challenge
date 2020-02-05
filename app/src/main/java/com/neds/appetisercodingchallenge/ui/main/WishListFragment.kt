package com.neds.appetisercodingchallenge.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.neds.appetisercodingchallenge.R
import com.neds.appetisercodingchallenge.adapter.ResultAdapter
import com.neds.appetisercodingchallenge.adapter.WishListAdapter
import com.neds.appetisercodingchallenge.data.ObjectBoxManager
import com.neds.appetisercodingchallenge.data.WishList
import com.neds.appetisercodingchallenge.databinding.FragmentListBinding
import com.neds.appetisercodingchallenge.model.ResultModel

/**
 * A simple [Fragment] subclass.
 */
class WishListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: WishListAdapter
    private var wishes = mutableListOf<WishList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        initData()
        return binding.root
    }

    private fun initData() {
        adapter = WishListAdapter(wishes, object : WishListAdapter.Listener {
            override fun onClick(w: WishList) {
                startActivity(SingleViewActivity.makeIntent(activity!!, ResultModel.map(w)))
            }

            override fun onDeleteClick(w: WishList) {
                ObjectBoxManager.removeWishList(w.id)
                wishes.remove(w)
                adapter.notifyDataSetChanged()
            }

            override fun onAddCartClick(w: WishList) {
                ObjectBoxManager.addToCart(ResultModel.map(w))
            }
        })

        ObjectBoxManager.getWishList()?.let {
            wishes.addAll(it)
            adapter.notifyDataSetChanged()
        }

        binding.recyclerView.adapter = adapter
    }
}
