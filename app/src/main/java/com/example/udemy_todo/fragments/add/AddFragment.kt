package com.example.udemy_todo.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.udemy_todo.R
import com.example.udemy_todo.data.ToDoViewModel
import com.example.udemy_todo.data.models.Priority
import com.example.udemy_todo.data.models.ToDoData
import com.example.udemy_todo.data.viewmodel.SharedViewModel

class AddFragment : Fragment() {

    private lateinit var view:View

    private val mTodoViewModel:ToDoViewModel by viewModels()
    private val mSharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        view = inflater.inflate(R.layout.fragment_add, container, false)
        val priorities_spinner:Spinner = view.findViewById(R.id.priorities_spinner)
        priorities_spinner.onItemSelectedListener = mSharedViewModel.listener
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title_et:EditText = view.findViewById(R.id.title_et)
        val mTitle = title_et.text.toString()
        val priorities_spinner:Spinner = view.findViewById(R.id.priorities_spinner)
        val mPriority = priorities_spinner.selectedItem.toString()
        val description_et:EditText = view.findViewById(R.id.description_et)
        val mDescription = description_et.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if(validation) {
            val newData = ToDoData (
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mTodoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }

    }


}