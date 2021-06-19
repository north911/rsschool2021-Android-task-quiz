package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.entity.getAllQeustions
import java.lang.StringBuilder


private const val result = "param1"
private const val answersParam = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    private var quizResult: Int? = null
    private var _binding: FragmentResultBinding? = null
    private var listener: ResultKeysPressed? = null
    private var answers: IntArray? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizResult = it.getInt(result)
            answers = it.getIntArray(answersParam)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ResultKeysPressed
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        _binding?.textView?.text = "Result is: ${quizResult?.times(20)}%"
        _binding?.backButton?.setOnClickListener {
            listener?.onBackKeyPressed(0, 0, intArrayOf(0, 0, 0, 0, 0))
        }
        _binding?.exitButton?.setOnClickListener {
            activity?.moveTaskToBack(true)
            activity?.finish()
        }

        _binding?.shareButton?.setOnClickListener {
            shareToEmail(answers, quizResult)
        }
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    fun shareToEmail(msg: IntArray?, result: Int?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_TEXT, prepareEmailBody(msg, result))
        startActivity(intent)
    }

    fun prepareEmailBody(msg: IntArray?, result: Int?) :String {
        var resultStr : StringBuilder = StringBuilder("Your result is: ")
        resultStr.append(result).append(" !").append("\n")
        var questions = getAllQeustions()
        var count = 0
        for (question in questions) {
            resultStr.append(count).append(".")
            resultStr.append(question.question).append("\n")
            resultStr.append("your answer is: ")
            when (answers?.get(count).toString().lastOrNull()) {
                '5' -> resultStr.append(question.opt1)
                '7' -> resultStr.append(question.opt2)
                '6' -> resultStr.append(question.opt3)
                '4' -> resultStr.append(question.opt4)
                '3' -> resultStr.append(question.opt5)
            }
            resultStr.append("\n")
            resultStr.append("Correct is: ${question.correct}")
            resultStr.append("\n")
            count++
        }
        return resultStr.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: IntArray?) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(result, param1)
                    putIntArray(answersParam, param2)
                }
            }
    }

    interface ResultKeysPressed {
        fun onBackKeyPressed(pointer: Int?, correctAnswers: Int?, answers: IntArray?)
    }
}