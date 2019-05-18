package com.era.themoviedb.view.activity

import com.era.themoviedb.R
import com.era.themoviedb.framework.extension.android.loadFromUrl
import com.era.themoviedb.contract.MvpMovieDetail
import com.era.themoviedb.view.entity.Movie
import com.era.themoviedb.view.entity.MovieDetail
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.toolbar_image.*
import org.koin.android.ext.android.inject
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import androidx.annotation.NonNull
import com.era.themoviedb.framework.extension.android.setVisible
import com.era.themoviedb.view.entity.YoutubeVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MovieDetailActivity : BaseActivity<MvpMovieDetail.Presenter>(), MvpMovieDetail.View {
    companion object {
        const val MOVIE_ID = "movieId"
        const val MOVIE_CATEGORY = "movieCategory"
    }

    override val activityResourceId = R.layout.activity_movie_detail

    override val presenter by inject<MvpMovieDetail.Presenter>()

    override fun onPostCreateView() {
        super.onPostCreateView()

        val id = intent?.getIntExtra(MOVIE_ID, -1)
        val category = intent?.getStringExtra(MOVIE_CATEGORY)
        presenter.onMovieDataReceived(id, category)
    }

    override fun showMovie(movie: Movie) {
        collapsingToolbar.title = movie.title
        imageView.loadFromUrl(movie.posterUrl)
    }

    override fun showMovieDetails(movie: MovieDetail) {
        textViewOverview.text = movie.overview
        imageView.loadFromUrl(movie.backdropUrl)
        textViewTitle.text = movie.title
        textViewOriginalTitle.text = getString(R.string.original_title)
            .replace("{original_title}", movie.originalTitle)

        movie.homePage?.apply {
            textViewHomepage.text = getString(R.string.homepage)
                .replace("{homepage}", this)
        }

        movie.youtubeVideos?.apply {
            viewPagerVideos.setVisible()
            viewPagerVideos.adapter = VideosViewPagerAdapter(this)
        }
    }

    inner class VideosViewPagerAdapter(private val videos : List<YoutubeVideo>) : PagerAdapter() {

        override fun instantiateItem(viewGroup: ViewGroup, position: Int): Any {
            val youTubePlayerView = YouTubePlayerView(this@MovieDetailActivity)

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