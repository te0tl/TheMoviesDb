package com.te0tl.themoviesdb.presentation.movie

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.te0tl.common.platform.extension.android.*
import com.te0tl.common.presentation.activity.BaseViewModelActivity
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.databinding.ActivityMovieBinding
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.entity.YoutubeVideo
import org.jetbrains.anko.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EXTRA_KEY_MOVIE_ID = "EXTRA_KEY_MOVIE_ID"
const val EXTRA_KEY_MOVIE_TITLE = "EXTRA_KEY_MOVIE_TITLE"
const val EXTRA_KEY_MOVIE_POSTER_URL = "EXTRA_KEY_MOVIE_POSTER_URL"

class MovieActivity : BaseViewModelActivity<ActivityMovieBinding, MovieState, MovieViewModel>() {

    override val viewBinding: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override val viewModel: MovieViewModel by viewModel()

    private var id: Int = 0
    private lateinit var title: String
    private lateinit var posterUrl: String

    override fun parseArguments(bundle: Bundle?) {
        checkNotNull(bundle) {
            "arguments can't be null"
        }
        bundle.run {
            require(containsKey(EXTRA_KEY_MOVIE_ID))
            require(containsKey(EXTRA_KEY_MOVIE_TITLE))
            require(containsKey(EXTRA_KEY_MOVIE_POSTER_URL))

            id = getInt(EXTRA_KEY_MOVIE_ID)
            title = getString(EXTRA_KEY_MOVIE_TITLE)?: ""
            posterUrl = getString(EXTRA_KEY_MOVIE_POSTER_URL)?: ""

        }
    }

    override fun onViewAndExtrasReady() {
        with(viewBinding) {
            includedToolbar.collapsingToolbar.title = title
            includedToolbar.imageView.loadFromUrl(posterUrl)
        }
        viewModel.getMovieDetail(id)

        val imageView = findViewById<ImageView>(R.id.imageView)

        imageView.setOnClickListener {

        }

    }

    override fun onNewViewModelState(newState: MovieState) {
        with(viewBinding) {
            when(newState) {
                is MovieState.MovieDetailReady -> {
                    onMovieDetailReady(newState.movieDetail)
                    contentContainer.show()
                    progressContainer.hide()
                    errorContainer.hide()
                }
                is MovieState.Loading -> {
                    contentContainer.hide()
                    progressContainer.show()
                    errorContainer.hide()
                }
                is MovieState.Error -> {
                    contentContainer.hide()
                    progressContainer.hide()
                    errorContainer.show()
                    txtViewError.text = newState.error
                }
            }
        }
    }

    private fun onMovieDetailReady(movie: MovieDetail) {
        with(viewBinding) {
            textViewOverview.text = movie.overview
            includedToolbar.imageView.loadFromUrl(movie.backdropUrl)
            textViewTitle.text = movie.title
            textViewOriginalTitle.text = getString(R.string.original_title)
                .replace("{original_title}", movie.originalTitle)

            movie.homePage?.also {
                textViewHomepage.text = getString(R.string.homepage)
                    .replace("{homepage}", it)
            }

            movie.youtubeVideos.also {
                viewPagerVideos.show()
                viewPagerVideos.adapter = VideosViewPagerAdapter(it)
            }
        }

    }

    inner class VideosViewPagerAdapter(private val videos : List<YoutubeVideo>) : PagerAdapter() {

        override fun instantiateItem(viewGroup: ViewGroup, position: Int): Any {
            val youTubePlayerView = YouTubePlayerView(this@MovieActivity)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    val videoId = videos[position].id
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })

            lifecycle.addObserver(youTubePlayerView)
            viewGroup.addView(youTubePlayerView)
            return youTubePlayerView
        }

        override fun destroyItem(viewGroup: ViewGroup, position: Int, view: Any) {
            lifecycle.removeObserver(view as YouTubePlayerView)
            viewGroup.removeView(view as View)
        }

        override fun getCount(): Int {
            return videos.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return videos[position].name
        }

    }
}