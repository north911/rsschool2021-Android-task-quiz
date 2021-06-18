package com.rsschool.quiz

import android.os.Bundle
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
        openQuestionFragment(0, 0)
    }

    private fun openQuestionFragment(previousNumber: Int, correctAnswers: Int) {
        val firstFragment: Fragment = QuestionFragment.newInstance(previousNumber, correctAnswers)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.container.id, firstFragment)
        transaction.commit()
    }

    private fun openResultFragment(result: Int) {
        val firstFragment: Fragment = ResultFragment.newInstance(result)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.container.id, firstFragment)
        transaction.commit()
    }


    override fun onNextKeyPressed(pointer: Int?, correctAnswers: Int?) {
        if (pointer != null && correctAnswers != null && pointer < 5)
            openQuestionFragment(pointer, correctAnswers)
        if (pointer != null && correctAnswers != null && pointer == 5)
            openResultFragment(100)
    }

    override fun onBackKeyPressed(pointer: Int?, correctAnswers: Int?) {
        if (pointer != null && correctAnswers != null)
            openQuestionFragment(pointer, correctAnswers)
    }
}