package com.te0tl.themoviesdb.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.te0tl.commons.platform.extension.android.inflate
import com.te0tl.commons.platform.extension.android.loadFromUrl
import com.te0tl.commons.presentation.view.BaseRecyclerViewAdapter
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.databinding.RecyclerviewMovieItemBinding
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.android.synthetic.main.recyclerview_movie_item.*

class MoviesAdapter : BaseRecyclerViewAdapter<RecyclerviewMovieItemBinding, Movie>(false) {

    override fun instantiateViewBinding(parent: ViewGroup) =
        RecyclerviewMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun bindData(viewBinding: RecyclerviewMovieItemBinding, model: Movie, position: Int) {
        with(viewBinding) {
            txtViewTitle.text = model.title
            txtViewOverview.text = model.overview
            txtViewDate.text = model.releaseDate
            imageViewPoster.loadFromUrl(model.posterUrl)
        }
    }

    override fun equals(model: Movie, otherModel: Movie) = model.id == otherModel.id

}