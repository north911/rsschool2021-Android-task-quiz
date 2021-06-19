package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), QuestionFragment.OnNextKeyPressed,
    ResultFragment.ResultKeysPressed {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openQuestionFragment(0, 0, intArrayOf(0, 0, 0, 0, 0))
    }

    private fun openQuestionFragment(previousNumber: Int, correctAnswers: Int, answers: IntArray?) {
        val firstFragment: Fragment =
            QuestionFragment.newInstance(previousNumber, correctAnswers, answers)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.container.id, firstFragment)
        transaction.commit()
    }

    private fun openResultFragment(result: Int, answers: IntArray?) {
        val firstFragment: Fragment = ResultFragment.newInstance(result, answers)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.container.id, firstFragment)
        transaction.commit()
    }


    override fun onNextKeyPressed(pointer: Int?, correctAnswers: Int?, answers: IntArray?) {
        if (pointer != null && correctAnswers != null && pointer < 5)
            openQuestionFragment(pointer, correctAnswers, answers)
        if (pointer != null && correctAnswers != null && pointer == 5)
            openResultFragment(correctAnswers, answers)
    }

    override fun onBackKeyPressed(pointer: Int?, correctAnswers: Int?, answers: IntArray?) {
        if (pointer != null && correctAnswers != null)
            openQuestionFragment(pointer, correctAnswers, answers)
    }
}