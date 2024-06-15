package com.example.quizapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val questions = arrayOf(
        "What is built-in database in Android Studio?",
        "What is the full form of APK in Android Development?",
        "In which year was the first Android released by Google?"
    )

    private val options = arrayOf(
        arrayOf("MySQL", "SQLite", "Firebase"),
        arrayOf("Application Programming Interface", "Android Programming Interface", "Android Package Information"),
        arrayOf("2010", "2006", "2008")
    )

    private val correctAnswers = arrayOf(1, 0, 2)

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.option1Button.setOnClickListener {
            checkAnswer(0)
        }
        binding.option2Button.setOnClickListener {
            checkAnswer(1)
        }
        binding.option3Button.setOnClickListener {
            checkAnswer(2)
        }

        binding.nextButton.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion()
            } else {
                showResults()
            }
        }

        binding.prevButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion()
            }
        }

        binding.restartButton.setOnClickListener {
            restartQuiz()
        }
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        binding.option1Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option2Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option3Button.setBackgroundColor(Color.rgb(50, 59, 96))
    }

    private fun showResults() {
        val message = "You scored $score out of ${questions.size}"
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Level Sabke Niklenge")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        binding.restartButton.isEnabled = true
    }

    private fun displayQuestion() {
        binding.questionText.text = questions[currentQuestionIndex]
        binding.option1Button.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]

        resetButtonColors()
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        displayQuestion()
        binding.restartButton.isEnabled = false
    }
}
