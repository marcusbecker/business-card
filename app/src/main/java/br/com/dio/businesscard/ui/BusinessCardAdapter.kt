package br.com.dio.businesscard.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.businesscard.data.BusinessCard
import br.com.dio.businesscard.databinding.ItemBusinessCardBinding

class BusinessCardAdapter :
    ListAdapter<BusinessCard, BusinessCardAdapter.ViewHolder>(DiffCallback()) {

    var listenerShare: (View) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusinessCardBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun convertTextColor(color: Int, opossite: Boolean): Int {
        if (opossite) {
            val red = 255 - Color.red(color)
            val green = 255 - Color.green(color)
            val blue = 255 - Color.blue(color)

            return Color.rgb(red, green, blue)

        } else {
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)
            val sum = (red + green + blue) / 3

            return if (sum < 125) {
                Color.parseColor("#ffffff")
            } else {
                Color.parseColor("#000000")
            }
        }
    }

    inner class ViewHolder(
        private val binding: ItemBusinessCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusinessCard) {
            binding.tvNome.text = item.nome
            binding.tvTelefone.text = item.telefone
            binding.tvEmail.text = item.email
            binding.tvNomeEmpresa.text = item.empresa

            val textColor = convertTextColor(item.fundoPersonalizado, false)

            binding.tvNome.setTextColor(textColor)
            binding.tvTelefone.setTextColor(textColor)
            binding.tvEmail.setTextColor(textColor)
            binding.tvNomeEmpresa.setTextColor(textColor)
            binding.cdContent.setCardBackgroundColor(item.fundoPersonalizado)

            binding.cdContent.setOnClickListener {
                listenerShare(it)
            }
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<BusinessCard>() {
    override fun areItemsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem == newItem
    override fun areContentsTheSame(oldItem: BusinessCard, newItem: BusinessCard) =
        oldItem.id == newItem.id
}