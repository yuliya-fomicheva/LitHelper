package com.example.litpantry.screen.characterGraph

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.litpantry.R
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.BookAndCharacter
import com.example.litpantry.database.entity.BookCharacterCrossRef
import com.example.litpantry.databinding.FragmentCharacterGraphBinding
import com.google.android.material.snackbar.Snackbar
import dev.bandb.graphview.AbstractGraphAdapter
import dev.bandb.graphview.decoration.edge.StraightEdgeDecoration
import dev.bandb.graphview.graph.Graph
import dev.bandb.graphview.graph.Node
import dev.bandb.graphview.layouts.GraphLayoutManager
import dev.bandb.graphview.layouts.energy.FruchtermanReingoldLayoutManager
import java.util.*

class CharacterGraphFragment : Fragment() {

    private lateinit var viewModel: CharacterGraphViewModel
    private lateinit var binding: FragmentCharacterGraphBinding
    private lateinit var currentBook: Book
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AbstractGraphAdapter<NodeViewHolder>
    private var currentNode: Node? = null
   // private lateinit var graph: Graph

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterGraphBinding.inflate(layoutInflater, container, false)
        currentBook = arguments?.getSerializable("book") as Book
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar4)
        recyclerView = binding.recycler
        val graphLayoutManager: GraphLayoutManager = FruchtermanReingoldLayoutManager(activity as AppCompatActivity)
        recyclerView.layoutManager = graphLayoutManager
        recyclerView.addItemDecoration(StraightEdgeDecoration())

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar4)

        toolbar.setupWithNavController(navController, appBarConfig)
        init()

    }

    private fun init() {
        viewModel = ViewModelProvider(this)[CharacterGraphViewModel::class.java]
        val graph = createGraph()
        recyclerView = binding.recycler
        recyclerView.layoutManager = FruchtermanReingoldLayoutManager(requireActivity(), 1000)
        setLayoutManager()
        setEdgeDecoration()
        setupGraphView(graph)
    }




   /* private fun setupGraphView ()
    {
        recyclerView = binding.recycler
        recyclerView.layoutManager = FruchtermanReingoldLayoutManager(context, 1000)
        recyclerView.addItemDecoration(StraightEdgeDecoration())
        val graph = createGraph()

        adapter = object : AbstractGraphAdapter<NodeViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.node, parent, false)
                return NodeViewHolder(view)
            }

            override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
                holder.textView.text = Objects.requireNonNull(getNodeData(position)).toString()
            }

        }.apply {
            this.submitGraph(graph)
            recyclerView.adapter = this
        }

        

    }*/



    private fun setLayoutManager() {
        recyclerView.layoutManager = FruchtermanReingoldLayoutManager(requireActivity(), 1000)
    }

    private fun setEdgeDecoration() {
        recyclerView.addItemDecoration(StraightEdgeDecoration())
    }

    private fun createGraph(): Graph {
        val graph = Graph()

        val list2: LiveData<BookAndCharacter> =
            viewModel.getCharacter(currentBook.bookId)

        list2.observe(viewLifecycleOwner) { characters ->
            viewModel.addAllBooksToGraph(graph, characters.characterList)

            val list: LiveData<List<BookCharacterCrossRef>> =
                viewModel.getCharacterRelationFromBook(currentBook.bookId)

            list.observe(viewLifecycleOwner) { listRel ->
                viewModel.addEdgeToGraph(graph, listRel)
            }
        }
        return graph
     }


 private fun setupGraphView(graph: Graph) {
     adapter = object : AbstractGraphAdapter<NodeViewHolder>() {
         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
             val view = LayoutInflater.from(parent.context)
                 .inflate(R.layout.node, parent, false)
             return NodeViewHolder(view)
         }

         override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
             holder.textView.text = Objects.requireNonNull(getNodeData(position)).toString()
         }

     }.apply {
         this.submitGraph(graph)
         recyclerView.adapter = this
     }
 }

 private inner class NodeViewHolder internal constructor(itemView: View) :
     RecyclerView.ViewHolder(itemView) {
     var textView: TextView = itemView.findViewById(R.id.textView)

     init {
         itemView.setOnClickListener {
             /*  if (!fab.isShown) {
               fab.show()
           }*/
             currentNode = adapter.getNode(bindingAdapterPosition)
             Snackbar.make(
                 itemView,
                 "Clicked on " + adapter.getNodeData(bindingAdapterPosition)?.toString(),
                 Snackbar.LENGTH_SHORT
             ).show()
         }
     }
 }

}


