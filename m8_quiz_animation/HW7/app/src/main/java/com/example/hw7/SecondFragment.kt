package com.example.hw7

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hw7.databinding.FragmentSecondBinding

private const val ARG_USERANSWER = "userAnswer"
private var userAnswer: String? = null

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userAnswer = it.getString(ARG_USERANSWER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Анимация кнопки - размер +50% циклично
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.5f)
        ObjectAnimator.ofPropertyValuesHolder(binding.buttonSecond, scaleX, scaleY).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        //Анимация текста
        ObjectAnimator.ofArgb(
            binding.resultText, "textColor",
            Color.parseColor("#FF00FF00"),
            Color.parseColor("#0FFF00F0")
        ).apply {
            duration = 2000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            start()
        }

        binding.resultText.text = arguments?.getString("userAnswer")
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}