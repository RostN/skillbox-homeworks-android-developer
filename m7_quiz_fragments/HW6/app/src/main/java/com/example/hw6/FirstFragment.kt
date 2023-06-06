package com.example.hw6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hw6.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
private const val ARG_USERANSWER = "userAnswer"

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val quizStorage = QuizStorage.getQuiz(QuizStorage.Locale.Ru)
    var answers = mutableListOf<Int>()
    var userAnswer = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        binding.questOne.text = quizStorage.questions[0].question
        binding.q1a1.text = quizStorage.questions[0].answers[0]
        binding.q1a2.text = quizStorage.questions[0].answers[1]
        binding.q1a3.text = quizStorage.questions[0].answers[2]
        binding.q1a4.text = quizStorage.questions[0].answers[3]

        binding.questTwo.text = quizStorage.questions[1].question
        binding.q2a1.text = quizStorage.questions[1].answers[0]
        binding.q2a2.text = quizStorage.questions[1].answers[1]
        binding.q2a3.text = quizStorage.questions[1].answers[2]
        binding.q2a4.text = quizStorage.questions[1].answers[3]

        binding.questTree.text = quizStorage.questions[2].question
        binding.q3a1.text = quizStorage.questions[2].answers[0]
        binding.q3a2.text = quizStorage.questions[2].answers[1]
        binding.q3a3.text = quizStorage.questions[2].answers[2]
        binding.q3a4.text = quizStorage.questions[2].answers[3]

        binding.quest1.setOnCheckedChangeListener { _, ButtonId ->
            when (ButtonId) {
                R.id.q1a1 -> {
                    binding.answerOne.text =
                        quizStorage.questions[0].feedback[0]
                    answers.add(0, 0)
                }
                R.id.q1a2 -> {
                    binding.answerOne.text =
                        quizStorage.questions[0].feedback[1]
                    answers.add(0, 1)
                }
                R.id.q1a3 -> {
                    answers.add(0, 2)
                    binding.answerOne.text =
                        quizStorage.questions[0].feedback[2]
                }
                R.id.q1a4 -> {
                    answers.add(0, 3)
                    binding.answerOne.text =
                        quizStorage.questions[0].feedback[3]
                }
                else -> binding.answerOne.text = "Ожидаем вашего решения"
            }
        }
        binding.quest2.setOnCheckedChangeListener { _, ButtonId ->
            when (ButtonId) {
                R.id.q2a1 -> {
                    answers.add(1, 0)
                    binding.answerTwo.text =
                        quizStorage.questions[1].feedback[0]
                }
                R.id.q2a2 -> {
                    answers.add(1, 1)
                    binding.answerTwo.text =
                        quizStorage.questions[1].feedback[1]
                }
                R.id.q2a3 -> {
                    answers.add(1, 2)
                    binding.answerTwo.text =
                        quizStorage.questions[1].feedback[2]
                }
                R.id.q2a4 -> {
                    answers.add(1, 3)
                    binding.answerTwo.text =
                        quizStorage.questions[1].feedback[3]
                }
                else -> binding.answerOne.text = "Ожидаем вашего решения"
            }
        }
        binding.quest3.setOnCheckedChangeListener { _, ButtonId ->
            when (ButtonId) {
                R.id.q3a1 -> {
                    answers.add(2, 0)
                    binding.answerTree.text =
                        quizStorage.questions[2].feedback[0]
                }
                R.id.q3a2 -> {
                    answers.add(2, 1)
                    binding.answerTree.text =
                        quizStorage.questions[2].feedback[1]
                }
                R.id.q3a3 -> {
                    answers.add(2, 2)
                    binding.answerTree.text =
                        quizStorage.questions[2].feedback[2]
                }
                R.id.q3a4 -> {
                    answers.add(2, 3)
                    binding.answerTree.text =
                        quizStorage.questions[2].feedback[3]
                }
                else -> binding.answerOne.text = "Ожидаем вашего решения"
            }
        }

        binding.buttonSecond.setOnClickListener {
            getActivity()?.finish()
        }

        binding.buttonFirst.setOnClickListener {
            userAnswer = QuizStorage.answer(QuizStorage.getQuiz(QuizStorage.Locale.Ru), answers)

            var bundle = Bundle().apply {
                putString("userAnswer", userAnswer)

            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            findNavController().navigate(R.id.SecondFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}