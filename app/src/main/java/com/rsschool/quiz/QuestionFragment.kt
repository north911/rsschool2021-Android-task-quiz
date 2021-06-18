package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.databinding.FragmentQuestionBinding
import com.rsschool.quiz.entity.getQuestion


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    private var pointer: Int? = null
    private var correctAnswers: Int? = null
    private var listener: OnNextKeyPressed? = null

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnNextKeyPressed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pointer = it.getInt(ARG_PARAM1)
            correctAnswers = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        val view = binding.root
        _binding?.nextButton?.isEnabled = false
        _binding?.toolbar?.title = "Question ${pointer?.plus(1)}"
        initRadioButton()
        if (_binding?.radioGroup?.checkedRadioButtonId == -1) {
            _binding?.nextButton?.isEnabled = false
        }
        if (pointer == 0) {
            _binding?.previousButton?.isEnabled = false
        }
        bindNextButtonListener()
        bindPreviousButtonListener()
        _binding?.radioGroup?.setOnCheckedChangeListener { radioGroup, i ->
            _binding?.nextButton?.isEnabled = true
        }
        return view
    }

    private fun bindPreviousButtonListener() {
        _binding?.previousButton?.setOnClickListener {
            pointer = pointer?.dec()
            correctAnswers = correctAnswers?.dec()
            listener?.onNextKeyPressed(pointer, correctAnswers)
        }
    }

    private fun bindNextButtonListener() {
        _binding?.nextButton?.setOnClickListener {
            pointer = pointer?.inc()
            correctAnswers = correctAnswers?.inc()
            listener?.onNextKeyPressed(pointer, correctAnswers)
        }
    }

    private fun initRadioButton() {
        binding.question.text = pointer?.let { getQuestion(it).question }.also {
            binding.optionOne.text = pointer?.let { it1 -> getQuestion(it1).opt1 }
            binding.optionTwo.text = pointer?.let { it1 -> getQuestion(it1).opt2 }
            binding.optionThree.text = pointer?.let { it1 -> getQuestion(it1).opt3 }
            binding.optionFour.text = pointer?.let { it1 -> getQuestion(it1).opt4 }
            binding.optionFive.text = pointer?.let { it1 -> getQuestion(it1).opt5 }
        }
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
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: Int) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

    interface OnNextKeyPressed {
        fun onNextKeyPressed(pointer: Int?, correctAnswers: Int?)
    }
}