package com.nativepractice.nmp_project_uas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateSecondFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_create_second, container, false)
        val btnNext = view.findViewById<Button>(R.id.btnNext2)
        btnNext.setOnClickListener{
            val grupAccess = view.findViewById<RadioGroup>(R.id.rdGrup)
            val selectedrdo = grupAccess.checkedRadioButtonId
            if(selectedrdo != 1){
                val selectedRadioButton = view.findViewById<RadioButton>(selectedrdo)
                val selectedAccess = selectedRadioButton.text.toString()
                Toast.makeText(this.context, selectedAccess, Toast.LENGTH_SHORT).show()
                val paragraftext = view.findViewById<EditText>(R.id.txtEditPar2).text.toString()
                (activity as MainActivity).access = selectedAccess
                (activity as MainActivity).par = paragraftext
                val fregment = CreateThirdFragment()
                childFragmentManager.beginTransaction().replace(R.id.container, fregment).addToBackStack(null).commit()
            }
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateSecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}