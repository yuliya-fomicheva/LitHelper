package com.example.litpantry.screen.characterGraph

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.litpantry.adapter.REPOSITORY
import com.example.litpantry.database.entity.BookAndCharacter
import com.example.litpantry.database.entity.BookCharacter
import com.example.litpantry.database.entity.BookCharacterCrossRef
import dev.bandb.graphview.graph.Graph
import dev.bandb.graphview.graph.Node

class CharacterGraphViewModel  (application: Application): AndroidViewModel(application)   {

    fun getCharacterRelationFromBook(bookId: Int): LiveData<List<BookCharacterCrossRef>> {
        return REPOSITORY.getCharacterRelationFromBook(bookId)
    }

    fun getCharacter(bookId: Int) : LiveData<BookAndCharacter> {
       return  REPOSITORY.getCharacters(bookId)
    }

    fun addEdgeToGraph(graph: Graph, list: List<BookCharacterCrossRef>) {
        for(k in list.indices) {
            for (i in graph.nodes.indices) {
                if(list[k].characterName1!!.equals(graph.nodes[i].data))
                    for (j in graph.nodes.indices)
                        if(list[k].characterName2!!.equals(graph.nodes[j].data))
                            graph.addEdge(graph.nodes[i], graph.nodes[j])
            }
        }
    }
fun addAllBooksToGraph(graph: Graph, list: List<BookCharacter>){
    for (i in list.indices) {
        graph.addNode(Node(list[i].characterName!!))
    }
}


}