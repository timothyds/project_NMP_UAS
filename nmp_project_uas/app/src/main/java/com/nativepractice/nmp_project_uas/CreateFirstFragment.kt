package com.nativepractice.nmp_project_uas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateFirstFragment: Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_first, container, false)
        val btnNext = view.findViewById<Button>(R.id.btnNext)
        val genre = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, Global.genre)
        genre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.findViewById<Spinner>(R.id.spinGenre).adapter = genre
        btnNext.setOnClickListener{
            (activity as MainActivity).title = view.findViewById<EditText>(R.id.txtTitleCreate).text.toString()
            (activity as MainActivity).desc = view.findViewById<EditText>(R.id.txtDescCreate).text.toString()
            (activity as MainActivity).imgurl = view.findViewById<EditText>(R.id.txtImgUrlCreate).text.toString()
            (activity as MainActivity).genre = view.findViewById<Spinner>(R.id.spinGenre).selectedItem.toString()
            Toast.makeText(this.context, view.findViewById<Spinner>(R.id.spinGenre).selectedItem.toString(), Toast.LENGTH_SHORT).show()
            var fragment = CreateSecondFragment()
            childFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateFirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}