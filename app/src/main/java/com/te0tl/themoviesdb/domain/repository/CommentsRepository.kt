package com.te0tl.themoviesdb.domain.repository

import com.te0tl.common.domain.Res
import com.te0tl.common.domain.repository.BaseRepository
import com.te0tl.themoviesdb.domain.entity.Comment
import kotlinx.coroutines.flow.Flow

interface CommentsRepository : BaseRepository<Comment>