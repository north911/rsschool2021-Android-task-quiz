package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.databinding.FragmentQuestionBinding
import com.rsschool.quiz.databinding.FragmentResultBinding

private const val result = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    private var quizResult: Int? = null
    private var _binding: FragmentResultBinding? = null
    private var listener: ResultKeysPressed? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizResult = it.getInt(result)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ResultKeysPressed
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        _binding?.textView?.text = "Result is: $quizResult"
        _binding?.backButton?.setOnClickListener {
            listener?.onBackKeyPressed(4,1)
        }
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(result, param1)
                }
            }
    }

    interface ResultKeysPressed {
        fun onBackKeyPressed(pointer: Int?, correctAnswers: Int?)
    }
}