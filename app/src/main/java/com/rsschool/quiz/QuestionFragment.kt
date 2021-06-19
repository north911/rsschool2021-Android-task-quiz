package com.rsschool.quiz

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuestionBinding
import com.rsschool.quiz.entity.getQuestion
import com.rsschool.quiz.entity.questions


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    private var pointer: Int? = null
    private var correctAnswers: Int? = null
    private var answers: IntArray? = null
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
            answers = it.getIntArray(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        applyTheme()
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
        if (pointer == 4) {
            _binding?.nextButton?.text = "SUBMIT"
        }
        _binding?.toolbar?.setOnClickListener {
            pointer?.let {
                if (pointer!! > 0)
                    backButtonAction()
            }
        }
        bindNextButtonListener()
        bindPreviousButtonListener()
        _binding?.radioGroup?.setOnCheckedChangeListener { radioGroup, i ->
            _binding?.nextButton?.isEnabled = true
            pointer?.let { answers?.set(it, i) }
        }
        pointer?.let {
            answers?.get(it)?.let {
                if (answers!![pointer!!] != 0)
                    binding.radioGroup.check(it)
            }
        }
        return view
    }

    private fun applyTheme() {
        if (pointer == 0)
            context?.theme?.applyStyle(com.rsschool.quiz.R.style.Theme_Quiz_First, true)
        if (pointer == 1)
            context?.theme?.applyStyle(com.rsschool.quiz.R.style.Theme_Quiz_Second, true)
        if (pointer == 2)
            context?.theme?.applyStyle(com.rsschool.quiz.R.style.Theme_Quiz_Third, true)
        if (pointer == 3)
            context?.theme?.applyStyle(com.rsschool.quiz.R.style.Theme_Quiz_Fourth, true)
        if (pointer == 4)
            context?.theme?.applyStyle(com.rsschool.quiz.R.style.Theme_Quiz_Fifth, true)
    }

    private fun bindPreviousButtonListener() {
        _binding?.previousButton?.setOnClickListener {
            backButtonAction()
        }
    }

    private fun backButtonAction() {
        _binding?.radioGroup?.also {
            val selected = it.findViewById<RadioButton>(it.checkedRadioButtonId)
            if (selected!= null && selected.text == questions[pointer!!].correct) {
                correctAnswers = correctAnswers?.dec()
            }
        }
        pointer = pointer?.dec()
        listener?.onNextKeyPressed(pointer, correctAnswers, answers)
    }

    private fun bindNextButtonListener() {
        _binding?.nextButton?.setOnClickListener {
            _binding?.radioGroup?.also {
                val selected = it.findViewById<RadioButton>(it.checkedRadioButtonId)
                if (selected.text == questions[pointer!!].correct) {
                    correctAnswers = correctAnswers?.inc()
                }
            }
            pointer = pointer?.inc()
            listener?.onNextKeyPressed(pointer, correctAnswers, answers)
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
        fun newInstance(param1: Int, param2: Int, param3: IntArray?) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                    putIntArray(ARG_PARAM3, param3)
                }
            }
    }

    interface OnNextKeyPressed {
        fun onNextKeyPressed(pointer: Int?, correctAnswers: Int?, answers: IntArray?)
    }
}