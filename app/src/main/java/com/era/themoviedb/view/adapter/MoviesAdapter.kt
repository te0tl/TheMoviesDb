package com.era.themoviedb.view.adapter

import com.era.themoviedb.R
import com.era.themoviedb.framework.extension.android.loadFromUrl
import com.era.themoviedb.view.common.BaseRecyclerViewAdapter
import com.era.themoviedb.view.entity.Movie
import kotlinx.android.synthetic.main.recyclerview_movie_item.*

class MoviesAdapter : BaseRecyclerViewAdapter<Movie>(true) {

    override val itemResourceId = R.layout.recyclerview_movie_item

    override val areItemsTheSame = { itemOne: Movie, itemTwo: Movie ->
        itemOne.id == itemTwo.id
    }

    override val bindData = { holder: ViewHolder<Movie>, report: Movie ->
        holder.txtViewTitle.text = report.title
        holder.txtViewOverview.text = report.overview
        holder.txtViewDate.text = report.date
        holder.imageViewPoster.loadFromUrl(report.posterUrl)
    }

}