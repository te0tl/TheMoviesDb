package com.te0tl.themoviesdb.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import com.te0tl.common.platform.extension.android.loadFromUrl
import com.te0tl.common.presentation.view.BaseRecyclerViewAdapter
import com.te0tl.themoviesdb.databinding.RecyclerviewMovieItemBinding
import com.te0tl.themoviesdb.domain.entity.Movie

class MoviesAdapter : BaseRecyclerViewAdapter<RecyclerviewMovieItemBinding, Movie>(true) {

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
