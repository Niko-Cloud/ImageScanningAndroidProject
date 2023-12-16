package com.ch2ps126.capstoneproject.data.remote.response

import com.google.gson.annotations.SerializedName

data class MuscleResponse(

	@field:SerializedName("MuscleResponse")
	val muscleResponse: List<MuscleResponseItem?>? = null
)

data class MuscleResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("targetMuscleName")
	val targetMuscleName: String? = null,

	@field:SerializedName("targetMuscleId")
	val targetMuscleId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
