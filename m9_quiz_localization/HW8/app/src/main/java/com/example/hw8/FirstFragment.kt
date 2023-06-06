package com.example.hw8

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.hw8.databinding.FragmentFirstBinding
import java.util.*

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    val lang = Locale.getDefault().toString()
    val quizStorage = {
        when (lang) {
            "en_US" -> QuizStorage.getQuiz(QuizStorage.Locale.En)
            else -> QuizStorage.getQuiz(QuizStorage.Locale.Ru)
        }
    }
    var answers = mutableListOf<Int>(0, 0, 0)
    var userAnswer = {
        when (lang) {
            "en_US" -> QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.En), answers)
            else -> QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.Ru), answers)
        }
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.questOne.text = quizStorage.invoke().questions[0].question
        binding.q1a1.text = quizStorage.invoke().questions[0].answers[0]
        binding.q1a2.text = quizStorage.invoke().questions[0].answers[1]
        binding.q1a3.text = quizStorage.invoke().questions[0].answers[2]
        binding.q1a4.text = quizStorage.invoke().questions[0].answers[3]

        binding.questTwo.text = quizStorage.invoke().questions[1].question
        binding.q2a1.text = quizStorage.invoke().questions[1].answers[0]
        binding.q2a2.text = quizStorage.invoke().questions[1].answers[1]
        binding.q2a3.text = quizStorage.invoke().questions[1].answers[2]
        binding.q2a4.text = quizStorage.invoke().questions[1].answers[3]

        binding.questTree.text = quizStorage.invoke().questions[2].question
        binding.q3a1.text = quizStorage.invoke().questions[2].answers[0]
        binding.q3a2.text = quizStorage.invoke().questions[2].answers[1]
        binding.q3a3.text = quizStorage.invoke().questions[2].answers[2]
        binding.q3a4.text = quizStorage.invoke().questions[2].answers[3]

        binding.quest2.isVisible = false
        binding.quest3.isVisible = false
        binding.questTwo.isVisible = false
        binding.questTree.isVisible = false
        binding.buttonFirst.isVisible = false


        binding.quest1.setOnCheckedChangeListener { _, ButtonId ->
            when (ButtonId) {
                R.id.q1a1 -> {
                    binding.answerOne.text =
                        quizStorage.invoke().questions[0].feedback[0]
                    answers[0] = 0
                    binding.quest2.isVisible = true
                    binding.questTwo.isVisible = true
                }
                R.id.q1a2 -> {
                    binding.answerOne.text =
                        quizStorage.invoke().questions[0].feedback[1]
                    answers[0] = 1
                    binding.quest2.isVisible = true
                    binding.questTwo.isVisible = true
                }
                R.id.q1a3 -> {
                    binding.answerOne.text =
                        quizStorage.invoke().questions[0].feedback[2]
                    answers[0] = 2
                    binding.quest2.isVisible = true
                    binding.questTwo.isVisible = true
                }
                R.id.q1a4 -> {
                    binding.answerOne.text =
                        quizStorage.invoke().questions[0].feedback[3]
                    answers[0] = 3
                    binding.quest2.isVisible = true
                    binding.questTwo.isVisible = true
                }
                else -> binding.answerOne.text = R.string.wait_user_answer.toString()
            }
        }

        binding.quest2.setOnCheckedChangeListener { _, ButtonId ->
            when (ButtonId) {
                R.id.q2a1 -> {
                    answers[1] = 0
                    binding.answerTwo.text =
                        quizStorage.invoke().questions[1].feedback[0]
                    binding.quest3.isVisible = true
                    binding.questTree.isVisible = true
                }
                R.id.q2a2 -> {
                    answers[1] = 1
                    binding.answerTwo.text =
                        quizStorage.invoke().questions[1].feedback[1]
                    binding.quest3.isVisible = true
                    binding.questTree.isVisible = true
                }
                R.id.q2a3 -> {
                    answers[1] = 2
                    binding.answerTwo.text =
                        quizStorage.invoke().questions[1].feedback[2]
                    binding.quest3.isVisible = true
                    binding.questTree.isVisible = true
                }
                R.id.q2a4 -> {
                    answers[1] = 3
                    binding.answerTwo.text =
                        quizStorage.invoke().questions[1].feedback[3]
                    binding.quest3.isVisible = true
                    binding.questTree.isVisible = true
                }
                else -> binding.answerOne.text = R.string.wait_user_answer.toString()
            }
        }

        binding.quest3.setOnCheckedChangeListener { _, ButtonId ->
            when (ButtonId) {
                R.id.q3a1 -> {
                    answers[2] = 0
                    binding.answerTree.text =
                        quizStorage.invoke().questions[2].feedback[0]
                    binding.buttonFirst.isVisible = true
                }
                R.id.q3a2 -> {
                    answers[2] = 1
                    binding.answerTree.text =
                        quizStorage.invoke().questions[2].feedback[1]
                    binding.buttonFirst.isVisible = true
                }
                R.id.q3a3 -> {
                    answers[2] = 2
                    binding.answerTree.text =
                        quizStorage.invoke().questions[2].feedback[2]
                    binding.buttonFirst.isVisible = true
                }
                R.id.q3a4 -> {
                    answers[2] = 3
                    binding.answerTree.text =
                        quizStorage.invoke().questions[2].feedback[3]
                    binding.buttonFirst.isVisible = true
                }
                else -> binding.answerOne.text = R.string.wait_user_answer.toString()
            }
        }

        //Кнопка "назад"
        binding.buttonSecond.setOnClickListener {
            getActivity()?.finish()
        }

        //Кнопка "Отправить"
        binding.buttonFirst.setOnClickListener {
            println(answers.toString())
            var bundle = Bundle().apply {
                putString("userAnswer", userAnswer.invoke())
            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}