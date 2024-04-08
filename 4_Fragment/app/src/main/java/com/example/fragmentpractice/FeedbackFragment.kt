package com.example.fragmentpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.transition.Visibility


/**
 * A simple [Fragment] subclass.
 * Use the [FeedbackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedbackFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_feedback, container, false)

        val yesButton = v.findViewById<RadioButton>(R.id.radio_btn_yes)
        val noButton = v.findViewById<RadioButton>(R.id.radio_btn_no)
        val reactUserTextView = v.findViewById<TextView>(R.id.react_to_user_tv)

        val radioButtonChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (!yesButton.isChecked && !noButton.isChecked)
                    reactUserTextView.visibility = View.GONE

                reactUserTextView.visibility = View.VISIBLE
                if (yesButton.isChecked)
                    reactUserTextView.text = getString(R.string.like)

                if (noButton.isChecked)
                    reactUserTextView.text = getString(R.string.thanks)
            }

        yesButton.setOnCheckedChangeListener(radioButtonChangeListener)
        noButton.setOnCheckedChangeListener(radioButtonChangeListener)

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment FeedbackFragment.
         */
        @JvmStatic
        fun newInstance() =
            FeedbackFragment()
    }
}