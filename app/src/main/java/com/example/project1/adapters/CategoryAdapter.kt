package com.example.project1.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.classes.Category

class CategoryAdapter(
    private val context: Context, // Corrigé pour être un Context
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit // Callback pour gérer les clics
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // ViewHolder pour contenir les vues de chaque élément
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val subTitleTextView: TextView = itemView.findViewById(R.id.subTitleTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_items, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.titleTextView.text = category.title
        holder.subTitleTextView.text = category.taskNumber
        holder.imageView.setImageResource(category.image)

        holder.itemView.setOnClickListener {
            onItemClick(category)
        }
    }

    override fun getItemCount(): Int = categories.size

}
