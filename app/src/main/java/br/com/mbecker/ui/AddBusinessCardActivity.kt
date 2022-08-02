package br.com.mbecker.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import br.com.mbecker.App
import br.com.dio.businesscard.R
import br.com.mbecker.data.BusinessCard
import br.com.dio.businesscard.databinding.ActivityAddBusinessCardBinding
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener


class AddBusinessCardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }

    var colorPicker: Int = 0

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
    }

    override fun onResume() {
        super.onResume()
        binding.colorPicker.setColorSelectionListener(object : SimpleColorSelectionListener() {
            override fun onColorSelected(color: Int) {
                binding.imageView.background.colorFilter =
                    BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                        color,
                        BlendModeCompat.SRC_ATOP
                    )

                colorPicker = color

                Log.i("Cor selecionada", "$colorPicker")
            }
        })
    }

    private fun insertListeners() {
        binding.btnConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                nome = binding.tilNome.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                fundoPersonalizado = colorPicker
            )

            mainViewModel.insert(businessCard)
            Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}
