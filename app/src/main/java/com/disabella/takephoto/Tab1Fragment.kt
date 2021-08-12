package com.disabella.takephoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Tab1Fragment : Fragment() {

    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_tab1, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(App.appContext)
        adapter = RecyclerAdapter(ArrayList())
        recyclerView?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val items = DataStorage.loadItems()
        adapter.items.clear()
        adapter.items.addAll(items)
        adapter.notifyDataSetChanged()
    }

}