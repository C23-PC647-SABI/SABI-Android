package com.example.sabi.model

import com.google.gson.annotations.SerializedName

data class ResponseList(

	@field:SerializedName("ResponseList")
	val responseList: List<ResponseListItem>
)

data class ResponseListItem(

	@field:SerializedName("word_id")
	val wordId: Int,

	@field:SerializedName("attachment")
	val attachment: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("word")
	val word: String
)
